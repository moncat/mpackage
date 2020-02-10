package com.co.example;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.solr.SolrService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest17Solr {
	
	@Autowired	
	SolrService solrService;
		
	@Test
	public void getData() throws InterruptedException{
		log.info("*************start****************");
		TBrProductQuery query = new TBrProductQuery();
//		query.setNormalType(4);
//		query.setNormal("百恩|冬之恋|婷美");
		
//		query.setNormalType(3);
//		query.setNormal("深圳|上海");
		
//		query.setNormalType(2);
//		query.setNormal("烟酸|仙人掌");
		
		query.setNormalType(1);
		query.setNormal("精华液|冬之恋|婷美"); //productName:(*精华液* OR *冬之恋* OR *婷美*)
//		query.setEIds("2320609604927488,2320609692598272"); //beid:(2320609604927488 OR 2320609692598272)
//		query.setPeIds("2320609604927488,2320609692598272");//peids:(2320609604927488 OR 2320609692598272)
//		query.setLIds("3359758924578816,3359760927539200"); //lids:(*3359758924578816* *3359760927539200* )
//		query.setIIds("2320609800454144,2320609777156096"); //iids:(*2320609800454144*+OR+*2320609777156096*)
		Map<String, Object> map = solrService.querySolr2(query, 0, 30);
		System.out.println(JSON.toJSONString(map)); 
		log.info("*************end****************");
	}
}
