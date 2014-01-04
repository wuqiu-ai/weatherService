package com.system.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.system.util.core.JsonUtil;

public class BaseController extends MultiActionController{
	protected final Logger log = Logger.getLogger(BaseController.class);
	
	public String CreateJsonStr(String sEcho,Integer iTotalRecords,Integer iTotalDisplayRecords,List<?> list){
		String result = null;
		result = "{\"sEcho\":"+sEcho+",\"iTotalRecords\":"+iTotalRecords+",\"iTotalDisplayRecords\":"+iTotalDisplayRecords+",\"aaData\":";
		String aaData = JsonUtil.toJSONString(list);
		result = result + aaData + "}" ;
		return result;
	}
	
}

