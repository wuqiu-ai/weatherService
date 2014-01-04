package com.feixing.message;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.feixing.bean.User;
import com.feixing.util.AESHelp;


/**
 * 读取用户-启动时加载类
 * @author J~杰
 *
 */
public class ReadUserListener  implements ServletContextListener {

	private static Logger log=Logger.getLogger(ReadUserListener.class);
	 public static List<User> usersList = new ArrayList<User>();
	 public static String path = null;

	 private static List<User> readXMLFile() {
		 try{
			  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			  DocumentBuilder builder = dbf.newDocumentBuilder();
			  File file=new File(path);  
			  Document doc = builder.parse(file); // 获取到xml文件
			  // 下面开始读取
			  Element root = doc.getDocumentElement(); // 获取根元素
			  NodeList nodes = root.getElementsByTagName("user");
			  for (int i = 0; i < nodes.getLength(); i++) {
				  Element childNode = (Element) nodes.item(i);
				  if(childNode instanceof Element){  
					  User user = new User();
					  user.setUserName(childNode.getElementsByTagName("userName").item(0).getFirstChild().getNodeValue());
					  //密码是通过AES加密的，取数据时，将密码解密放入缓存
					  String content = childNode.getElementsByTagName("password").item(0).getFirstChild().getNodeValue();
					  String password = childNode.getElementsByTagName("datePwd").item(0).getFirstChild().getNodeValue();
					  byte[] decryptFrom = AESHelp.parseHexStr2Byte(content);
					  byte[] decryptResult = AESHelp.decrypt(decryptFrom,password);
					  user.setPassword(new String(decryptResult));
					  
					  user.setArea(childNode.getElementsByTagName("area").item(0).getFirstChild().getNodeValue());
					  usersList.add(user);
				  }  
			  }
		 }catch (Exception e) {
			 e.printStackTrace();
			log.error("读取XML文件失败，"+e.getMessage());
		}
		  log.info("读取XML文件成功，目前存在"+usersList.size()+"个用户");
		  return usersList;
	 }
	 
	    /**
	     * 向已存在的xml文件中插入元素
	     * */
	    public static Boolean insertXml(User user){
	    	Boolean bol = true ;
	        Element node = null;
	        Element users = null;
	        Element userName = null;
	        Element password = null;//密码
	        Element pwdpwd = null;//加密密码
	        Element area = null;
	        try{
	            //得到DOM解析器的工厂实例
	            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	            //从DOM工厂中获得DOM解析器
	            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
	            //把要解析的xml文档读入DOM解析器
	            Document doc = dbBuilder.parse(path);
	            
	            //得到文档名称为usr的元素的节点列表
	            NodeList nList = doc.getElementsByTagName("users");
	            users = (Element)nList.item(0);//获取第一个users
	            node = doc.createElement("user");//创建node--User
	            userName = doc.createElement("userName");//创建node下面的username
	            userName.appendChild(doc.createTextNode(user.getUserName()));//文本值
	            node.appendChild(userName);
	            password = doc.createElement("password");//创建node--password
	            
	            String datePwd = Long.toString( new Date().getTime() );//时间戳来作为加密密码
	            String content = user.getPassword();//需要加密的内容
	            byte[] encryptResult = AESHelp.encrypt(content, datePwd);
	            String encryptResultStr = AESHelp.parseByte2HexStr(encryptResult);//加密的密码
	            password.appendChild(doc.createTextNode(encryptResultStr));//创建node的文本值
	            node.appendChild(password);
	            
	            pwdpwd = doc.createElement("datePwd");
	            pwdpwd.appendChild(doc.createTextNode(datePwd));
	            node.appendChild(pwdpwd);
	            
	            area = doc.createElement("area");
	            area.appendChild(doc.createTextNode(user.getArea()));
	            node.appendChild(area);
	            //将use作为子元素添加到树的根节点users
	            users.appendChild(node);
	            //定义一个用于输出xml文档的类
	            TransformerFactory factory = TransformerFactory.newInstance(); 
	            factory.setAttribute("indent-number", new Integer(4));// 设置缩进长度为4   
	            Transformer transformer = factory.newTransformer();   
	            transformer.setOutputProperty(OutputKeys.INDENT, "yes");// 设置自动换行   
	            DOMSource source = new DOMSource(doc);  
	            transformer.transform(source, new StreamResult(new BufferedWriter(   
	                    new OutputStreamWriter(new FileOutputStream(path), "UTF-8"))));
	        }catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	            bol = false;
	            log.error("写入XML文件失败，"+e.getMessage());
	        }
	        usersList.add(user);
	        log.info("插入用户成功，目前存在"+usersList.size()+"个用户");
	        return bol;
	    }

	 /**
	  * 容器加载时
	  */
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		path = sce.getServletContext().getRealPath("/WEB-INF/user.xml");
		ReadUserListener read = new ReadUserListener();
		try {
			log.info("------监听器启动------");
			read.readXMLFile();
			TriggerRunner triger =new TriggerRunner();
			triger.runJob();//job启动
			TimeZone.setDefault(TimeZone.getTimeZone("GMT+0"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
		usersList = null;
		log.info("------监听器销毁------");
	}
	 
	 
	public static void main(String[] args){
		path  ="F:/eclipse/workspace/message/src/main/webapp/WEB-INF/user.xml";
		User user = new User();
		user.setUserName("1361571****");
		user.setPassword("******");
		user.setArea("*");
		ReadUserListener.insertXml(user);
	}

	
}
