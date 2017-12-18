package com.co.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.service.expressage.TBrSfRegionService;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest9sfExpress {
	
	@Autowired
	TBrSfRegionService tBrSfRegionService;
	
	@Test
	public void getSFExpressData(){
		tBrSfRegionService.addSFExpressData();
	}
		
}




