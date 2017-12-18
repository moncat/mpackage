package com.cntend.beauty.base.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.co.example.constant.SessionConstant;
import com.co.example.entity.user.aide.UserSession;
import com.co.example.service.user.TUserService;


public class SAInterceptor  extends HandlerInterceptorAdapter {

	@Resource
	TUserService tUserService;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object arg) throws Exception {
		UserSession userSession = (UserSession)request.getSession().getAttribute(SessionConstant.SESSION_USER);
		if(userSession==null){
			userSession = new UserSession();
			request.getSession().setAttribute(SessionConstant.SESSION_USER, userSession);
		}

		return true;
	}
	
}
