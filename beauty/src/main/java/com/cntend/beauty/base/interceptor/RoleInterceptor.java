package com.cntend.beauty.base.interceptor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.co.example.constant.SessionConstant;
import com.co.example.entity.user.aide.UserSession;
import com.co.example.service.user.TUserService;



public class RoleInterceptor  extends HandlerInterceptorAdapter {

	@Resource
	TUserService tUserService;
	//必须是管理员才有的权限
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object arg) throws Exception {
//		UserSession userSession = (UserSession)request.getSession().getAttribute(SessionConstant.SESSION_USER);
//		List<Long> roles = userSession.getRoles();
		return true;
	}
	
}
