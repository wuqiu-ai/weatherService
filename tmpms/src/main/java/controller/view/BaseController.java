package controller.view;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import util.core.MethodUtil;

public class BaseController extends MultiActionController{
	protected final Logger log = Logger.getLogger(BaseController.class);
	//工具类
	protected static  MethodUtil util = new MethodUtil();
	
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>输出JSON<br>
	 * @param t
	 * @param response
	 */
	/*
	public <T> void toJson(T t,HttpServletResponse response){
		 try {
			PrintWriter pw=response.getWriter();
			pw.write(JSONUtil.toJSONString(t));
			pw.flush();
			pw.close();
		} catch (Exception e) {
			log.error(e);
		}
	}*/
}

