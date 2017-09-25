package com.co.example;

import java.util.Calendar;
import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.common.utils.HttpUtils;
import com.co.example.entity.product.aide.ProductConstant;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.product.TBrProductService;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest2 {
	
	@Resource
    private TBrProductService service;
	
//	public void test1() throws InterruptedException {
//		//模拟定时执行
//		TBrProductQuery query = new TBrProductQuery();
//		Calendar instance = Calendar.getInstance();
//		instance.add(Calendar.DAY_OF_YEAR, -1);
//		
//		String dateStr = DateFormatUtils.format(instance.getTime(), "yyyy-MM-dd");
//		query.setConfirmDate(dateStr);
//		long startMs = System.currentTimeMillis();
//		long queryCount = service.queryCount(query);
//		if(queryCount==0){
//			System.out.println("在数据库中没有查询到昨天("+dateStr+")数据");
//			log.info("No CFTA data in database at "+dateStr);
//			service.saveLog(ProductConstant.PRODUCT_SOURCE_CFDA,  "No data in CFTA at "+dateStr, 0 ,"无",  null);
//			for (int i = 1; i <= 500; i++) {
//			//Thread.sleep(1000);
//			int dataFlg = service.addProductFromCFDA(i+"",dateStr);
//			if(dataFlg == 1){
//				long endMs = System.currentTimeMillis();
//				Long interval=endMs-startMs;
//				Long minute = interval/60000;
//				log.info("***爬取结束***"+i+"***用时(分钟)***"+minute);
//				System.out.println("***爬取结束***"+i+"***用时(分钟)***"+minute);
//				service.saveLog(ProductConstant.PRODUCT_SOURCE_CFDA,  "Got data in CFTA at "+dateStr+"takes "+minute+" minutes", 0 ,"无",  null);
//				break;
//			}
//			log.info("***继续爬取***"+i);
//		}
//		}else{
//			// 有最新的数据
//			System.out.println("查询到昨天数据");
//			
//		}
		

//	}
	
	@Test
	public void test2() throws InterruptedException {
		for (int i = 1009; i <= 1020; i++) {
			service.addProductFromCFDA(i+"","");
			log.info("******"+i);
		}
		
	}
	
	
	public static void main(String[] args) {
		HashMap<String, String> params = Maps.newHashMap();
		params.put("on", "true");
		params.put("page", "1"); //67133
		params.put("pageSize", "15");
		String text = HttpUtils.postData(ProductConstant.CFDA_PRODUCT_URL, params);
		System.out.println(text);
	}
	
	
	@Test
	public void test3() throws InterruptedException {
		service.addProductFromBEVOL(6,8);
		
	}
	
//	@Test
//	public void test4() throws InterruptedException {
//		service.doSomeThing();
//		
//	}
	
	
	
}

