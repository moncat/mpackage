package com.co.example;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.entity.product.aide.ConfirmVo;
import com.co.example.entity.product.aide.TBrEnterpriseCountVo;
import com.co.example.entity.product.aide.TBrIngredientCountVo;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrIngredientService;
import com.co.example.service.product.TBrProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest19confirm {


	@Autowired
	TBrProductService tBrProductService;
	
	
	@Autowired
	TBrIngredientService tBrIngredientService;
	
	@Autowired
	TBrEnterpriseService tBrEnterpriseService;

	/**
	 * 批量处理 是否是生产企业
	 * @throws InterruptedException
	 */
	 
	public void getData() throws InterruptedException{
		log.info("*************start****************");
		 List<ConfirmVo>  queryConfirmData = tBrProductService.queryConfirmData(10,2);
		System.out.println(queryConfirmData);
		log.info("*************end****************");
	}
	
	/**
	 * 新增成分所包含的产品数量排名
	 */
	public void getData1()  {
		log.info("*************start****************");
		List<TBrIngredientCountVo> queryIngredientCount = tBrIngredientService.queryIngredientCount("2019-04-23");
		System.out.println(queryIngredientCount);
		log.info("*************end****************");
	}
	
	
	/**
	 * 新增企业所包含的产品数量排名
	 */
	@Test
	public void getData2()  {
		log.info("*************start****************");
		List<TBrEnterpriseCountVo> queryEnterpriseCount = tBrEnterpriseService.queryEnterpriseCount("2019-08-03");
		System.out.println(queryEnterpriseCount);
		log.info("*************end******************");
	}
}
