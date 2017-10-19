package com.co.example;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.common.utils.HttpUtils;
import com.co.example.service.brand.TBrBrandService;
import com.google.common.collect.Lists;

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

	
	@Test
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
		ArrayList<String> list = Lists.newArrayList();
		
	}
	
	
}

