package com.feixing.message;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

import com.feixing.bean.User;

public class SendMessage {
	
	private static Logger log = Logger.getLogger(SendMessage.class);
	private DefaultHttpClient httpclient = null;
	private HttpResponse response;
	
	// 账号
	private String userName = "";
	// 密码
	private String password = "";
	//登陆者的userId（给自己发短信）
	private String idMyself = "";
	private static String loginURL = "http://f.10086.cn/im5/login/loginHtml5.action";//登陆飞信
	private static String sendUrl = "http://f.10086.cn/im5/chat/sendNewGroupShortMsg.action";//发送短信URL
	private static String getUserIdUrl= "http://f.10086.cn/im5/index/searchFriendsByQueryKey.action";//查找好友
	private static String logoutUrl = "http://f.10086.cn/im5/index/logoutsubmit.action?t=1366516396340";//退出飞信
	
	public SendMessage(User user) {
		this.userName = user.getUserName();
		this.password = user.getPassword();
	}
	
	/**
	 * 登陆飞信wap门户
	 * @return
	 */
	public boolean login() {
		Boolean bol = false ;
		if (httpclient != null) {
			return true;
		}
		httpclient = null;
		httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(loginURL);
		// All the parameters post to the web site
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("m", userName));
		nvps.add(new BasicNameValuePair("pass", password));
		nvps.add(new BasicNameValuePair("loginstatus", "1"));
		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();  
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8")); 
		    String line = null;   
		    if ((line = reader.readLine()) != null) {   
		       JSONObject jsonObject = JSONObject.fromObject( line );
		       if("200".equals(jsonObject.get("loginstate").toString())){
		    	   bol = true;//登陆成功
		    	   idMyself = jsonObject.getString("idUser");//赋值自己的userid
		    	   log.info("用户登录成功,手机号为"+userName+",idMyself="+idMyself);
		       }else{
		    	   log.error(userName+"用户登录失败，userName="+userName);
		       }
		     }
 		} catch (Exception e) {
			e.printStackTrace();
			log.error(userName+"用户登录失败，userName="+userName+"."+e.getMessage());
		} finally {
			httpost.abort();
		}
		return bol;
	}
	
    /**
     * 获取飞信ID
     * @param string $mobile 手机号
     * @return string
     */
	public  String  getUid(String mobile){
    	String usid = "";
    	if (httpclient != null) {
    		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("queryKey", mobile));
			try{
				HttpPost httpost = new HttpPost(getUserIdUrl);
				httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
				response = httpclient.execute(httpost);
				HttpEntity entity = response.getEntity();  
				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8")); 
			    String line = null;   
			    if ((line = reader.readLine()) != null) { 
			        JSONObject jsonObject = JSONObject.fromObject( line );
			        JSONArray jsonArray = JSONArray.fromObject( jsonObject.get("contacts") );//获取搜索的信息
			        JSONObject obj = jsonArray.getJSONObject(0);
			        usid = obj.get("idContact").toString();
			        log.info("获取号码成功，mobile="+mobile);
			     }else{
			    	 log.info("该号码有问题，或者你没有加飞信好友,号码为："+mobile);
			     }
			}catch (Exception e) {
				log.error("获取飞信ID失败，手机号为："+mobile+","+e.getMessage());
			}
    	}
    	return usid;
    }
    
    /**
     * 
    	 * 
    	 *@方法名称:给自己发短信
    	 *@输    入:
    	 *@输    出：
    	 *@作    者:庞培杰
    	 *@创建日期:2013-4-23
    	 *@方法描述:
    	 * @param 
    	 * @return Boolean
     */
	public Boolean sendMsgToMyselfs(String msg){
    	Boolean result = false;
		if (httpclient != null) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("msg", msg));
			nvps.add(new BasicNameValuePair("touserid", idMyself));
			try{
				HttpPost httpost = new HttpPost(sendUrl);
				httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
				response = httpclient.execute(httpost);
				HttpEntity entity = response.getEntity();  
				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8")); 
			    String line = null;   
			    if ((line = reader.readLine()) != null) {   
			       JSONObject jsonObject = JSONObject.fromObject( line );
			       if("200".equals(jsonObject.get("sendCode"))){
			    	   result =true;
			    	   log.info("给自己发短信成功,idMyself="+idMyself);
			       }
			     }
			}catch (Exception e) {
				log.error("给自己发短信失败，idMyself="+idMyself+","+e.getMessage());
			}
		}
		return result;
    }
	
	/**
	 * 
		 * 
		 *@方法名称:发送短信
		 *@输    入:
		 *@输    出：
		 *@作    者:庞培杰
		 *@创建日期:2013-4-21
		 *@方法描述:
		 * @param msg:短信内容  touserid：发送人id(必须加飞信好友)
		 * @return void
	 */
	public Boolean sendMessage(String msg,String touserid){
		boolean result = false;
		if (httpclient != null) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("msg", msg));
			nvps.add(new BasicNameValuePair("touserid", touserid));
			try{
				HttpPost httpost = new HttpPost(sendUrl);
				httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
				response = httpclient.execute(httpost);
				HttpEntity entity = response.getEntity();  
				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8")); 
			    String line = null;   
			    if ((line = reader.readLine()) != null) {   
			       JSONObject jsonObject = JSONObject.fromObject( line );
			       if("200".equals(jsonObject.get("sendCode"))){
			    	   result = true;
			    	   log.info("发短信成功,idMyself="+idMyself);
			       }
			     }
			}catch (Exception e) {
				log.error("发短信失败,idMyself="+idMyself);
			}
		}
		return result;
	}
	
	public Boolean  logout(){
		Boolean result = false;
		if (httpclient != null) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			try{
				HttpPost httpost = new HttpPost(logoutUrl);
				httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
				response = httpclient.execute(httpost);
				HttpEntity entity = response.getEntity();  
				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8")); 
			    String line = null;   
			    if ((line = reader.readLine()) != null) {   
			       JSONObject jsonObject = JSONObject.fromObject( line );
			       if("退出成功".equals(jsonObject.get("tip"))){
			    	   result = true;
			    	   log.info("登出成功,idMyself="+idMyself);
			       }
			     }
			}catch (Exception e) {
				log.error(idMyself+"登出失败，"+e.getMessage());
			}
		}
		return result;
	}
	
	
	
	public static void main(String[] args) {
			User testUser = new User();
			testUser.setUserName("1361571****");
			testUser.setPassword("*******");
			testUser.setArea("1");
			SendMessage fx = new SendMessage(testUser);
			if(fx.login()){
				
				fx.sendMsgToMyselfs("ppj测试用！！！");
				//fx.sendMessage("测试",fx.getUid("1586915****"));
				fx.logout();
			}
	}
	

}
