package web.shopadmin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dto.ProductCategoryExecution;
import entity.ProductCategory;
import entity.Shop;
import enums.ProductCategoryStateEnum;
import myException.ProductCategoryExecuteException;
import service.ProductCategoryService;
import util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {
	@Autowired
	private ProductCategoryService productCategoryService;
	


	//查询商品
	@RequestMapping(value = "/getproductcategorylistbyshopid", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getProductCategoryListByShopId(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		Shop curShop = (Shop) request.getSession().getAttribute("curShop");
		Long shopId = null;
		if (curShop != null && curShop.getShopId() != null) {
			shopId = curShop.getShopId();
		}
		if (shopId != null && shopId > 0) {
			try {
				List<ProductCategory> productCategoryList = productCategoryService
						.getProductCategoryListByShopId(shopId);
				map.put("productCategoryList", productCategoryList);
				map.put("success", true);
			} catch (Exception e) {
				map.put("success", false);
				map.put("errMsg", e.getMessage());
			}
		} else {
			map.put("success", false);
			map.put("errMsg", "shopId非法");
		}
		return map;
	}
	//批量插入
	@RequestMapping(value = "/insertproductcategorybatch", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> batchInsertProductCategory(HttpServletRequest request,
			@RequestBody List<ProductCategory> productCategoryList) {
		Map<String, Object> map = new HashMap<String, Object>();
		Shop curShop = (Shop) request.getSession().getAttribute("curShop");
		for (ProductCategory pc : productCategoryList) {
			pc.setShopId(curShop.getShopId());
			pc.setCreateDate(new Date());
		}
		if (productCategoryList == null || productCategoryList.size() == 0) {
			map.put("success", false);
			map.put("errMsg", "要添加的元素为0！");
		} else {
			try {
				ProductCategoryExecution pce = productCategoryService.batchInsertProductCategory(productCategoryList);
				if (pce.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
					map.put("success", true);
				} else if (pce.getState() == ProductCategoryStateEnum.EMPTY_LIST.getState()) {
					map.put("success", false);
					map.put("errMsg", pce.getStateInfo());
				} else if (pce.getState() == ProductCategoryStateEnum.WARNING.getState()) {
					map.put("success", false);
					map.put("errMsg", pce.getStateInfo());
				}
			} catch (ProductCategoryExecuteException e) {
				map.put("success", false);
				map.put("errMsg", e.getMessage());
				return map;
			}
		}
		return map;
	}
	//删除
	@RequestMapping(value = "/deleteproductcategory", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> deleteProductCategory(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
		if (productCategoryId != null && productCategoryId > 0) {
			try {
				Shop curShop = (Shop) request.getSession().getAttribute("curShop");
				ProductCategoryExecution pce = productCategoryService.deleteProductCategory(productCategoryId,
						curShop.getShopId());
				if (pce.getState() == ProductCategoryStateEnum.SUCCESS.getState())
					map.put("success", true);
				else {
					map.put("success", false);
					map.put("errMsg", pce.getStateInfo());
				}
			} catch (ProductCategoryExecuteException e) {
				map.put("success", false);
				map.put("errMsg", e.getMessage());
				return map;
			}
		} else {
			map.put("success", false);
			map.put("errMsg", "productCategoryId为空！");
		}
		return map;
	}
}
