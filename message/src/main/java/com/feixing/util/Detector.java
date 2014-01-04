package com.feixing.util;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
/**
* 
* 检测tomcate自宕机，如果宕机自启动服务
*
*/
public class Detector {
 
private static void keepTomcatAlive() throws NullPointerException {
String s;
String t = new String("tomcat6");
boolean isTomcatAlive = false;
java.io.BufferedReader in;
 
System.setProperty("sun.net.client.defaultConnectTimeout", "8000");   
System.setProperty("sun.net.client.defaultReadTimeout", "10000");  
 
try {
	URL url = new URL("http://localhost:8080/message/index.jsp");
	URLConnection con = url.openConnection();
	in = new java.io.BufferedReader(new java.io.InputStreamReader(con.getInputStream()));
	con.setConnectTimeout(1000);
	con.setReadTimeout(4000);
	while ((s = in.readLine()) != null) {
	if (s.length() > 0) {
		//accessed page successful.
		return;
		}
	}
	in.close();
} catch (Exception ex) {
          //ex.printStackTrace();
	}
 
try {
	java.lang.Process p = java.lang.Runtime.getRuntime().exec("ps -aux");
	in = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
	while ((s = in.readLine()) != null) {
	if (s.startsWith(t)) {
		isTomcatAlive = true;
		break;
		}
	}
	in.close();
}catch (Exception e) {
	e.printStackTrace();
}
 
if (isTomcatAlive) {
	System.out.println("<" + new Date() + "> Tomcat is alive but not response!");
	stopTomcat();
}
 
	startTomcat();
}
 
public static void stopTomcat() {
	try {
		java.lang.Process p = java.lang.Runtime.getRuntime().exec("/etc/init.d/tomcat6 stop");
		java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
		String s;
		String t = "Stopping";
		boolean restart = false;
		while ((s = in.readLine()) != null) {
			if (s.indexOf(t) != -1) {
			restart = true;
			break;
			}
		}
		System.out.println("<" + new Date() + "> Tomcat is stop " + (restart ? "OK" : "ERROR"));
	} catch (Exception e) {
  		e.printStackTrace();
	}
}
 
public static void startTomcat() {
	try {
		java.lang.Process p = java.lang.Runtime.getRuntime().exec("/etc/init.d/tomcat6 start");
		java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
		String s;
		String t = "Starting";
		boolean restart = false;
		while ((s = in.readLine()) != null) {
		if (s.indexOf(t) != -1) {
			restart = true;
			break;
			}
		}
		System.out.println("<" + new Date() + "> Tomcat is start " + (restart ? "OK" : "ERROR"));
	} catch (Exception e) {
		e.printStackTrace();
	}
}
 
private static void debug(String msg){
	System.out.println("Debug::: "+msg);
}
 
public static void main(String[] args) {
	while (true) {
			try {   
                                debug("Detect agin <"+new Date()+">");
				Detector.keepTomcatAlive();
                                debug("Sleep...");
				Thread.sleep(30000);
			} catch (Exception ex) {
                           ex.printStackTrace();
			}
		}
}
 
}