package com.co.example.simulateLogin;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class weiboLoginUtil {
	public static WebDriver login(String ipPort) throws InterruptedException {
		WebDriver chrome = BrowserFactory.getChrome(ipPort);
		
		chrome.get("https://www.weibo.com");
		Thread.sleep(10000);
		String tbuserNmae="moncatcn@sina.com";
		String tbpassWord="???";
		
		chrome.findElement(By.id("loginname")).clear();
		chrome.findElement(By.id("loginname")).sendKeys(tbuserNmae);
		chrome.findElement(By.name("password")).clear();
		chrome.findElement(By.name("password")).sendKeys(tbpassWord);
		Thread.sleep(3000);
		chrome.findElement(By.xpath("//*[@id=\"pl_login_form\"]/div/div[3]/div[6]/a")).click();
		log.info("登录成功");
		Thread.sleep(5000);
		log.info("获得原cookies");
		Set<Cookie> cookies = chrome.manage().getCookies();
		log.info("关闭浏览器");
		chrome.close();
		String ipPortNew = ProxyUtil.getInfo();
		chrome = BrowserFactory.getChrome(ipPortNew);
		log.info("新浏览器添加cookies");
		for (Cookie cookie : cookies) {
			chrome.manage().addCookie(cookie);
		}
		return chrome;
		
	}
	
	public static void main(String[] args) {
		try {
//			String ipPort = ProxyUtil.getInfo();
			login(null);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
