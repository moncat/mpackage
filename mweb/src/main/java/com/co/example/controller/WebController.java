package com.co.example.controller;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.example.common.utils.PageReq;
import com.co.example.entity.user.TUserLogin;
import com.co.example.entity.user.TUsers;
import com.co.example.entity.user.aide.TUsersQuery;
import com.co.example.service.user.TUserLoginService;
import com.co.example.service.user.TUsersService;

@RestController
@PropertySource(value = { "classpath:application-data.properties"})
public class WebController {

	@Value("${obj.name}")
	String name;
	
	@Resource
	TUsersService tUsersService;
	
	@Resource
	TUserLoginService tUserLoginService;
	
    @RequestMapping("/")
    Map<String, Object> home() {
    	 Map<String,Object> map = new HashMap<String,Object>();
         map.put("code","start");
         map.put("codeMsg", "success");
         map.put("user", name);
		return map;
    }
    
   
    @RequestMapping("/db")
    String db() {
    	
    	PageReq pageReq = new PageReq();
    	pageReq.setPageSize(2);;
    	Page<TUsers> page = tUsersService.queryPageList(new TUsersQuery(), pageReq);
    	return "debug";
    }
    @RequestMapping("/db2")
    String db2() {
    	TUserLogin tUserLogin = tUserLoginService.queryById(7);
    	return tUserLogin.toString();
    }
    @RequestMapping("/db3")
    String db3() {
    	tUsersService.addUser();
    	return "yes";
    }
    
    void restClient(){
     	Client client = ClientBuilder.newClient();  
    }
    
    
    
}
