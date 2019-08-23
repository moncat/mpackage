package com.co.example;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.common.constant.Constant;
import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.aide.TBrProductEnterpriseQuery;
import com.co.example.entity.product.aide.TBrProductOperEnterpriseQuery;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrProductEnterpriseService;
import com.co.example.service.product.TBrProductOperEnterpriseService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest18enterprise {

	@Autowired
	TBrEnterpriseService tBrEnterpriseService;

	@Autowired
	TBrProductEnterpriseService tBrProductEnterpriseService;
	
	
	@Autowired
	TBrProductOperEnterpriseService tBrProductOperEnterpriseService;

	/**
	 * 批量处理 是否是生产企业
	 * @throws InterruptedException
	 */
	@Test
	public void getData() throws InterruptedException{
		log.info("*************start****************");
		List<TBrEnterprise> queryList = tBrEnterpriseService.queryList();
//		List<Long> collect = queryList.stream().map(c->c.getId()).collect(Collectors.toList());
		TBrEnterprise one;
		log.info("***size**"+queryList.size());
		 for (int i = 0; i < queryList.size(); i++) {
			 one = queryList.get(i);
			 log.info("***i**"+i);
			 Long id = one.getId();
			 TBrProductEnterpriseQuery tBrProductEnterpriseQuery = new TBrProductEnterpriseQuery();
			 tBrProductEnterpriseQuery.setEnterpriseId(id);
			 long queryCount = tBrProductEnterpriseService.queryCount(tBrProductEnterpriseQuery);
			 if(queryCount>0){
				 TBrEnterprise tBrEnterpriseUpdate = new TBrEnterprise();
				 tBrEnterpriseUpdate.setId(id);
				 tBrEnterpriseUpdate.setIsProduct(Constant.YES);
				 tBrEnterpriseService.updateByIdSelective(tBrEnterpriseUpdate);
			 }
			
		}	 
		
		log.info("*************end****************");
	}
	
	
	/**
	 * 批量处理 是否是运营企业 
	 * @throws InterruptedException
	 */
	@Test
	public void getData2() throws InterruptedException{
		log.info("*************start****************");
		List<TBrEnterprise> queryList = tBrEnterpriseService.queryList(); 
		TBrEnterprise one;
		log.info("***size**"+queryList.size());
		 for (int i = 0; i < queryList.size(); i++) {
			 one = queryList.get(i);
			 log.info("***i2**"+i);
			 Long id = one.getId();
			 TBrProductOperEnterpriseQuery query = new TBrProductOperEnterpriseQuery();
			 query.setEnterpriseId(id);
			 long queryCount = tBrProductOperEnterpriseService.queryCount(query);
			 if(queryCount>0){
				 log.info("***i**"+i+"***yes");
				 TBrEnterprise tBrEnterpriseUpdate = new TBrEnterprise();
				 tBrEnterpriseUpdate.setId(id);
				 tBrEnterpriseUpdate.setIsBus(Constant.YES);
				 tBrEnterpriseService.updateByIdSelective(tBrEnterpriseUpdate);
			 }
			
		}	 
		
		log.info("*************end****************");
	}
}
