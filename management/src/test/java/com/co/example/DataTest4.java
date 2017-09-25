package com.co.example;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.PageReq;
import com.co.example.entity.brand.TBrBrand;
import com.co.example.entity.brand.TBrProductBrand;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.brand.TBrProductBrandService;
import com.co.example.service.product.TBrProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest4 {
	
	@Resource
    TBrBrandService tBrBrandService;
	
	@Resource
	TBrProductService tBrProductService;
	
	@Resource
	TBrProductBrandService tBrProductBrandService;
	
	
	@Test
	public void test(int k) throws InterruptedException {
		List<TBrBrand> brands = tBrBrandService.queryByNameLength();
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setJoinBrandFlg(true);
		Page<TBrProduct> productPageList  = null;
		int pageSize = 10;
		long pageCount = 0l;
		ExecutorService  executor = null;
		for (; k < brands.size(); k++) {
			Thread.sleep(2000);
			int m = k; 
			executor = Executors.newCachedThreadPool();// 启用多线程
			long productCount = tBrProductService.queryCount(tBrProductQuery);
			log.info("当前还有未匹配的产品个数"+productCount);
			if(productCount>60000){
				pageSize = 10000;
			}else if(productCount>10000){
				pageSize = 2000;
			}else if(productCount>5000){
				pageSize = 1000;
			}else if(productCount<=5000){
				pageSize = 200;
			}else if(productCount<100){
				pageSize = 10;
			}
			pageCount = productCount/pageSize+1l;
			PageReq pageReq = new PageReq();
			pageReq.setPageSize(pageSize);
			TBrBrand brand = brands.get(m);
			String brandName  = brand.getName();
			Long brandId = brand.getId();
			if(StringUtils.isNoneBlank(brandName)){
				for (int i = 0; i < pageCount; i++) {
					pageReq.setPage(i);
					productPageList = tBrProductService.queryPageList(tBrProductQuery, pageReq);
					Page<TBrProduct> productPageListTmp = productPageList;
					executor.execute(new Runnable() {
						@Override
						public void run() {
							String productName = null;
							try {
								List<TBrProduct> content = productPageListTmp.getContent();
								if(CollectionUtils.isNotEmpty(content)){
									for (TBrProduct tBrProduct : content) {
										productName = tBrProduct.getProductName();
										log.info("与品牌"+m+"["+brandName+"]匹配中...");
										if(productName.contains(brandName)){
											TBrProductBrand tBrProductBrand = new TBrProductBrand();
											tBrProductBrand.setBrandId(brandId);
											tBrProductBrand.setProductId(tBrProduct.getId());
											tBrProductBrandService.add(tBrProductBrand);
											log.info("匹配成功！ "+productName+"["+brandName+"]");
											tBrProductService.saveLog(Constant.YES, brandName, m, productName, null);
										}
									}
								}
							} catch (Exception e) {
								tBrProductService.saveLog(Constant.NO, brandName, m, productName, e);
								e.printStackTrace();
							}
						}
					});
				}
			}else{
				log.info("品牌名为空");
			}
			// 启动一次顺序关闭，执行以前提交的任务，但不接受新任务。  
			executor.shutdown();  
		    try {  
		      // 请求关闭、发生超时或者当前线程中断，无论哪一个首先发生之后，都将导致阻塞，直到所有任务完成执行  
		      // 设置最长等待10秒  
		    	executor.awaitTermination(10, TimeUnit.SECONDS);  
		    } catch (InterruptedException e) {  
		      tBrProductService.saveLog(Constant.NO, brandName, m, "", e);
		      e.printStackTrace();  
		    }  
			
		}
	}

	
}




