package com.co.example.common.utils;

import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class htmlUtil {
    
	static WebClient webClient=new WebClient(BrowserVersion.BEST_SUPPORTED);
	
	static {
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(true);
		//js运行错误时，是否抛出异常
		webClient.getOptions().setThrowExceptionOnScriptError(false); 
		//设置 Ajax控制器，同步执行ajax代码
//		webClient.setAjaxController(new NicelyResynchronizingAjaxController());  
	}
	
	public static String getPage(String url) throws Exception {
		HtmlPage page = webClient.getPage(url);
		HtmlTextInput elementById1 = (HtmlTextInput)page.getElementById("s1-0[0]-0[0]-account");
		HtmlPasswordInput elementById2 = (HtmlPasswordInput)page.getElementById("s1-0[0]-0[0]-password");
		elementById1.setValueAttribute("888");
		elementById2.setValueAttribute("*");
		List<Object> newLinks = page.getByXPath ("//a[@class='btn btn-primary btn-block btn-lg']");
//		 List<Object> newLinks = page.getByXPath ("//a[@href='/download']");
		HtmlAnchor  aBtn = (HtmlAnchor)newLinks.get(0);
//	    Page newPage = aBtn.click();
		
		Page page1 = page.executeJavaScript("$('.btn.btn-primary.btn-block.btn-lg').eq(0).click()").getNewPage();
		System.out.println("***1***"+page1.getWebResponse().getContentAsString());
//	    boolean flg = newPage.isHtmlPage();
//	    if(flg){
//	    	URL url2 = newPage.getUrl();
//	    	HtmlPage page2 = (HtmlPage) newPage;
//	    	String asText2 = page2.asText();
//	    	System.out.println("***"+url2);
//	    	System.out.println("***"+asText2);
//	    	Thread.sleep(5000);
//	    	HtmlPage page2 = webClient.getPage("https://www.qixin.com/company/1b9df7af-e7b3-4d45-93ce-8acf02534adb");
//	    	System.out.println("***2***"+page2.asText());
		
//	    }else{
//	    	System.out.println("*************************");
//	    }
		
		webClient.close();
		return "";
	}
	
	public static String getPage2(String url) throws Exception {
		HtmlPage page = webClient.getPage(url);
		List<Object> xp1 = page.getByXPath("//input[@class='_input input_nor contactphone']");
		HtmlTextInput e1 = (HtmlTextInput)xp1.get(1);
		List<Object> xp2 = page.getByXPath("//input[@class='_input input_nor contactword']");
		HtmlPasswordInput e2 = (HtmlPasswordInput)xp2.get(1);
		e1.setValueAttribute("888");
		e2.setValueAttribute("***");
		List<Object> xp3 = page.getByXPath("//div[@class='c-white b-c9 pt8 f18 text-center login_btn']");
		HtmlDivision e3 = (HtmlDivision)xp3.get(1);
	    Page newPage = e3.click();
	    HtmlPage newHtmlPage = (HtmlPage) newPage;
//	    System.out.println("***2***"+newHtmlPage.asText());
	    HtmlPage page3 = webClient.getPage(url);
	    System.out.println("***3***"+page3.asText());
		webClient.close();
		return "";
	}
	
	public static void main(String[] args) throws Exception {
//		getPage("https://www.qixin.com/auth/login?return_url=%2F");
		getPage2("https://www.tianyancha.com/login?from=https%3A%2F%2Fwww.tianyancha.com%2Fsearch%3Fkey%3Dofo%26checkFrom%3DsearchBox");
	}

}





