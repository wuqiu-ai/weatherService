package com.system.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.system.model.TbsMenuModel;
import com.system.model.TbsUserModel;
import com.system.service.TbsMenuService;
import com.system.service.TbsUserService;
import com.system.util.core.DataTableParameter;
import com.system.util.core.JsonUtil;
import com.system.util.core.MethodUtil;
import com.system.util.spring.MyTimestampPropertyEdit;
import com.system.util.spring.SessionUtil;

@Controller
@RequestMapping("/admin/TbsUser/")
public class TbsUserController extends BaseController{
	private static  MethodUtil util = new MethodUtil();
	
	@Autowired
	TbsMenuService<TbsMenuModel> tbsMenuService;

	// 服务类
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private TbsUserService<TbsUserModel> tbsUserService; 
	
	@InitBinder//必须有一个参数WebDataBinder 日期类型装换
	public void initBinder(WebDataBinder binder) {
		    binder.registerCustomEditor(Timestamp.class,new MyTimestampPropertyEdit()); //使用自定义属性编辑器
		//  binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>转向指定的视图<br>
	 * @return
	 */
	@RequestMapping("index.html")
	public ModelAndView index(String id,ModelMap modelMap, HttpServletRequest request,HttpServletResponse response) {
		return new ModelAndView("admin/TbsUser/list");
	}
	
	
	/**
	 * 列表数据
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("listData.html")
	@ResponseBody
	public String listData(TbsUserModel tbsUserModel,DataTableParameter parameter,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		tbsUserModel.getPageUtil().setPaging(true);
		tbsUserModel.getPageUtil().setLike(true);
		tbsUserModel.getPageUtil().setOrderByCondition("createTime desc");
		
		tbsUserModel.getPageUtil().setPageSize(Integer.valueOf(parameter.getiDisplayLength()));
		tbsUserModel.getPageUtil().setPageId(Integer.valueOf(parameter.getiDisplayStart()));
		
		List<TbsUserModel> listTbsUserModel = null;
		listTbsUserModel = tbsUserService.selectByModel(tbsUserModel);
				
		String result = "[]";
		result = CreateJsonStr(parameter.getsEcho(),tbsUserModel.getPageUtil().getRowCount(),tbsUserModel.getPageUtil().getRowCount(), listTbsUserModel);
		
		return result;
	}
	
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>转向指定的视图<br>
	 * @return
	 */
	@RequestMapping("add.html")
	public ModelAndView add(ModelMap modelMap, HttpServletRequest request) {
		
		return new ModelAndView("admin/TbsUser/add", modelMap);
	}
	
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>转向指定的视图<br>
	 * @return
	 */
	@RequestMapping("show.html")
	public ModelAndView show(ModelMap modelMap, HttpServletRequest request) {
		
		return new ModelAndView("admin/TbsUser/show", modelMap);
	}
	
    /**
     * 
     * <br>
     * <b>功能：</b>删除 TbsUserModel<br>
     * @param ids
     * @param response
     */
	@RequestMapping("del.html")
	@ResponseBody
	public String del(String[] ids,HttpServletResponse response){
		System.out.println("del-ids:"+Arrays.toString(ids));
		String result = "0";//0:失败  1：成功
		try{
			tbsUserService.deleteByPrimaryKeys(ids);
			result = "1";
		}catch(Exception e){
			result = "0";
			log.error(e);
		}
		return result;
	}
	
}
