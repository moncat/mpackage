package com.co.example;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Test {

	public static void main(String[] args) throws InterruptedException {

		/*
		 * 不同浏览器的设置不同，推荐使用火狐浏览器。不推荐使用IE浏览器。
		 * 此处我用的火狐版本为40.4，selenium版本为2.53.1
		 */
		System.setProperty("webdriver.firefox.bin", "C:/Program Files (x86)/Mozilla Firefox/firefox.exe");

		System.out.println("start");

		WebDriver driver = new FirefoxDriver();
		
		/*
		 * 由于要设置cookie，所以在设置之前必须就建立连接。
		 * 否则会报错。Exception in thread "main" org.openqa.selenium.InvalidCookieDomainException: You may only set cookies for the current domain
		 * 故第一句get是不能少的。
		 */
		driver.get("https://www.tianyancha.com/");
		
		for(Cookie cookie:Test.click("182", "*")){
			driver.manage().addCookie(cookie);
			
		}
		driver.manage().window().maximize();
		
		driver.get("https://www.tianyancha.com/");
		
		String pageSource = driver.getPageSource();
		System.out.println(pageSource);
		
		/*
		 * 此处我使用set来去重，当然不是什么好办法。只是这个版本只是最初版
		 * 之后会再优化
		 */
		Set<String> result = new HashSet<String>();
		
		int i = 0;
		
		int num = 4600;
		
		
		
//		while(true){
//			System.out.println(i++);
//			Thread.sleep(1000*20);  //拖动滚动条之后，休眠，便于网站加载数据
//			
//			
//			List<WebElement> list = driver.findElements(By.cssSelector("img.pinImg.fullBleed.loaded.fade"));
//			for(WebElement webElement:list){
//				String url = webElement.getAttribute("src");
//				System.out.println("url:"+url);
//				result.add(url);
//			}
//			System.out.println("result.size = "+result.size());
//			if(list.size() == 0){
//				break;
//			}
//			
//			int roll = num*i;
//			System.out.println("roll:"+roll);
//			
//			/*
//			 * 这个方法模拟向下拖动  垂直滚动条
//			 */
//			String setscroll = "document.documentElement.scrollTop=" + "" + roll; 
//			JavascriptExecutor jse=(JavascriptExecutor) driver;
//			jse.executeScript(setscroll); 
//		}

		driver.close();

		System.out.println("end");

	}

	
	/**
	 * 获取cookie
	 * @param username
	 * @param password
	 * @return
	 * @throws InterruptedException
	 */
	public static Set<Cookie> click(String username,String password) throws InterruptedException{
		
		System.setProperty("webdriver.firefox.bin", "C:/Program Files (x86)/Mozilla Firefox/firefox.exe");
		WebDriver driver = new FirefoxDriver();
		driver.get("https://www.tianyancha.com/");
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//Thread.sleep(1000*8);
		driver.findElement(By.xpath("//div[@class='modulein1']//input[@type='text']")).clear();
		driver.findElement(By.xpath("//div[@class='modulein1']//input[@type='text']")).sendKeys(username);
		driver.findElement(By.xpath("//div[@class='modulein1']//input[@type='password']")).clear();
		driver.findElement(By.xpath("//div[@class='modulein1']//input[@type='password']")).sendKeys(password);
		driver.findElement(By.xpath("//div[@class='modulein1']//div[@class='login_btn']")).click();
		
		Thread.sleep(1000*10);

		Set<Cookie> cookies = driver.manage().getCookies();
		
		System.out.println("Cookie.size = " + cookies.size());
		
		driver.close();

		return cookies;
	}

}


