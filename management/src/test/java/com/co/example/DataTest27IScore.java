package com.co.example;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.entity.product.TBrProduct;
import com.co.example.service.product.TBrIngredientService;
import com.co.example.service.product.TBrProductService;
import com.github.moncat.common.utils.PageReq;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest27IScore {
	

	@Inject
	TBrProductService  service;
	
	@Inject
	TBrIngredientService  tBrIngredientService;
	
	
	PageReq pageReq = new PageReq();
	Long id = 0l;
	String productName  = null;
	Float productScore = 0f;
	TBrProduct update = null;
	@Test
	public void setIScore(){
		TBrProduct query = new TBrProduct();
		query.setItemOrder(Byte.valueOf("0"));
		long queryCount = service.queryCount(query);
		try {
			while(queryCount>0){
				pageReq.setPageSize(1000);
				Page<TBrProduct> queryPageList = service.queryPageList(query, pageReq);
				for (TBrProduct tBrProduct : queryPageList) {
					id = tBrProduct.getId();
					productName = tBrProduct.getProductName();
					productScore = tBrIngredientService.getProductScore(id);
					update = new TBrProduct();
					update.setId(id);
					update.setIScore(BigDecimal.valueOf(productScore));
					update.setItemOrder(Byte.valueOf("1"));
					service.updateByIdSelective(update);
					
				}
				log.info("num1000--id:"+id+",name:"+productName+",iscore:"+productScore);
//				queryCount = service.queryCount(query);
				queryCount = queryCount-1000;
				log.info("queryCount:"+queryCount);
			}
		} catch (NumberFormatException e) {
			log.info("error!!!!!!!!!!id:"+id+",name:"+productName+",iscore:"+productScore);
			e.printStackTrace();
		}
		log.info("set over");
	}
		
	
}













