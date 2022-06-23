package web.shopadmin;

import entity.Area;
import entity.Shop;
import entity.ShopCategory;
import entity.UserInfo;
import enums.ShopStateEnum;
import service.AreaService;
import service.ShopCategoryService;
import service.ShopService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import dto.ShopExecution;
import util.CodeUtil;
import util.HttpServletRequestUtil;

//店家管理后端实现
@Controller
@RequestMapping("/shopadmin") // url
public class ShopManagementController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;

	/**
	 * 返回可选的区域和类别
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopInitInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList;
		List<Area> areaList ;
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			map.put("shopCategoryList", shopCategoryList);
			map.put("areaList", areaList);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			map.put("errMsg", e.getMessage());
		}
		return map;
	}

	/**
	 * 转化实体信息为实体类 转化文件流信息 调用Service层在数据库添加
	 * 
	 * @param request 与HTTP报文头的信息都封装在对象里
	 * @return 返回注册结果信息
	 */
	@RequestMapping(value = "/registershop", method = RequestMethod.POST) // url 请求方法
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		if (!CodeUtil.checkVerifyCode(request)) {
			map.put("success", false);
			map.put("errMsg", "验证码错误");
			return map;
		}
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop;
		// 将信息转化为实体类
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			map.put("success", false);
			map.put("errMsg", e.getMessage());
			return map;
		}
		// 处理图片流信息
		CommonsMultipartFile shopImg;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		} else {
			map.put("success", false);
			map.put("errMsg", "没有上传图片");
			return map;
		}
		// 注册店铺 操作数据库
		if (shop != null && shopImg != null) {
			// 通过会话直接获取注册人员的ID 保证可靠性
			UserInfo owner =(UserInfo)request.getSession().getAttribute("user");
			shop.setOwner(owner);
			ShopExecution se = shopService.addShop(shop, shopImg);
			if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
				map.put("success", true);
				//通过会话获取用户所拥有的店铺
				@SuppressWarnings("unchecked")
				List<Shop> shopList=(List<Shop>) request.getSession().getAttribute("shopList");
				if(shopList==null) {
					shopList=new ArrayList<Shop>();
				}
				shopList.add(se.getShop());
				request.getSession().setAttribute("shopList", shopList);
			} else {
				map.put("success", false);
				map.put("errMsg", se.getStateInfo());
			}
			return map;
		} else {
			map.put("success", false);
			map.put("errMsg", "请输入所有店铺信息");
			return map;
		}
	}
	@RequestMapping(value = "/updateshop",method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> updateShop(HttpServletRequest request){
		Map<String, Object> map = new HashMap<>();
		if (!CodeUtil.checkVerifyCode(request)) {
			map.put("success", false);
			map.put("errMsg", "验证码错误");
			return map;
		}
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop=null;
		// 将信息转化为实体类
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			map.put("success", false);
			map.put("errMsg", e.getMessage());
			return map;
		}
		// 处理图片流信息
		CommonsMultipartFile shopImg=null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		}
		// 修改店铺信息 操作数据库
		if (shop != null&&shop.getShopId()!=null ) {
			ShopExecution se = shopService.updateShop(shop, shopImg);
			if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
				map.put("success", true);
			} else {
				map.put("success", false);
				map.put("errMsg", se.getStateInfo());
			}
			return map;
		} else if (shop == null){
			map.put("success", false);
			map.put("errMsg", "请输入欲修改的店铺信息！");
			return map;
		}else {
			map.put("success", false);
			map.put("errMsg", "shopId为空！");
			return map;
		}
	}
	@RequestMapping(value = "/getshopbyid",method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopById(HttpServletRequest request){
		Map<String, Object> map=new HashMap<String, Object>();
		Long shopId=HttpServletRequestUtil.getLong(request, "shopId");
		if(shopId!=null&&shopId>0) {
			try {
				Shop shop=shopService.getShopById(shopId);
				List<ShopCategory> shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
				List<Area> areaList = areaService.getAreaList();
				map.put("shop",shop);
				map.put("shopCategoryList", shopCategoryList);
				map.put("areaList", areaList);
				map.put("success", true);
			} catch (Exception e) {
				map.put("success",false);
				map.put("errMsg",e.getMessage());
			}
		}else if(shopId==null){
			map.put("success",false);
			map.put("errMsg","请输入shopId");
		}else {
			map.put("success",false);
			map.put("errMsg","shopId须大于0");
		}
		return map;
	}
	@RequestMapping(value = "/getshoplist",method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopList(HttpServletRequest request){
		Map<String,Object> map=new HashMap<String, Object>();
		// 通过会话获取注册人员的ID 保证可靠性
		UserInfo owner =(UserInfo)request.getSession().getAttribute("user");
		try {
			Shop shop=new Shop();
			shop.setOwner(owner);
			ShopExecution se=shopService.getShopList(shop, 0, 100);
			map.put("success",true);
			map.put("shopList",se.getShopList());
			map.put("owner",se.getShopList().get(0).getOwner());
		}catch (Exception e) {
			map.put("success",false);
			map.put("errMsg",e.getMessage());
		}
		return map;
	}
	@RequestMapping(value = "/getshopmanageinfo",method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopManageInfo(HttpServletRequest request){
		Map<String,Object> map=new HashMap<String, Object>();
		Long shopId=HttpServletRequestUtil.getLong(request, "shopId");
		if(shopId==null||shopId<=0) {
			Object curShopObject=request.getSession().getAttribute("curShop");
			if(curShopObject==null) {
				map.put("redirect",true);
				map.put("url","/SummerTraining/shopadmin/shoplist");
			}
			else {
				Shop curShop=(Shop)curShopObject;
				map.put("redirect",false);
				map.put("shopId",curShop.getShopId());
			}
		}else {
			Shop curShop=new Shop();
			curShop.setShopId(shopId);
			request.getSession().setAttribute("curShop", curShop);
			map.put("redirect",false);
		}
		return map;
	}
}
