package com.system.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/admin")
public class SystemExceptionController extends BaseController{
	private final static Logger log = Logger.getLogger(SystemExceptionController.class);
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>404页面<br>
	 * @return
	 */
	@RequestMapping(value = "/404.html")
	public String exception_404() {
		return "/admin/404";
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>500页面<br>
	 * @return
	 */
	@RequestMapping(value = "/500.html")
	public String exception_500() {
		return "/admin/500";
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>无权限<br>
	 * @return
	 */
	@RequestMapping(value = "/intercept.html")
	public String intercept() {
		return "/admin/intercept";
	}
	
}

