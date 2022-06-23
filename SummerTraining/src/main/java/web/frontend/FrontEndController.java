package web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="frontend",method = {RequestMethod.GET})
public class FrontEndController {
	@RequestMapping(value = "/mainpage",method=RequestMethod.GET)
	public String getMainPage() {
		return "/frontend/mainpage";
	}
	@RequestMapping(value = "/shoplist",method=RequestMethod.GET)
	public String getShopList() {
		return "/frontend/shoplist";
	}
	@RequestMapping(value = "/shopdetail",method=RequestMethod.GET)
	public String getShopdetail() {
		return "/frontend/shopdetail";
	}
}
