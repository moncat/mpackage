package com.co.example.controller;

import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {


	@RequestMapping("/")
	public String home(Locale locale, Model model, Principal puser) {
		if (puser != null) {
			model.addAttribute("username", puser.getName());
		}
		return "home";
	}

	@RequestMapping(value = "/deny")
	public String handleDeny() {
		return "deny";
	}


}
