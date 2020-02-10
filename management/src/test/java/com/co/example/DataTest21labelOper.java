package com.co.example;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.entity.label.TBrLabel;
import com.co.example.entity.label.TBrProductLabel;
import com.co.example.entity.label.aide.TBrProductLabelQuery;
import com.co.example.service.label.TBrLabelService;
import com.co.example.service.label.TBrProductLabelService;
import com.github.moncat.common.generator.id.NextId;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest21labelOper {
	
	@Autowired	
	TBrLabelService tBrLabelService;
		
	
	@Autowired	
	TBrProductLabelService tBrProductLabelService;
	
	@Test
	public void getData() throws InterruptedException{
		log.info("*************start****************");		
//		List<TBrLabel> queryList = tBrLabelService.queryList();
//		for (int i = 0; i < queryList.size(); i++) {
//			Long idNew = NextId.getId();
//			TBrLabel one = queryList.get(i);
//			TBrProductLabelQuery tBrProductLabelQuery = new TBrProductLabelQuery();
//			tBrProductLabelQuery.setLabelId(one.getId());
//			List<TBrProductLabel> plList = tBrProductLabelService.queryList(tBrProductLabelQuery);
//			for (TBrProductLabel two :plList) {
//				two.setLabelId(idNew);				
//			}
//			
//			tBrProductLabelService.updateInBatch(entityCollection);
//		}
		System.out.println(NextId.getId()); 
		log.info("*************end****************");
	}
	
	public static void main(String[] args) {
		for(int i = 0; i < 50; i++){
			System.out.println(NextId.getId()); 
			
		}
	}
}











