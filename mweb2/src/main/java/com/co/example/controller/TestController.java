package com.co.example.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.entity.user.TUsers;

@Controller
public class TestController {

    @RequestMapping("/")
    public String getUser(ModelMap model, Principal user){
        model.addAttribute("user",user);
        return "user/index";
    }

    @RequestMapping("/show")
    public String show(ModelMap model,TUsers user) throws Exception{
        model.addAttribute("user",user);
        return "user/show";
    }

}
