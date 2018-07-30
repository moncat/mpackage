package com.co.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.service.label.TBrLabelService;
import com.co.example.service.product.TBrAimService;
import com.co.example.service.product.TBrIngredientService;
import com.co.example.service.product.TBrProductService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest16 {
	
	@Autowired	
	TBrLabelService  tBrLabelService;
	
	@Autowired	
	TBrProductService  tBrProductService;
	
	@Autowired	
	TBrIngredientService  tBrIngredientService;
	
	@Autowired	
	TBrAimService  tBrAimService;
		
	@Test
	public void getData() throws InterruptedException{
		
	}
}




