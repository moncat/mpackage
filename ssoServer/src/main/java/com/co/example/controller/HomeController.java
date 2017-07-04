package com.co.example.controller;

import java.security.Principal;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {

	//首页
    @RequestMapping("/")
    public String home(Locale locale, Model model, Principal puser){
		if (puser != null){
			model.addAttribute("username", puser.getName());
		}
        return "home";
    }

    //拒绝授权
    @RequestMapping(value="/deny")
    public String handleDeny(){
        return "deny";
    }

}
