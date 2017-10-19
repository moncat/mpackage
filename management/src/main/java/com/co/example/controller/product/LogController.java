package com.co.example.controller.product;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.EmailUtil;
import com.co.example.common.utils.PageReq;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.brand.TBrBrand;
import com.co.example.entity.brand.TBrProductBrand;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.ProductConstant;
import com.co.example.entity.product.aide.TBrLogQuery;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.brand.TBrProductBrandService;
import com.co.example.service.product.TBrProductService;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("log")
public class LogController extends BaseControllerHandler<TBrLogQuery> {
	
	@Resource
    TBrBrandService tBrBrandService;
	
	@Resource
	TBrProductService tBrProductService;
	
	@Resource
	TBrProductBrandService tBrProductBrandService;
	
	
	//定时
//	@Scheduled(fixedRate=10000)   //每隔10秒执行一次
//	@Scheduled(cron="0 12 16 * * ? ")  //每天16:12分执行一次
//	@Scheduled(cron="0 0/10 * * * ? ")  //每10秒执行一次
	public void testTasks() {    
		tBrProductService.doSomeThing();
	}
	
	/**
	 * 运维工作
	 * 1.检查同步药监局数据情况            正常运行
	 * 2.检查药监局数据品牌匹配情况   正常运行
	 * 3.获得新的实际生产企业（手动）  create_by  4 已匹配    2 未匹配     0 新数据
	 * 4.获得运营企业数据（手动）       尚未获得，待处理
	 * 5.全量维护企业法律诉讼，行政处罚的数据（手动）、
	 * 6.抓取天猫，淘宝等数据
	 */
	
	
	
