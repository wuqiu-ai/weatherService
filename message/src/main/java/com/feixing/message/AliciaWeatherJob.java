package com.feixing.message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.feixing.bean.User;

public class AliciaWeatherJob implements Job {
	private Logger log =Logger.getLogger(AliciaWeatherJob.class);

	public void execute(JobExecutionContext jobCtx) throws JobExecutionException {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.info(sdf.format(new Date())+":AliciaWeatherJob开始执行");
		Weather weather = new Weather();
		Map<String,String> weatherMap = weather.getWeatherMap();//每一次实例化，都会调用当前的天气信息，获取Map
		
		User user =new User();
		user.setUserName("1361571****");
		user.setPassword("****");
		user.setArea("1");
		String touserid = "347681757";//飞亚
		//String touserid = "430506118";//测试-董文春
		String result = null;
		SendMessage fx = new SendMessage(user);
		if(fx.login()){
				if(fx.sendMessage(weather.getAliciaWeatherDate(), touserid)){
					result = "短信发送成功！";
				}else{
					result = "短信发送失败，请重试！";
				}
			fx.logout();
		}
		log.info(sdf.format(new Date())+":AliciaWeatherJob执行结束");
	}

}
