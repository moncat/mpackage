package com.co.example;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.entity.brand.TBrBrand;
import com.co.example.entity.brand.TBrProductBrand;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.brand.TBrProductBrandService;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.solr.SolrService;
import com.co.example.utils.BaseDataUtil;
import com.google.common.io.Files;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest2GetBrand {
	
	@Resource
    TBrBrandService service;
	

	//太平洋女性
	public static String  BRAND_LIST_PCLADY_URL = "http://cosme.pclady.com.cn/brand_list.html";
	//yoka时尚
	public static String  BRAND_LIST_YOKA_URL = "http://brand.yoka.com/cosmetics/brandlist.htm";
	//女人志
	public static String  BRAND_LIST_ONLYLADY_URL = "http://hzp.onlylady.com/brand.html";
	//腾讯女性
	public static String  BRAND_LIST_QQLADY_URL = "http://lady.qq.com/product/pinpai.htm";
	//39化妆品库
	public static String  BRAND_LIST_39_URL = "http://hzpk.39.net/allbrand.html";
	//凤凰时尚
	public static String  BRAND_LIST_IFENG_URL = "http://cosmetics.ifeng.com/brand_all.html";
	//瑞丽网
	public static String  BRAND_LIST_RAYLI_URL = "http://hzp.rayli.com.cn/brand.html";
	//网易女人
	public static String  BRAND_LIST_163LADY_URL = "http://cosmetic.lady.163.com/brand/";

	
//	@Test
	public void test() throws InterruptedException {
//		syncData(BRAND_LIST_PCLADY_URL,"gb2312",".sBrand li");
//		syncData(BRAND_LIST_YOKA_URL,"utf-8","#fic_main1 dd");
//		syncData(BRAND_LIST_163LADY_URL,"utf-8",".sect-main.tabs-panel2 li"); 
//		syncData(BRAND_LIST_IFENG_URL,"utf-8",".box1055 .blockBg td");
//		syncData(BRAND_LIST_39_URL,"gb2312",".brandBox li");
//		syncData(BRAND_LIST_RAYLI_URL,"utf-8",".g_zmpic li");
//		syncData(BRAND_LIST_QQLADY_URL,"utf-8",".uChar .c a");
		syncData(BRAND_LIST_ONLYLADY_URL,"gbk","#sortByLetter .brand");
		
	}

	
	public void syncData(String url,String encode,String tags) throws InterruptedException{
		Document doc = null;
		try {
			doc = Jsoup.parse(new URL(url).openStream(), encode, url);
//			String postData = HttpUtils.postData(url, "", "gb2312");
//			doc = Jsoup.parse(postData);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Elements sBrand=doc.select(tags);
		int size = sBrand.size();
		System.out.println("***需要抓取***"+size);
		log.info("***需要抓取***"+size);
		for (int i = 0; i < size; i++) {
			Element element = sBrand.get(i);
//			Thread.sleep(500);
			log.info("***正在抓取***"+i);
			System.out.println("***正在抓取***"+i);
//			service.addBrandFromPclady(element);
//			service.addBrandFromYOKA(element);
//			service.addBrandFrom163LADY(element);
//			service.addBrandFromIfeng(element);
//			service.addBrandFrom39(element);
//			service.addBrandFromRAYLI(element);
//			service.addBrandFromQQLADY(element);
			service.addBrandFromONLYLADY(element);
		}
		System.out.println("***抓取完毕***");
		log.info("***抓取完毕***");
	}
	
	public static void main(String[] args) {
	}
	
	
	@Inject
	TBrProductService tBrProductService;
	
	@Inject
	TBrBrandService tBrBrandService;

	@Inject
	TBrProductBrandService tBrProductBrandService;
	
	@Inject
	SolrService solrService;
	
	/**
	 * 2019年11月21日,完善功能，优先级放后，先处理基础增量数据。
	 * 从王永美抓取的产品品牌关联信息xls，补充数据库t_br_product_brand
	 */
	@Test
	public void getPBFromFile(){
		String bcName = "";
		String beName = "";
		String pName = "";
		String sn = "";
		Long pid = 0l;
		Long bid = 0l;
		TBrBrand tBrBrand;
		int suc = 0;
		List<TBrProduct> pList =  null;
				String bcNameTmp = "";
		String beNameTmp = "";
		Long bidTmp = 0l;
		TBrProductQuery  tmp =null;
				
		try {
			List<String> readLines = Files.readLines(new File("D:\\Desktop\\t_br_brand_product.txt"), 
					Charset.forName("gbk"));
			log.info("size:"+readLines.size());
			for (int i = 61325; i < readLines.size(); i++) {
				String str = readLines.get(i);
//				Thread.sleep(1000l);
				String[] split = str.split(",");
				try {
					pName =  split[1];
					sn =  split[2];
					bcName = split[0];
					beName =  split[3];
				} catch (Exception e) {
					log.info("字段长度不足");
//					e.printStackTrace();
				}
				//根据名称查产品
				if(StringUtils.isNotBlank(pName)){
					TBrProductQuery query = new TBrProductQuery();
					query.setProductNameLike(pName);
					pList = tBrProductService.queryList(query);
				}
				//根据备案号查产品
				if(pList.size() == 0 && StringUtils.isNotBlank(sn)){
					TBrProductQuery query = new TBrProductQuery();
					query.setApplySn(sn);
					pList = tBrProductService.queryList(query);
				}
				//pid 多个      
				if(pList.size() != 0){
					//计算品牌id
					log.info("查询到了"+pName);
					if(bcName.equals(bcNameTmp) || beName.equals(beNameTmp)){
						bid = bidTmp;
					}
					if(bid == 0 && StringUtils.isNotBlank(bcName)){
						TBrBrand query = new TBrBrand();
						query.setName(bcName);
						List<TBrBrand> bList = tBrBrandService.queryList(query);
						if(bList.size()>0){
							tBrBrand = bList.get(0);
							bid = tBrBrand.getId();
							bcNameTmp = bcName;
							bidTmp = bid;
						}
					}
					if(bid == 0 && StringUtils.isNotBlank(beName)){
						TBrBrand query = new TBrBrand();
						query.setNameEn(beName);
						List<TBrBrand> bList = tBrBrandService.queryList(query);
						if(bList.size()>0){
							tBrBrand = bList.get(0);
							bid = tBrBrand.getId();
						}
					}
					if(bid != 0){
						log.info("查询到了品牌"+bcName+"("+beName+")"+"开始关联");
						//查看是否关联，未关联则关联。
						for(TBrProduct p :pList){
							pid = p.getId();
							TBrProductBrand tBrProductBrand = new TBrProductBrand();
							tBrProductBrand.setBrandId(bid);
							tBrProductBrand.setProductId(pid);
							long queryCount = tBrProductBrandService.queryCount(tBrProductBrand);
							
							if(queryCount>0){
								log.info("已经有关联关系，不必再进行关联。");
							}else{
								BaseDataUtil.setDefaultData(tBrProductBrand);
								tBrProductBrandService.add(tBrProductBrand);
								//做品牌冗余
								tmp = new TBrProductQuery();
								tmp.setId(pid);
								tmp.setProductBrandId(bid);
								tmp.setProductBrandName(bcName);
								tBrProductService.updateByIdSelective(tmp);
//								solrService.updateByIdSelective(pid+"", "brands", bcName);
								log.info("关联成功"+suc++);	
							}
						}
					}
				}
				bid = 0l;
				pid = 0l;
				log.info("已经匹配到i行="+i);
			}
		} catch (IOException e) {
			log.info("getPBFromFile error!");
			e.printStackTrace();
		}
	}
	
}







