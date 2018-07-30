package com.co.example.controller;

import org.springframework.security.access.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import com.alibaba.fastjson.JSONObject;
import com.github.moncat.common.generator.id.NextId;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
public class BaseControllerAdvice {
	
	//格式化所有date数据
	@InitBinder  
    protected void initBinder(WebDataBinder binder) {  
     //   binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));  
   
	} 
	
//	应用到所有@RequestMapping注解方法，在其执行之前把返回值放入Model 
//	@ModelAttribute  
//    public User newUser() {  
//        return new User();  
//    } 
	
	//处理公共exception
	@ExceptionHandler  
    public String exceptionProcess(Model model,HttpServletRequest request, HttpServletResponse response, RuntimeException ex) {  
		StackTraceElement[] stackTrace = ex.getStackTrace();
		String info = "" ;
		for (StackTraceElement element : stackTrace) {
			info += element.toString()+"<br/>";
		}
		JSONObject json  = new JSONObject();
		Long code = NextId.getId();
        json.put("code", code);  
        json.put("url", request.getRequestURL()); 
        json.put("msg", ex.getMessage()); 
        json.put("info", info); 
        String str = json.toString();      
        model.addAttribute("errInfo", str);              
        ex.printStackTrace(); 
        return "error";  
    } 
	
	
	
	
}
