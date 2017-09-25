package com.co.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

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
public class DataTest8 {
	
	@Test
	public void test1(){
		log.info("");
	}
	
	public static void submittingForm() throws Exception {  
        final WebClient webClient = new WebClient();  
        //1.获取某个待测页面  
        final HtmlPage page1 = webClient.getPage("https://www.qixin.com/auth/login?return_url=%2F");  
        //3.获取页面上的各个元素  
        
        DomElement elementById = page1.getElementById("s1-0[0]-0[0]-account");
        
//        DomElement elementById2 = page1.getElementById("s1-0[0]-0[0]-password");
        
        System.out.println(elementById);
//        final HtmlSubmitInput button = form.getInputByName("testlogin");  
//        final HtmlTextInput textField = form.getInputByName("username");  
//        final HtmlPasswordInput pass= form.getInputByName("pass");  
//        //4.给元素赋值  
//        textField.setValueAttribute("18254133367");  
//        pass.setValueAttribute("routon");  
//        //5.提交  
//        button.click();  
        webClient.close();
    }  
    public static void main(String[] args) {  
        try {  
            submittingForm();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}