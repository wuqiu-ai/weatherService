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

public class SendMseeageJob implements Job {
	private Logger log =Logger.getLogger(SendMseeageJob.class);

	public void execute(JobExecutionContext jobCtx) throws JobExecutionException {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.info(sdf.format(new Date())+":SendMseeageJob开始执行");
		List<User> users = ReadUserListener.usersList;//用户列表
		Weather weather = new Weather();
		Map<String,String> weatherMap = weather.getWeatherMap();//每一次实例化，都会调用当前的天气信息，获取Map
		for(int i=0;i<users.size();i++){
			User user = users.get(i);
			SendMessage fx = new SendMessage(user);
			if(fx.login()){
				fx.sendMsgToMyselfs(weatherMap.get(user.getArea()));//发短信
				fx.logout();
			}
		}
		log.info(sdf.format(new Date())+":SendMseeageJob执行结束");
	}

}
