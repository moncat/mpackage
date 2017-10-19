package com.co.example;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.common.utils.PageReq;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.ProductConstant;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.entity.spec.TBrSpecKey;
import com.co.example.service.product.TBrProductImageService;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.product.TBrProductSpecService;
import com.co.example.service.spec.TBrSpecKeyService;
import com.co.example.service.spec.TBrSpecValueService;
import com.co.example.simulateLogin.TaoBaoLoginUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 开始抓取京东天猫数据
 * @author zyl
 *
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest8TmallAndJD {

	@Autowired
	TBrProductService tBrProductService;
	
	@Autowired
	TBrProductImageService tBrProductImageService;
	
	@Autowired
	TBrProductSpecService tBrProductSpecService;
	
	@Autowired
	TBrSpecKeyService tBrSpecKeyService;
	
	@Autowired
	TBrSpecValueService tBrSpecValueService;
	
	
	// https://search.jd.com/Search?keyword=理肤泉清痘净肤舒缓洁面啫喱&enc=utf-8
	// https://list.tmall.com/search_product.htm?q=理肤泉清痘净肤舒缓洁面啫喱        gbk

	String url ="";
	String encode = "utf-8";
	
	WebDriver chrome = null;
	
	 {
		
//		try {
//			chrome =  TaoBaoLoginUtil.login();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
	@Test
	public void getData() throws InterruptedException {
		
		Byte k = ProductConstant.PRODUCT_SOURCE_JD;
//		Byte k = ProductConstant.PRODUCT_SOURCE_TMALL;
		//查询未匹配的数据
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		PageReq pageReq = new PageReq();
		pageReq.setPage(1);
		pageReq.setPageSize(500);
		if(k == ProductConstant.PRODUCT_SOURCE_JD){
			tBrProductQuery.setJdUrl("0");
			log.info("***抓取京东数据***");
		}else if(k == ProductConstant.PRODUCT_SOURCE_TMALL){
			tBrProductQuery.setTmallUrl("0");
			log.info("***抓取天猫数据***");
		}else{
			log.info("***数据来源设置错误***");
			return ;
		}
		
		long queryCount = tBrProductService.queryCount(tBrProductQuery);
		log.info("***剩余待抓取***"+queryCount);
		while(queryCount>0){
			Page<TBrProduct> pageList = tBrProductService.querySimplePageList(tBrProductQuery , pageReq);
			List<TBrProduct> content = pageList.getContent();
			List<TBrSpecKey> tbrSpecKeyList = tBrSpecKeyService.queryList();
			for (TBrProduct tBrProduct : content) {
				Thread.sleep(1000);
				//该抓取业务将在TBrProductSpecService编写
				tBrProductSpecService.addData(tBrProduct, k,tbrSpecKeyList,chrome);
			}
			queryCount = tBrProductService.queryCount(tBrProductQuery);
			log.info("***剩余待抓取***"+queryCount);
		}
		
		log.info("***抓取完毕***");
	}
	
	
//	public static void main(String[] args) {
//		for (int i = 0; i < 15; i++) {
//			System.out.println(NextId.getId());
//			
//		}
//	}
//	
}




















