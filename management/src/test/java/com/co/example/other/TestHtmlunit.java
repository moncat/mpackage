package com.co.example.other;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.ManagerApplication;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class TestHtmlunit {
	
//	@Test
	public void test1(){
		log.info("");
	}
	
	
	@Test
	public  void submittingForm() throws Exception {  
        final WebClient webClient = new WebClient();  
        //1.获取某个待测页面  
        final HtmlPage page1 = webClient.getPage("http://125.35.6.80:8181/ftban/fw.jsp");  
        //3.获取页面上的各个元素  
        
        DomElement txt = page1.getElementById("searchtext");
        DomElement btn = page1.getElementById("searchInfo");
        txt.setTextContent("唯恩诗");
        Page page2 = btn.click();
 
        
//        final HtmlSubmitInput button = form.getInputByName("testlogin");  
//        final HtmlTextInput textField = form.getInputByName("username");  
//        final HtmlPasswordInput pass= form.getInputByName("pass");  
//        //4.给元素赋值  
//        textField.setValueAttribute("18254133367");  
//        pass.setValueAttribute("routon");  
//        //5.提交  
//        button.click();  
        System.out.println(page2.toString());
        webClient.close();
    }  
    public static void main(String[] args) {  
        try {  
//            submittingForm();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}