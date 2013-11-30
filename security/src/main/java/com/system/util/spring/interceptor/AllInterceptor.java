package com.system.util.spring.interceptor;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

/**
 * 资源文件url设置--main_static_url
 * @author PangPeijie
 *
 */
public class AllInterceptor implements WebRequestInterceptor {
	
	/**  
     * 该方法将在整个请求完成之后，也就是说在视图渲染之后进行调用，主要用于进行一些资源的释放  
     */ 
	public void afterCompletion(WebRequest arg0, Exception arg1)
			throws Exception {
		// TODO Auto-generated method stub
	}

	 /**  
     * 该方法将在Controller执行之后，返回视图之前执行，ModelMap表示请求Controller处理之后返回的Model对象，所以可以在  
     * 这个方法中修改ModelMap的属性，从而达到改变返回的模型的效果。  
     */  
	public void postHandle(WebRequest arg0, ModelMap modelMap) throws Exception {
		// TODO Auto-generated method stub
		if(modelMap!=null){
			modelMap.put("main_static_url", "/security");
		}
	}

	 /**  
     * 在请求处理之前执行，该方法主要是用于准备资源数据的，然后可以把它们当做请求属性放到WebRequest中  
     */ 
	public void preHandle(WebRequest arg0) throws Exception {
		// TODO Auto-generated method stub
	}

}
