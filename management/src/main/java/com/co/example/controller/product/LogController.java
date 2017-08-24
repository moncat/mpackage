package com.co.example.controller.product;

import java.util.Calendar;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.base.controller.BaseControllerHandler;
import com.co.example.entity.product.aide.ProductConstant;
import com.co.example.entity.product.aide.TBrLogQuery;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.product.TBrProductService;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("log")
public class LogController extends BaseControllerHandler<TBrLogQuery> {
	@Inject
	TBrProductService service;
	
	
	//定时
	
//	@Scheduled(fixedRate=10000)   //每隔10秒执行一次
//	@Scheduled(cron="0 12 16 * * ? ")  //每天16:12分执行一次
	public void testTasks() {    
		System.out.println("******************start****************");
		log.info("每10秒执行一次。开始……");
		service.doSomeThing();
		log.info("每10秒执行一次。结束。");
		System.out.println("******************end*******************");
	}
	
	
	@Scheduled(cron="0 0/10 * * * ? ") 
	public void testUpdateTasks() {    
		log.info("每10分钟执行一次。开始……");
		service.doSomeThing();
		log.info("每10分钟执行一次。结束。");
	}
	
	
	
	//定时爬取CFDA数据，定时爬取昨天的500条数据，如果有数据则爬取， 每天早上1点开始执行，并记录日志
	//暂时不开起
	//@Scheduled(cron="0 0 1 * * ? ")
	public void testOne() throws InterruptedException {
		//模拟定时执行
		TBrProductQuery query = new TBrProductQuery();
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.DAY_OF_YEAR, -1);
		
		String dateStr = DateFormatUtils.format(instance.getTime(), "yyyy-MM-dd");
		query.setConfirmDate(dateStr);
		long startMs = System.currentTimeMillis();
		long queryCount = service.queryCount(query);
		if(queryCount==0){
			System.out.println("在数据库中没有查询到昨天("+dateStr+")数据");
			log.info("No CFTA data in database at "+dateStr);
			service.saveLog(ProductConstant.PRODUCT_SOURCE_CFDA,  "No data in CFTA at "+dateStr, 0 ,"无",  null);
			for (int i = 1; i <= 500; i++) {
			Thread.sleep(1000);
			int dataFlg = service.addProductFromCFDA(i+"",dateStr);
			if(dataFlg == 1){
				long endMs = System.currentTimeMillis();
				Long interval=endMs-startMs;
				Long minute = interval/60000;
				log.info("***爬取结束***"+i+"***用时(分钟)***"+minute);
				System.out.println("***爬取结束***"+i+"***用时(分钟)***"+minute);
				service.saveLog(ProductConstant.PRODUCT_SOURCE_CFDA,  "Got data in CFTA at "+dateStr+"takes "+minute+" minutes", 0 ,"有",  null);
				break;
			}
			log.info("***继续爬取***"+i);
		}
		}else{
			// 有最新的数据
			System.out.println("查询到昨天数据");
			
		}
	
	}
	
	
    //定时爬取bevol数据 ，如果有数据则爬取， 每天早上3点开始执行，并记录日志
	//暂时不开起
	//@Scheduled(cron="0 0 3 * * ? ")
	
	
	//手动
	//手动抓取CFDA数据
	@ResponseBody
	@RequestMapping("cfda")
	public Map<String,String> getCFDA(Integer startPage1,Integer endPage1) throws InterruptedException {
		Map<String,String> map = Maps.newHashMap();
		if(startPage1 != null && endPage1 != null){
			long startMs = System.currentTimeMillis();
			for (int i = startPage1; i <= endPage1; i++) {
				int dataFlg = service.addProductFromCFDA(i+"","");
				if(dataFlg == 1){
					long endMs = System.currentTimeMillis();
					Long interval=endMs-startMs;
					Long minute = interval/60000;
					log.info("***爬取结束***"+i+"***用时(分钟)***"+minute);
					System.out.println("***爬取结束***"+i+"***用时(分钟)***"+minute);
					service.saveLog(ProductConstant.PRODUCT_SOURCE_CFDA,  "takes "+minute+" minutes", 0 ,"有",  null);
					break;
				}
				log.info("***cfda***"+i);
			}
			map.put("desc", "***cfda***success!!!");
		}else{
			map.put("desc", "***cfda***fail...");
			log.info("***cfda***NONE");
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping("bevol")
	public Map<String,String> getBEVOL(Integer startPage2,Integer endPage2,Integer category) throws InterruptedException {
		Map<String,String> map = Maps.newHashMap();
		if(startPage2 != null && endPage2 != null && category != null){
			for (int i = startPage2; i <= endPage2; i++) {
				service.addProductFromBEVOL(i,category);
				log.info("***bevol***"+i);
			}
			map.put("desc", "***bevol***success!!!");
		}else{
			log.info("***bevol***NONE");
			map.put("desc", "***bevol***fail...");
		}
		return map;
	}
	
}









