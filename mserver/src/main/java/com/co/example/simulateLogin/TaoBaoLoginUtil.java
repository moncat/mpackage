/**
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.co.example.simulateLogin;

import org.jsoup.Jsoup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author zyl
 *
 */
public class TaoBaoLoginUtil {
	public static WebDriver login(String ipPort) throws InterruptedException {
		WebDriver chrome = BrowserFactory.getChrome(ipPort);
		chrome.get("https://login.taobao.com/member/login.jhtml");
		Thread.sleep(5000);
		String tbuserNmae="18254133367";
		String tbpassWord="13579zyl";
		
		WebElement btn = chrome.findElement(By.id("J_Quick2Static"));
		if(btn!=null && btn.isDisplayed()){
			btn.click();
		}
		
		chrome.findElement(By.id("TPL_username_1")).clear();
		chrome.findElement(By.id("TPL_username_1")).sendKeys(tbuserNmae);
		chrome.findElement(By.id("TPL_password_1")).clear();
		chrome.findElement(By.id("TPL_password_1")).sendKeys(tbpassWord);
		chrome.findElement(By.id("J_SubmitStatic")).click();
		
		Thread.sleep(30000L);
		String url = "https://www.tmall.com";
		chrome.get(url);
		String html = chrome.getPageSource();
		String username = Jsoup.parse(html).select(".j_Username").text();
		System.out.println(username);
		return chrome;
		
	}
}
