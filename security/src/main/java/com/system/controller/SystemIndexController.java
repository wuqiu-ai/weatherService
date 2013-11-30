package com.system.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.model.TbsMenuModel;
import com.system.model.TbsUserModel;
import com.system.service.TbsMenuService;
import com.system.service.TbsUserService;
import com.system.util.core.MapBeanUtil;
import com.system.util.core.MethodUtil;
import com.system.util.spring.SessionUtil;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


@Controller
@RequestMapping("/admin")
public class SystemIndexController extends BaseController {
	private final static Logger log = Logger.getLogger(SystemIndexController.class);
	public static MethodUtil util = new MethodUtil();
	private StringBuffer sb = new StringBuffer();

	@Autowired
	private TbsUserService<TbsUserModel> tbsUserService;
	
	@Autowired
	private TbsMenuService<TbsMenuModel> tbsMenuService;

	/**
	 * 
	 * <br>
	 * <b>功能：</b>登录页面<br>
	 * @return
	 */
	@RequestMapping(value = "/login.html", method = RequestMethod.GET)
	public String from() {
		return "/admin/login";
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>登录递交页<br>
	 * @param tbsUserModel
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login.html", method = RequestMethod.POST)
	public String submit(TbsUserModel tbsUserModel, ModelMap model,HttpServletResponse response, HttpServletRequest request) throws Exception {
		String sessionVerifyCode = (String) SessionUtil.getAttr(request, "VERIFY_TYPE_COMMENT");// session验证码
		SessionUtil.removeAttr(request, "VERIFY_TYPE_COMMENT");
		String verifyCode = request.getParameter("verifyCode"); // 递交的验证码
		String msg;
		String ip=MethodUtil.getIpAddr(request);
		tbsUserModel.setIp(ip);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", tbsUserModel.getUsername());
		map.put("password", util.getDES("desKey!@#", tbsUserModel.getPassword(), 0));
		List<TbsUserModel> ltub = tbsUserService.selectByMap(map);
		if (null == ltub || ltub.size() != 1) {
			msg="用户名密码有误";
			model.put("msg", msg);
			return "admin/login";
		}
		tbsUserModel = ltub.get(0);
		Integer isAdmin = tbsUserModel.getIsAdmin() == null ? 1 : tbsUserModel.getIsAdmin();
		SessionUtil.setAttr(request, "isAdmin", "" + isAdmin);
		SessionUtil.setAttr(request, "tbsUserModel", tbsUserModel);
		List<String> authUrls = new ArrayList<String>();
		authUrls.add("/admin/index.html");
		SessionUtil.setAttr(request, "authUrls", authUrls);
		return "redirect:/admin/index.html"; 
	}

	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>主页面<br>
	 * @return
	 */
	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap modelMap) throws Exception {
		TbsUserModel user = (TbsUserModel) SessionUtil.getAttr(request, "tbsUserModel");
		modelMap.put("user", user);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("andCondition", "parentId IS NULL");
		map.put("orderCondition", "sortNumber");
		List<TbsMenuModel> parentMenu = tbsMenuService.selectByMap(map);
		String isAdmin = (String) SessionUtil.getAttr(request, "isAdmin");
		if (null != isAdmin && isAdmin.equals("0")) { // 管理员
			for (int i = 0; i < parentMenu.size(); i++) {
				String id = parentMenu.get(i).getId();
				map.clear();
				map.put("parentId", id);
				List<TbsMenuModel> child = tbsMenuService.selectByMap(map);
				for (int j = 0; j < child.size(); j++) {
					if(parentMenu.get(i).getListTbsMenuModel()==null){
						parentMenu.get(i).setListTbsMenuModel(new java.util.ArrayList<TbsMenuModel>());
					}
					parentMenu.get(i).getListTbsMenuModel().add(child.get(j));
				}
			}
			modelMap.put("listTbsMenuModel", parentMenu);
			return "admin/index";
		}
		// 其他用户
		TbsUserModel tbsUserModel = (TbsUserModel) SessionUtil.getAttr(request, "tbsUserModel");
		@SuppressWarnings("unchecked")
		List<String> authUrls = (List<String>) SessionUtil.getAttr(request, "authUrls");
		map.clear();
		map.put("cloumn", "menuIdFun");
		map.put("userId", tbsUserModel.getId());
		List<Map<String, Object>> childMenu = tbsUserService.selectByRoleUrls(map);
		if (childMenu != null && childMenu.size() > 0) { // 添加授权地址
			for (int i = 0; i < childMenu.size(); i++) {
				String roleUrls = (String) childMenu.get(i).get("url");
				String[] urls = roleUrls.split("\\,");
				for (int j = 0; j < urls.length; j++) {
					System.out.println("addUrl:" + urls[j]);
					authUrls.add("/"+urls[j]);
				}
			}
		}
		map.clear();
		map.put("cloumn", "menuId");
		map.put("userId", tbsUserModel.getId());
		childMenu = tbsUserService.selectByRoleUrls(map);
		for (int i = 0; i < parentMenu.size(); i++) { // 主菜单找子菜单
			TbsMenuModel tbsMenuModel = parentMenu.get(i);
			if (null != childMenu && childMenu.size() > 0) {
				for (int j = 0; j < childMenu.size(); j++) {
					Map<String, Object> childMap = childMenu.get(j);
					System.out.println("childMap:" + childMap);
					String parentId = (String) childMap.get("parentId");
					if (tbsMenuModel != null && tbsMenuModel.getId().equals(parentId)) {
						TbsMenuModel bean=MapBeanUtil.mapToBean(childMap, TbsMenuModel.class);
						authUrls.add("/"+bean.getUrl()); // 权限URL
						if(parentMenu.get(i).getListTbsMenuModel()==null){
							parentMenu.get(i).setListTbsMenuModel(new java.util.ArrayList<TbsMenuModel>());
						}
						parentMenu.get(i).getListTbsMenuModel().add(bean);
						System.out.println("childMap:" + childMap+"|bean:"+bean.toString());
					}
				}
			}
			if (tbsMenuModel.getListTbsMenuModel()==null || tbsMenuModel.getListTbsMenuModel().size()==0) { // 没找到子菜单 删除自己
				parentMenu.remove(i);
				i--;
			}
		}
		SessionUtil.setAttr(request, "authUrls", authUrls);// 重置
		modelMap.put("listTbsMenuModel", parentMenu);
		return "/admin/index";
	}
	

	/**
	 * 
	 * <br>
	 * <b>功能：</b>退出<br>
	 * @param session
	 * @return
	 */
	@RequestMapping("/exit.html")
	public String exit(HttpSession session) {
		SessionUtil.removeSessionAll(session);
		return "/admin/login";
	}

	
	
}
