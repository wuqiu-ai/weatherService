package com.feixing.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.feixing.bean.User;
import com.feixing.message.ReadUserListener;
import com.feixing.message.SendMessage;


public class SendServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String result = "";
		String userName = req.getParameter("phone");
		String passWord = req.getParameter("fetion_password");
		String toPhone = req.getParameter("tophone");
		String content = req.getParameter("message");

		System.out.println("userName:"+userName+" passWord:"+passWord+" toPhone:"+toPhone+" content"+content);
		User user =new User();
		user.setUserName(userName);
		user.setPassword(passWord);
		SendMessage fx = new SendMessage(user);
		if(fx.login()){
			String touserid = "";
			touserid = fx.getUid(toPhone);
			if(!"".equals(touserid)){
				if(fx.sendMessage(content, touserid)){
					result = "短信发送成功！";
				}else{
					result = "短信发送失败，请重试！";
				}
			}else{
				result = "发送方手机号码有问题，请重新输入！";
			}
			fx.logout();
		}else{
			result = "手机号码或者飞信密码错误，请重新输入！";
		}
		System.out.println(result);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "utf-8"));//
		resp.setCharacterEncoding("utf-8");          
		resp.setContentType("text/html; charset=utf-8");          
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">"); 
		out.println("<HTML>"); 
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>"); 
		out.println("  <BODY>"); 
		out.print(result); 
		out.println("  </BODY>"); 
		out.println("</HTML>"); 
		out.flush(); 
		out.close();
	}

}
