package web.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dto.ProductExecution;
import entity.Product;
import entity.ProductCategory;
import entity.Shop;
import service.ProductCategoryService;
import service.ProductService;
import service.ShopService;
import util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ShopDetailController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@RequestMapping(value = "/getshopdetailinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopDetailInfo(HttpServletRequest request){
		Map<String, Object> map=new HashMap<>();
		Long shopId=HttpServletRequestUtil.getLong(request, "shopId");
		Shop shop=null;
		List<ProductCategory> list=null;
		if(shopId!=null&&shopId>0) {
			shop=shopService.getShopById(shopId);
			list=productCategoryService.getProductCategoryListByShopId(shopId);
			map.put("shop",shop);
			map.put("productCategoryList",list);
			map.put("success",true);
			
		}else {
			map.put("success",false);
			map.put("errMsg","没有获取到shopId");
		}
		return map;
	}
	@RequestMapping(value = "/getproductlist", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> geProductList(HttpServletRequest request){
		Map<String, Object> map=new HashMap<>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize =  HttpServletRequestUtil.getInt(request, "pageSize");
		Long shopId=HttpServletRequestUtil.getLong(request, "shopId");
		if(pageIndex>-1&&pageSize>-1&&shopId!=null&&shopId>0) {
			Product product=new Product();
			Long productCategoryId=HttpServletRequestUtil.getLong(request, "productCategoryId");
			String productName=HttpServletRequestUtil.getString(request, "productName");
			if(productCategoryId!=null&&productCategoryId>0) {
				ProductCategory productCategory=new ProductCategory();
				productCategory.setProductCategoryId(productCategoryId);
				product.setProductCategory(productCategory);
			}
			if(productName!=null) {
				product.setProductName(productName);
			}
			Shop shop=new Shop();
			shop.setShopId(shopId);
			product.setShop(shop);
			product.setStatus(1);
			ProductExecution pe=productService.getProductList(product, pageIndex, pageSize);
			map.put("productList",pe.getProductList());
			map.put("count",pe.getCount());
			map.put("success",true);
		}else {
			map.put("success",false);
			map.put("errMsg","没有获取到shopId或pageIndex或pageSize");
		}
		return map;
	}
}
