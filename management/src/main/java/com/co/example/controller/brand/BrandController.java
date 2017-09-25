package com.co.example.controller.brand;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.brand.aide.TBrBrandQuery;
import com.co.example.service.brand.TBrBrandService;

@Controller
@RequestMapping("brand")
public class BrandController extends BaseControllerHandler<TBrBrandQuery>{
	
	@Autowired
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
	
	
	
	@ResponseBody
	@RequestMapping("sync")
	public String sync() throws InterruptedException{
		syncData(BRAND_LIST_PCLADY_URL,"gb2312",".sBrand li");
		syncData(BRAND_LIST_YOKA_URL,"utf-8","fic_main1 dd");
		syncData(BRAND_LIST_ONLYLADY_URL,"gbk","sortByLetter .brand");
		syncData(BRAND_LIST_QQLADY_URL,"utf-8","uChar .c");
		syncData(BRAND_LIST_39_URL,"gb2312",".brandBox li");
		syncData(BRAND_LIST_IFENG_URL,"utf-8",".box1055 .blockBg td");
		syncData(BRAND_LIST_RAYLI_URL,"utf-8",".g_zmpic li");
		syncData(BRAND_LIST_163LADY_URL,"utf-8",".sect-main.tabs-panel2 li");
		
		
		return "200";
	}			
	


	public void syncData(String url,String encode,String tags) throws InterruptedException{
		Document doc = null;
		try {
			doc = Jsoup.parse(new URL(url).openStream(), encode, url);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Elements sBrand=doc.select(tags);
		for (int i = 0; i < sBrand.size(); i++) {
			Element element = sBrand.get(i);
			Thread.sleep(2000);
			service.addBrandFromPclady(element);
			service.addBrandFromYOKA(element);
			service.addBrandFromONLYLADY(element);
			service.addBrandFromQQLADY(element);
			service.addBrandFrom39(element);
			service.addBrandFromIfeng(element);
			service.addBrandFromRAYLI(element);
			service.addBrandFrom163LADY(element);
		}
	}
	

}
