package com.co.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest7 {
	@Autowired  
	Environment env;  
	
	@Test
	public void test1(){
		String property = env.getProperty("spring.datasource.username");
		log.info(property);
	}
	

}