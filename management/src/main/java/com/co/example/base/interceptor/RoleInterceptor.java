package com.co.example.base.interceptor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.co.example.base.constant.SessionConstant;
import com.co.example.constant.RoleConstant;
import com.co.example.entity.admin.aide.AdminSession;
import com.co.example.service.admin.TAdminService;
import com.co.example.utils.PathUtil;



public class RoleInterceptor  extends HandlerInterceptorAdapter {

	@Resource
	TAdminService tAdminService;
	//必须是管理员才有的权限
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object arg) throws Exception {
		AdminSession adminSession = (AdminSession)request.getSession().getAttribute(SessionConstant.SESSION_ADMIN);
		List<Long> roles = adminSession.getRoles();
		Boolean flg = false;
		for (Long role : roles) {
			if(RoleConstant.SUPER_ADMIN == role){
				flg = true;
			}
		}
		if(flg == false){
			String basePath = PathUtil.getBasePath(request);
			response.sendRedirect(basePath+"/error"); 
			return false;
		}
		return true;
	}
	
}
