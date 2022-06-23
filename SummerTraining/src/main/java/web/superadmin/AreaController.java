package web.superadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



import entity.Area;
import service.AreaService;

@Controller//指明时控制层
@RequestMapping("/superadmin")//指明url
public class AreaController {
	Logger logger= LoggerFactory.getLogger(AreaController.class);
	@Autowired
	private AreaService areaService;

	@RequestMapping(value = "/listarea", method = RequestMethod.GET)//指明url和http请求方法GET
	@ResponseBody//将map对象转换为json
	private Map<String, Object> listArea() {
		logger.info("===start===");
		long startTime=System.currentTimeMillis();
		Map<String, Object> modelMap = new HashMap<>();
		List<Area> list ;
		try {
			list = areaService.getAreaList();
			modelMap.put("rows", list);
			modelMap.put("total", list.size());
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		logger.error("test error!");
		long endTime=System.currentTimeMillis();
		logger.info("costTime:[{}ms]",endTime-startTime);
		logger.info("===end===");
		return modelMap;
	}
}
