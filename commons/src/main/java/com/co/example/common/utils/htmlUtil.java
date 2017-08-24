package com.co.example.common.utils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class htmlUtil {
    
	static WebClient webClient=new WebClient(BrowserVersion.BEST_SUPPORTED);
	
	static {
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(true);
		//js运行错误时，是否抛出异常
		webClient.getOptions().setThrowExceptionOnScriptError(true); 
		//设置 Ajax控制器，同步执行ajax代码
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());  
	}
	
	public static String getPage(String url) throws Exception {
		HtmlPage page = webClient.getPage(url);
		String asXml = page.asXml();
		log.debug("asXml"+asXml);
		webClient.close();
		return asXml;
	}
	
	public static void main(String[] args) throws Exception {
		getPage("http://125.35.6.80:8080/ftba/#");
	}
}
