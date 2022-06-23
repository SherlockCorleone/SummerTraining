package web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="shopadmin",method = {RequestMethod.GET})
public class ShopAdminController {
	@RequestMapping(value = "/shopregister")
	public String shopRegister() {
		return "/shop/shopregister";
	}
	@RequestMapping(value = "/shopupdate")
	public String shopUpdate() {
		return "/shop/shopupdate";
	}
	@RequestMapping(value = "/shoplist")
	public String shopSelect() {
		return "/shop/shoplist";
	}
	@RequestMapping(value = "/shopmanagement")
	public String shopManagement() {
		return "/shop/shopmanagement";
	}
	
	@RequestMapping(value = "/productcategorylist")
	public String productCategoryManagement() {
		return "/productcategory/productcategorylist";
	}
	
	@RequestMapping(value = "/productinsert")
	public String productInsert() {
		return "/product/productinsert";
	}
	@RequestMapping(value = "/productupdate")
	public String productUpdate() {
		return "/product/productupdate";
	}
	@RequestMapping(value = "/productlist")
	public String productManagement() {
		return "/product/productlist";
	}
}
