package com.co.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

/**
 * 暂时无法同时模拟登陆以及页面点击  TODO
 * @author zyl
 *
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest5GetMorePunish {
	
	
	public static WebDriver driver=null;
	public static DesiredCapabilities caps = new DesiredCapabilities();
	public static String phantomJs_dir = "D:/phantomjs-2.1.1-windows/bin/phantomjs.exe";
	static{
		caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomJs_dir);
	}
	
	
	@Test
	public void  test() throws InterruptedException {
		log.info("1111111");
		if(null==driver){
			driver = new PhantomJSDriver(caps);
		}
		Options manage = driver.manage();
		manage.deleteAllCookies();
		
		try {
			Cookie cookie = new Cookie("Hm_lpvt_e92c8d65d92d534b0fc290df538b4758", "1506751485");
			manage.addCookie(cookie);
			manage.addCookie(new Cookie("Hm_lvt_e92c8d65d92d534b0fc290df538b4758", "1506393380,1506506743,1506674704,1506738504"));
			manage.addCookie(new Cookie("OA", "VxQaJA9v3KJPRRjJE47Mc7frXsvHH8g2VGs7ZzHltKjxHyzQ9CrsNBv+fXYNcfZr"));
			manage.addCookie(new Cookie("RTYCID", "f4b9d56478a84d39a5b703ce3e1d0d69"));
			manage.addCookie(new Cookie("TYCID", "97a93400a26311e7aec7f9f91b57c269"));
			manage.addCookie(new Cookie("_csrf", "bqGIXXK/6L4oawQE7H9eng=="));
			manage.addCookie(new Cookie("_csrf_bk", "824b4216325edb756099d86c561d04f2"));
			manage.addCookie(new Cookie("aliyungf_tc", "AQAAAGBCTEJpPwAAon3QPAGOjrNUNT2L"));
			manage.addCookie(new Cookie("auth_token",
					"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODI1NDEzMzM2NyIsImlhdCI6MTUwNjczODU4OSwiZXhwIjoxNTIyMjkwNTg5fQ.ektX4B6bN8rMVkFfxnFW9bp7PXVJgc60Yr19bLBBgAJrCAGTTqZSuz-x54gJ0Tr8oUjoPQ2nVRXTW3zRfhFFIg"));
			manage.addCookie(new Cookie("bannerFlag", "true"));
			manage.addCookie(new Cookie("csrfToken", "7K_eev7pLkaxp3BHdixeBp7q"));
			manage.addCookie(new Cookie("ssuid", "2882060535"));
			manage.addCookie(new Cookie("tyc-user-info",
					"%257B%2522token%2522%253A%2522eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODI1NDEzMzM2NyIsImlhdCI6MTUwNjczODU4OSwiZXhwIjoxNTIyMjkwNTg5fQ.ektX4B6bN8rMVkFfxnFW9bp7PXVJgc60Yr19bLBBgAJrCAGTTqZSuz-x54gJ0Tr8oUjoPQ2nVRXTW3zRfhFFIg%2522%252C%2522integrity%2522%253A%25220%2525%2522%252C%2522state%2522%253A%25220%2522%252C%2522vnum%2522%253A%25220%2522%252C%2522onum%2522%253A%25220%2522%252C%2522mobile%2522%253A%252218254133367%2522%257D"));
			manage.addCookie(new Cookie("uccid", "4697cd5309e0efff9ef11629e94649b4"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String url2="https://www.tianyancha.com/company/80223669";
		driver.get(url2);
		String html = driver.getPageSource();
		System.out.println(html);
//		driver.findElement(By.xpath("//a[text()='下一页']")).click();
//		html=driver.getPageSource();
		driver.quit();
		}
	 
}









