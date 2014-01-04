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


public class ServiceServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String result = "定制该服务失败，该号码或者飞信密码错误，请重新输入！";
		String userName = req.getParameter("phone");
		String passWord = req.getParameter("fetion_password");
		String area = req.getParameter("area");
		System.out.println("userName:"+userName+" passWord:"+passWord+" area:"+area);
		User user =new User();
		user.setUserName(userName);
		user.setPassword(passWord);
		user.setArea(area);
		SendMessage fx = new SendMessage(user);
		if(fx.login()){
			Boolean bol = ReadUserListener.insertXml(user); 
			if(bol){
				result = "定制天气服务成功，感谢你的使用！";
				fx.sendMsgToMyselfs("欢迎定制天气服务，感谢你的使用！[J~杰]");
			}
			fx.logout();
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
