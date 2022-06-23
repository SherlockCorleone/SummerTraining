package web.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import entity.HeadLine;
import entity.ShopCategory;
import service.HeadLineService;
import service.ShopCategoryService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



//主页面
@Controller
@RequestMapping("/frontend")
public class MainPageController {
	@Autowired
	ShopCategoryService shopCategoryService;
	@Autowired
	HeadLineService headLineService;
	
	
	@RequestMapping(value = "/getmainpageinfo",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getMainPageInfo(){
		Map<String,Object> map=new HashMap<>();
		//获取一级店铺类别
		List<ShopCategory> shopCategoryList= new ArrayList<ShopCategory>();
		try {
			shopCategoryList=shopCategoryService.getShopCategoryList(null);
			map.put("shopCategoryList", shopCategoryList);
		}catch (Exception e) {
			map.put("success",false);
			map.put("errMsg",e.getMessage());
			return map;
		}
		//获取头条信息
		List<HeadLine> headLineList=new ArrayList<>();
		try {
			HeadLine headLine=new HeadLine();
			headLine.setStatus(1);
			headLineList=headLineService.getHeadLine(headLine);
			map.put("headLineList", headLineList);
		} catch (Exception e) {
			map.put("success",false);
			map.put("errMsg",e.getMessage());
			return map;
		}
		map.put("success",true);
		return map;
	}
}
