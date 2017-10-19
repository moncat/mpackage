package com.co.example.controller.product;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("log2")
public class Log2Controller {
	
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
	
	String url ="";
	String encode = "utf-8";
	
	WebDriver chrome = null;
	
	@RequestMapping(value = "/oper", method = { RequestMethod.GET, RequestMethod.POST })
	public void oper() throws InterruptedException {

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
		
}
