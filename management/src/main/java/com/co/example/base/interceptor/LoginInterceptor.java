package com.co.example.base.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.co.example.base.constant.HttpStatusCode;
import com.co.example.base.constant.SessionConstant;
import com.co.example.entity.admin.aide.AdminSession;
import com.co.example.service.admin.TAdminService;
import com.co.example.utils.PathUtil;



public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Resource
	TAdminService tAdminService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object arg) throws Exception {
		String basePath = PathUtil.getBasePath(request);
		String requestPath = request.getServletPath();
		
		AdminSession adminSession = (AdminSession)request.getSession().getAttribute(SessionConstant.SESSION_ADMIN);
		String requestType = request.getHeader("X-Requested-With");
		
		if(!adminSession.isLogin()){
			String parm = request.getQueryString();
			if(StringUtils.isBlank(parm)){
				request.getSession().setAttribute(SessionConstant.SESSION_BACK_URL, basePath+requestPath);
			}else{
				request.getSession().setAttribute(SessionConstant.SESSION_BACK_URL, basePath+requestPath+"?"+parm);
			}
			request.getSession().setAttribute(SessionConstant.SESSION_BACK_URL, basePath);
			if(requestType != null && requestType.equals("XMLHttpRequest")){
			//如果是ajax请求
				response.getWriter().print(HttpStatusCode.CODE_ERROR);
			}else{
				//普通请求，重定向页面
				response.sendRedirect(basePath+"/loginInit"); 
			}
			return false;
		}
		return true;
	}
}
