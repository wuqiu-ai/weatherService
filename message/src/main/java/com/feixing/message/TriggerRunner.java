package com.feixing.message;


import org.apache.log4j.Logger;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;   
import org.quartz.Scheduler;   
import org.quartz.SchedulerFactory;   
import org.quartz.impl.StdSchedulerFactory;   

public class TriggerRunner {
	private static Logger log=Logger.getLogger(ReadUserListener.class);
	
	public void runJob(){
        try {   
            //创建一个JobDdtail实例，指定SimpleJob，组名为jgroup1，名称为job1_1   
            JobDetail jobDetail = new JobDetail("job1_1", "jgroup1", SendMseeageJob.class);   
            CronTrigger cronTrigger = new CronTrigger("cronTrigger1","defaultGroup");
            CronExpression cexp1 = new CronExpression("0 0 0 * * ? *" );//每天定时8点执行任务
            //CronExpression cexp1 = new CronExpression("0 0/2 * * * ?" );//测试
            cronTrigger.setCronExpression(cexp1);

            
            //飞亚天气预报:6点
            //JobDetail jobDetail2 = new JobDetail("job2", "jgroup2", AliciaWeatherJob.class);   
            //CronTrigger cronTrigger2 = new CronTrigger("cronTrigger","defaultGroup");
            //CronExpression cexp2 = new CronExpression("0 0 13 * * ? *" );//每天定时21点执行任务
            //CronExpression cexp2 = new CronExpression("0 0/1 * * * ?" );//测试
            //cronTrigger2.setCronExpression(cexp2);
  
            
            //通过SchedulerFactory获取一个调度器实例
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();   
            Scheduler scheduler = schedulerFactory.getScheduler();   
            //注册并进行调度   
            scheduler.scheduleJob(jobDetail, cronTrigger);   
            //scheduler.scheduleJob(jobDetail2, cronTrigger2);   
            //调度开始
            scheduler.start();
            log.info("job启动成功");
            
            
        } catch (Exception e) {
            e.printStackTrace();
            log.info("job启动失败，"+e.getMessage());
        }   
	}
	
	public static void main(String[] args) {
		TriggerRunner triger =new TriggerRunner();
		triger.runJob();
    }   


}
