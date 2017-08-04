package com.co.example.base.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.co.example.base.constant.CookieConstant;
import com.co.example.base.constant.SessionConstant;
import com.co.example.common.constant.Constant;
import com.co.example.entity.admin.TAdmin;
import com.co.example.entity.admin.TAdminLogin;
import com.co.example.entity.admin.aide.AdminSession;
import com.co.example.service.admin.TAdminLoginService;
import com.co.example.service.admin.TAdminService;
import com.co.example.utils.ClientUtil;
import com.co.example.utils.CookieUtil;


public class RemembermeInterceptor extends HandlerInterceptorAdapter{
	
	@Resource
	private TAdminService tAdminService;
	@Resource
	private TAdminLoginService tAdminLoginService;

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		
		AdminSession adminSession = (AdminSession)request.getSession().getAttribute(SessionConstant.SESSION_ADMIN);

		if(!adminSession.isLogin() && adminSession.isRemembermeCheck()){
			//自动登录
			String adminKey = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_ADMIN_KEY);
			if(StringUtils.isNotBlank(adminKey)){
				//给用户自动登录
				TAdminLogin adminLogin = tAdminLoginService.queryByRememberKey(adminKey);
				if(adminLogin != null){
					TAdmin admin = tAdminService.queryVoById(adminLogin.getAdminId());
					//用户存在且不为锁定状态
					if(admin != null && admin.getDelFlg() != Constant.YES){
						tAdminService.updateLogin(adminSession, admin, ClientUtil.getIp(request));
					}
				}else{
					adminSession.setRemembermeCheck(false);
				}
			}else{
				adminSession.setRemembermeCheck(false);
			}
		}
		
		request.getSession().setAttribute(SessionConstant.SESSION_ADMIN, adminSession);
		return true;
	}

}
