package com.co.example.controller;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.co.example.entity.user.TUserLogin;
import com.co.example.service.user.TUserLoginService;

@Controller
public class WebController {

	@Resource
	TUserLoginService tUserLoginService;
	
    @RequestMapping("/")
    Map<String, Object> home() {
    	 Map<String,Object> map = new HashMap<String,Object>();
         map.put("code","start");
         map.put("codeMsg", "success");
         map.put("user", "zzz");
		return map;
    }
    

    @RequestMapping(value = "/db2", method =  { RequestMethod.GET,RequestMethod.POST })
    String db2(Model model) {
    	TUserLogin tUserLogin = tUserLoginService.queryById(7);
    	model.addAttribute("one", tUserLogin);
    	return "user/show";
    }

    
    
}
