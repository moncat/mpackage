package com.co.example.base.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.co.example.base.constant.SessionConstant;
import com.co.example.entity.admin.aide.AdminSession;
import com.co.example.service.admin.TAdminService;



public class SessionInterceptor  extends HandlerInterceptorAdapter {

	@Resource
	TAdminService tAdminService;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object arg) throws Exception {
		AdminSession adminSession = (AdminSession)request.getSession().getAttribute(SessionConstant.SESSION_ADMIN);
		if(adminSession==null){
			adminSession = new AdminSession();
			request.getSession().setAttribute(SessionConstant.SESSION_ADMIN, adminSession);
		}

		return true;
	}
	
}