	//定时爬取CFDA数据，定时爬取昨天的500条数据，如果有数据则爬取， 并记录日志
	@Scheduled(cron="0 00 16 * * ? ")  //每天16:00分执行一次
	public void testOne() throws InterruptedException {
		//模拟定时执行
		//查询到了最新数据开始获取
		TBrProductQuery query = new TBrProductQuery();
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.DAY_OF_YEAR, -1);	
		String dateStr = DateFormatUtils.format(instance.getTime(), "yyyy-MM-dd");
		log.info("开始执行定时器:"+ new Date());
//		EmailUtil.sendEmail("moncat@126.com", "BR系统开发人员", "数据同步", "数据同步定时器开始运行");
		query.setConfirmDate(dateStr);
		long startMs = System.currentTimeMillis();
		long queryCount = tBrProductService.queryCount(query);
		if(queryCount==0){
			System.out.println("在数据库中没有查询到昨天("+dateStr+")数据,需要爬取验证");
			log.info("在数据库中没有查询到昨天("+dateStr+")数据,需要爬取验证");
			tBrProductService.saveLog(ProductConstant.PRODUCT_SOURCE_CFDA,  "在数据库中没有查询到昨天("+dateStr+")数据,需要爬取验证", 0 ,"无",  null);
			for (int i = 1; i <= 500; i++) {
				Thread.sleep(1000);
				int dataFlg = tBrProductService.addProductFromCFDA(i+"",dateStr);
				if(dataFlg == 1){
					long endMs = System.currentTimeMillis();
					Long interval=endMs-startMs;
					Long minute = interval/60000;
					log.info("***爬取结束***"+i+"***用时(分钟)***"+minute);
					System.out.println("***爬取结束***"+i+"***用时(分钟)***"+minute);
					tBrProductService.saveLog(ProductConstant.PRODUCT_SOURCE_CFDA,  "***爬取结束***"+i+"***用时(分钟)***"+minute, 0 ,"有",  null);
					if(i>1){
						EmailUtil.sendEmail("moncat@126.com", "BR系统开发人员", "数据同步", "数据同步完毕，同步"+dateStr+"的数据,在第"+i+"页时结束，用时"+minute+"分钟");
						log.info("***开始执行品牌匹配***"+dateStr);
						brand(dateStr);
					}
					break;
				}
				log.info("***继续爬取***"+i);
			}
		}else{
			// 有最新的数据
			System.out.println("查询到昨天数据，不要重复爬取");
			log.info("查询到昨天数据，不要重复爬取");
		}
	
	}
	
	//定时抓取cfda数据后，开始执行品牌匹配
	public void brand(String dateStr) throws InterruptedException {
		List<TBrBrand> brands = tBrBrandService.queryByNameLength();
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setJoinBrandFlg(true);
		tBrProductQuery.setConfirmDate(dateStr);
		List<TBrProduct> tBrProductList = tBrProductService.queryList(tBrProductQuery);
		String productName = null;
		String brandName = null;
		Long brandId = 0l;
		int i = 0;
		try {
			if(CollectionUtils.isNotEmpty(tBrProductList)){
				for (TBrProduct tBrProduct : tBrProductList) {
					productName = tBrProduct.getProductName();
					i++;
					if(StringUtils.isNoneBlank(productName)){
						for (TBrBrand brand : brands) {
							brandName = brand.getName();
							brandId = brand.getId();
							if(StringUtils.isNoneBlank(brandName)){
								if(productName.contains(brandName)){
									TBrProductBrand tBrProductBrand = new TBrProductBrand();
									tBrProductBrand.setBrandId(brandId);
									tBrProductBrand.setProductId(tBrProduct.getId());
									tBrProductBrand.setCreateTime(new Date());
									tBrProductBrandService.add(tBrProductBrand);
									log.info("定时品牌匹配成功！ "+productName+"["+brandName+"]");
									tBrProductService.saveLog(Constant.YES, brandName, i, productName, null);
									break;
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			tBrProductService.saveLog(Constant.NO, brandName, i, productName, e);
			e.printStackTrace();
		}
		log.info("***定时品牌匹配完毕***"+i);
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
				Thread.sleep(1200);
				int dataFlg = tBrProductService.addProductFromCFDA(i+"","");
				if(dataFlg == 1){
					long endMs = System.currentTimeMillis();
					Long interval=endMs-startMs;
					Long minute = interval/60000;
					log.info("***爬取结束***"+i+"***用时(分钟)***"+minute);
					System.out.println("***爬取结束***"+i+"***用时(分钟)***"+minute);
					tBrProductService.saveLog(ProductConstant.PRODUCT_SOURCE_CFDA,  "takes "+minute+" minutes", 0 ,"有",  null);
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
				tBrProductService.addProductFromBEVOL(i,category);
				log.info("***bevol***"+i);
			}
			map.put("desc", "***bevol***success!!!");
		}else{
			log.info("***bevol***NONE");
			map.put("desc", "***bevol***fail...");
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value="brand",method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String,String> brand( String startDateStr, Integer startPage3) throws InterruptedException {
		Map<String,String> map = Maps.newHashMap();
		if(startPage3 != null){
			List<TBrBrand> brands = tBrBrandService.queryByNameLength();
			TBrProductQuery tBrProductQuery = new TBrProductQuery();
			if(StringUtils.isNoneBlank(startDateStr)){
				tBrProductQuery.setGreaterThanConfirmDate(startDateStr);
			}
			tBrProductQuery.setJoinBrandFlg(true);
			Page<TBrProduct> productPageList  = null;
			int k = startPage3;
			PageReq pageReq = new PageReq();
			pageReq.setPageSize(1000);
			pageReq.setPage(1);
			String productName = null;
			for (; k < brands.size(); k++) {
				Thread.sleep(2000);
				log.info("程序运行中k..."+k);
				long productCount = tBrProductService.queryCount(tBrProductQuery);
				log.info("当前还有未匹配的产品个数"+productCount);
				TBrBrand brand = brands.get(k);
				String brandName  = brand.getName();
				Long brandId = brand.getId();
				Long tmp = 0l;
				if(StringUtils.isNotBlank(brandName)){
					while (productCount>0) {
						productPageList = tBrProductService.querySimplePageList(tBrProductQuery, pageReq);
						if(productCount<1000){
							productCount = tBrProductService.queryCount(tBrProductQuery);
							if(productCount == tmp){
							}else{
								tmp = productCount;
							}
						}
						List<TBrProduct> content = productPageList.getContent();
						if(CollectionUtils.isNotEmpty(content)){
							for (TBrProduct tBrProduct : content) {
								productName = tBrProduct.getProductName();
								if(StringUtils.isNotBlank(productName)){
									if(productName.contains(brandName)){
										TBrProductBrand tBrProductBrand = new TBrProductBrand();
										tBrProductBrand.setBrandId(brandId);
										tBrProductBrand.setProductId(tBrProduct.getId());
										tBrProductBrand.setCreateTime(new Date());
										tBrProductBrandService.add(tBrProductBrand);
										log.info("匹配成功！ "+productName+"["+brandName+"]");
										tBrProductService.saveLog(Constant.YES, brandName, k, productName, null);
										break;
									}
								}
								productCount--;	
							}
						}
					}
				}else{
					log.info("品牌名为空");
				}
			}
			map.put("desc", "***brand***success!!!");
		}else{
			log.info("***brand***NONE");
			map.put("desc", "***brand***fail...");
		}
		return map;
	}
	
	
	private String getRealEn(String en) {
		String reg = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(reg);  
		Matcher mat=pat.matcher(en); 
		en = mat.replaceAll("");
		en = en.replace("（", "").replace("）", "");
		return en.toLowerCase();
	}
	
	public static void main(String[] args) {
		String str = "123abc（你好）efc";
    	String reg = "[\u4e00-\u9fa5]";
    	Pattern pat = Pattern.compile(reg);  
    	Matcher mat=pat.matcher(str); 
    	str = mat.replaceAll("");
    	str = str.replace("（", "").replace("）", "");
    	System.out.println("去中文后:"+str);
	}
	
	
	
}









