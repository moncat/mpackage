package com.co.example.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.co.example.constant.SessionConstant;
import com.co.example.entity.admin.aide.AdminSession;

/**
 * 配合 baseController使用的请求路径拦截器
 * @author zyl
 *
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg) throws Exception {
		HttpSession session = request.getSession();
		AdminSession adminSession = (AdminSession) session.getAttribute(SessionConstant.SESSION_ADMIN);
		if(null == adminSession){
			response.sendRedirect("/login");
			return false;
		}
		
		return true;
	}
}