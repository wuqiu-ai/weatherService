package controller.admin;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import com.alibaba.fastjson.JSON;

public class BaseController extends MultiActionController{
	protected final Logger log = Logger.getLogger(BaseController.class);
}

