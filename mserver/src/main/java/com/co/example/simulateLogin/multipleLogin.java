package com.co.example.simulateLogin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.extern.slf4j.Slf4j;

/**
 * 1、本地已经按照google chrome 2、本机已登录qq客户端 3、微博已经绑定qq登录
 * 4、使用qq登录微博，然后可以通过微博授权登录其他qq不能登录的网站如淘宝天猫
 * 
 * @author zyl
 *
 */
@Slf4j
public class multipleLogin {

	static String weiboConnQQUrl = "https://passport.weibo.com/othersitebind/authorize?entry=miniblog&site=qq";

	static String taobaoLoginUrl = "https://login.taobao.com/member/login.jhtml";

	// 未登录微博，则使用qq登录
	public static void loginByQQWeibo(WebDriver chrome) throws InterruptedException {
			log.info("QQ登录weibo");
			chrome.get(weiboConnQQUrl);
			Thread.sleep(5000);
			
			try {
				chrome.switchTo().frame("ptlogin_iframe");
				chrome.findElement(By.xpath("//*[@id=\"img_out_781417636\"]")).click();
			} catch (Exception e) {
				Thread.sleep(5000);
				log.info("qq无法自动登录");
			}
			
			log.info("weibo登录taobao");
			Thread.sleep(2000);
			chrome.get(taobaoLoginUrl);
			WebElement btn = chrome.findElement(By.id("J_Quick2Static"));
			if (btn != null && btn.isDisplayed()) {
				btn.click();
			}
			chrome.findElement(By.xpath("//*[@id=\"J_OtherLogin\"]//a[1]")).click();
			Thread.sleep(2000);
			WebElement weiboPicBtn = chrome.findElement(By.xpath("//*[@class=\"logged_info\"]//a"));
			weiboPicBtn.click();

	}

	//TODO
	public static void loginWeibo(WebDriver chrome) throws InterruptedException {
		log.info("直接登录weibo");
		chrome.get("");
		Thread.sleep(5000);
	}
	
	
	public static void main(String[] args) {
		try {
			WebDriver chrome = BrowserFactory.getChrome();
			loginByQQWeibo(chrome);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
