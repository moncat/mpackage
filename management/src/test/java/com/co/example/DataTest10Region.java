package com.co.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.entity.product.TBrEnterprise;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.region.TBrRegionService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest10Region {
	
	@Autowired
	TBrRegionService tBrRegionService;
	
	@Autowired
	TBrEnterpriseService tBrEnterpriseService;
	
//	@Test
	public void getRegionData(){
		try {
			tBrRegionService.crawRegionData();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
//		List<TBrRegion> queryList = tBrRegionService.queryList();
//		for (TBrRegion tBrRegion : queryList) {
//			try {
//				tBrRegionService.findData(tBrRegion);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}
	//根据 产品名称对比
	@Test
	public void setEnterpriseRegion(){
		tBrRegionService.setEnterpriseRegion();
	}
	//根据产品备案缩写对比
//	@Test
	public void setEnterpriseRegionByShort(){
		tBrRegionService.setEnterpriseRegionByShort();
	}
	
	
//	@Test
	public void setEnterpriseRegionByAll(){
		TBrEnterprise tBrEnterprise = tBrEnterpriseService.queryById(3464067331751936l);
		tBrRegionService.setEnterpriseRegionByAll(tBrEnterprise);
		log.info(tBrEnterprise.toString());
		log.info(tBrEnterprise.toString());
	}
}

















