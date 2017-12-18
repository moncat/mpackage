package com.co.example.common.utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class PhantomjsUtil {
	public static WebDriver driver=null;
	public static DesiredCapabilities caps = new DesiredCapabilities();
	public static String phantomJs_dir = "D:/phantomjs-2.1.1-windows/bin/phantomjs.exe";
	public static void main(String[] args) throws InterruptedException {
		
		//设置必要参数
        DesiredCapabilities dcaps = new DesiredCapabilities();
        //ssl证书支持
        dcaps.setCapability("acceptSslCerts", true);
        //截屏支持
        dcaps.setCapability("takesScreenshot", true);
        //css搜索支持
        dcaps.setCapability("cssSelectorsEnabled", true);
        //js支持
        dcaps.setJavascriptEnabled(true);
        //驱动支持
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,phantomJs_dir);
        //创建无界面浏览器对象
        PhantomJSDriver driver = new PhantomJSDriver(dcaps);
        
		String html="";
		String url="https://www.qixin.com/";
		driver.get(url);
//		html=driver.getPageSource();
//		System.out.println(html);
		
//		WebElement name = driver.findElement(By.xpath("//input[@class='input-flat-user']"));
//		name.clear();
//		name.sendKeys("182*");

//		WebElement pass = driver.findElement(By.xpath("//input[@class='input-flat-lock']"));
//		pass.clear();
//		pass.sendKeys("**");
		WebElement btn = driver.findElement(By.xpath("//a[@href='/auth/login?return_url=%2F']"));
		btn.click();
		String loginUrl =driver.getCurrentUrl();
		System.out.println(loginUrl);
		driver.get(loginUrl);
		String newhtml=driver.getPageSource();
		System.out.println(newhtml);
		driver.quit();
	}
}










