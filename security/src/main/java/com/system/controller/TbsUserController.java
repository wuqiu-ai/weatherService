package com.system.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.system.model.TbsMenuModel;
import com.system.model.TbsUserModel;
import com.system.service.TbsMenuService;
import com.system.service.TbsUserService;
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
	public ModelAndView index(String id,ModelMap modelMap, HttpServletRequest request) {
		List<String> buttons = new ArrayList<String>();
		List<TbsUserModel> userlist = new ArrayList<TbsUserModel>();
		TbsMenuModel tbsMenuModel=new TbsMenuModel();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", id);
		map.put("orderCondition", "sortNumber");
		System.out.println("id:" + id);
		String isAdmin = (String) SessionUtil.getAttr(request, "isAdmin");
		List<TbsMenuModel> list=null;
		try {
			if (null != isAdmin && isAdmin.equals("0")) {// 超管不需要验证权限
				list = tbsMenuService.selectByMap(map);
			} else {
				list = tbsMenuService.selectByButtons(map);
			}
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					tbsMenuModel = list.get(i);
					String button = tbsMenuModel.getButton();
					if (null != button && "null" != button) {
						buttons.add(button);//.replaceAll("&apos;", "'").replaceAll("&quot;", "\"")
					}
				}
			}
			userlist = tbsUserService.selectByEntiry(new TbsUserModel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelMap.addAttribute("userlist",userlist);
		modelMap.addAttribute("buttons", buttons);
		
		return new ModelAndView("admin/TbsUser/list", modelMap);
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
	
}
