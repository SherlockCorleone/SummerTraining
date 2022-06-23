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

import dto.ShopExecution;
import entity.Area;
import entity.Shop;
import entity.ShopCategory;
import service.AreaService;
import service.ShopCategoryService;
import service.ShopService;
import util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ShopListController {
	@Autowired
	private AreaService areaService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private ShopService shopService;

	@RequestMapping(value = "/getshoplistinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopListInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		Long parentId = HttpServletRequestUtil.getLong(request, "parentId");
		List<ShopCategory> shopCategoryList = null;
		if (parentId != null && parentId > 0) {
			try {
				//获取二级分类
				ShopCategory shopCategory = new ShopCategory();
				ShopCategory parent = new ShopCategory();
				parent.setShopCategoryId(parentId);
				shopCategory.setParent(parent);
				shopCategoryList = shopCategoryService.getShopCategoryList(shopCategory);
			} catch (Exception e) {
				map.put("success", false);
				map.put("errMsg", e.getMessage());
				return map;
			}
		} else {
			try {
				//获取一级分类
				shopCategoryList = shopCategoryService.getShopCategoryList(null);
			} catch (Exception e) {
				map.put("success", false);
				map.put("errMsg", e.getMessage());
				return map;
			}
		}
		map.put("shopCategoryList", shopCategoryList);
		List<Area> areaList = null;
		try {
			areaList = areaService.getAreaList();
			map.put("areaList", areaList);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			map.put("errMsg", e.getMessage());
		}
		return map;
	}
	@RequestMapping(value = "/getshoplist",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getShopList(HttpServletRequest request){
		Map<String, Object> map = new HashMap<>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize =  HttpServletRequestUtil.getInt(request, "pageSize");
		if(pageIndex>-1&&pageSize>-1) {
			Long parentId=HttpServletRequestUtil.getLong(request, "parentId");
			Long shopCategoryId=HttpServletRequestUtil.getLong(request, "shopCategoryId");
			Integer areaId=HttpServletRequestUtil.getInt(request, "areaId");
			String shopName=HttpServletRequestUtil.getString(request, "shopName");
			Shop shop=new Shop();
			if(parentId!=null&&parentId>0) {
				ShopCategory child=new ShopCategory();
				ShopCategory parent=new ShopCategory();
				parent.setShopCategoryId(parentId);
				child.setParent(parent);
				shop.setShopCategory(child);
			}
			if(shopCategoryId!=null &&shopCategoryId>0) {
				ShopCategory shopCategory=new ShopCategory();
				shopCategory.setShopCategoryId(shopCategoryId);
				shop.setShopCategory(shopCategory);
			}
			if(areaId!=null&&areaId>0) {
				Area area=new Area();
				area.setAreaId(areaId);
				shop.setArea(area);
			}
			if(shopName!=null) {
				shop.setShopName(shopName);
			}
			shop.setStatus(1);
			ShopExecution se=shopService.getShopList(shop, pageIndex, pageSize);
			map.put("shopList",se.getShopList());
			map.put("count",se.getCount());
			map.put("success",true);
		}else {
			map.put("success",false);
			map.put("errMsg","PageSize和PageIndex错误");
		}
		return map;
	}

}

