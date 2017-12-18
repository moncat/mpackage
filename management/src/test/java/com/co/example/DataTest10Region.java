package com.co.example;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.entity.region.TBrRegion;
import com.co.example.service.region.TBrRegionService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest10Region {
	
	@Autowired
	TBrRegionService tBrRegionService;
	
	@Test
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
		
}
