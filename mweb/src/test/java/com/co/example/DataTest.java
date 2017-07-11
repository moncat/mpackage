package com.co.example;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.entity.user.TUsers;
import com.co.example.service.user.TUsersService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleMwebApp.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest {
	
	
	@Resource
    private TUsersService tUsersService;
	
	@Test
	public void testOne() {
		//TUsers queryByLoginName = tUsersService.queryByLoginName("z");
		//System.out.println(queryByLoginName);
		TUsers queryById = tUsersService.queryById(1);
		System.out.println("111111111");
	}
	
}

