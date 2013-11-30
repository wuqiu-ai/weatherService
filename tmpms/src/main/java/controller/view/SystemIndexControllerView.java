package controller.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class SystemIndexControllerView extends BaseController{
	
	@RequestMapping("index")
	public String index(ModelMap modelMap) {
		//super.loadLeft(modelMap);
		return "index";
	}
	
	@RequestMapping("download.html")
	public String download(){
		System.out.println("download.html");
		return "download";
	}
	
	
	
	
}
