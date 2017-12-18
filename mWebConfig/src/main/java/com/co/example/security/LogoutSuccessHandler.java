package com.co.example.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.co.example.constant.CookieConstant;
import com.co.example.utils.CookieUtil;

public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		CookieUtil.removeCookie(response, CookieConstant.COOKIE_ADMIN_KEY);	
		CookieUtil.removeCookie(response, CookieConstant.COOKIE_REMEMBER_ME);
		super.onLogoutSuccess(request, response, authentication);
	}
	
}
