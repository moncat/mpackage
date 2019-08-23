package com.co.example;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

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
		Map<String, Object> map = solrService.queryProductSolr(query, 0, 30);
		System.out.println(map); 
		log.info("*************end****************");
	}
}
