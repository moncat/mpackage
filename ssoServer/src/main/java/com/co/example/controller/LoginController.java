package com.co.example.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
    @RequestMapping("/login")
    public String login(){
        return "loginpage";
    }


    
    @RequestMapping("/logout2")
    public String logout2(HttpServletRequest request) throws Exception{
        request.logout();
        return "redirect:/";
    }
    
    /*  
		logout的mapping默认是post的，get的没有，然而某些时候莫名其妙会跳到get的/logout。
    	不管logoutSuccessUrl是设置为/logout2还是/signout。
    */
    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String logout(HttpServletRequest request) throws Exception{
        request.logout();
        return "redirect:/";
    }

    
    /*
	注意如果在AuthorizationServerConfigurerAdapter的子类的
	configure(ClientDetailsServiceConfigurer clients)里面进行了autoApprove(true)，则这个页面不起作用。
     */
    @RequestMapping("/oauth/confirm_access")
    public String confirm_access(){
        return "confirm_access";
    }


}
