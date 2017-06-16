package com.co.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    
    @RequestMapping("/show")
    public String show() {
        return "user/show";

    }
    
    
    

}