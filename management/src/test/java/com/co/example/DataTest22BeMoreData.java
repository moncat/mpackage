package com.co.example;

import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.common.utils.JsoupUtil;
import com.co.example.entity.product.aide.BeVo;
import com.co.example.service.product.TBrProductService;
import com.co.example.simulateLogin.BrowserFactory;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest22BeMoreData {
	
	@Autowired	
	TBrProductService  tBrProductService;
	
	WebDriver chrome ;
	
	
	
	
	public void  text(){
		try {
			Document doc = JsoupUtil.getDoc("http://125.35.6.80:8181/ftban/itownet/hzp_ba/fw/pz.jsp?processid=20181120110128rirce");
			String text = doc.text();
			log.info("抓取完毕"+text);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void getData() {
		try {
//			chrome = BrowserFactory.getFireFox();
			chrome = BrowserFactory.getChrome();
//			Navigation navigate = chrome.navigate();
//			navigate.refresh();
			chrome.get("http://125.35.6.80:8181/ftban/fw.jsp");
//			chrome.get("http://www.baidu.com");
			log.info("睡眠40s");
			Thread.sleep(40*1000);		
//			chrome.findElement(By.id("searchtext")).sendKeys("唯恩诗");
//			chrome.findElement(By.id("searchInfo")).click();
			String data = chrome.getPageSource();
			log.info("抓取完毕"+data);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}







