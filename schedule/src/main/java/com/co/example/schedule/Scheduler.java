package com.co.example.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Scheduler {
	
	@Scheduled(cron="0 0/1 * * * ?") //每分钟执行一次
	public void statusCheck() {    
		log.info("每分钟执行一次。开始……");
		//statusTask.healthCheck();
		log.info("每分钟执行一次。结束。");
	}  

	@Scheduled(fixedRate=20000)
	public void testTasks() {    
		log.info("每20秒执行一次。开始……");
		//statusTask.healthCheck();
		log.info("每20秒执行一次。结束。");
	}  
}