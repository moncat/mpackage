package com.co.example.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.co.example.constant.SessionConstant;
import com.co.example.entity.admin.aide.AdminSession;
import com.co.example.entity.admin.aide.TAdminVo;
import com.co.example.service.admin.TAdminService;
import com.co.example.utils.ClientUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 登录成功后
 * @author zyl
 *
 */
@Slf4j
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Autowired
	TAdminService  tAdminService;
		
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		HttpSession session = request.getSession();
		Object attribute = session.getAttribute(SessionConstant.SESSION_ADMIN);
		AdminSession oldAdminSession = null;
		if(attribute == null ){
			oldAdminSession = new AdminSession();
		}else{
			oldAdminSession = (AdminSession) attribute;
		}
		log.info("***oldAdminSession***"+oldAdminSession);
		TAdminVo userDetails = (TAdminVo) authentication.getPrincipal();
		// 清空验证码
		session.removeAttribute(SessionConstant.SESSION_IDENTITY_CODE);
		//管理员登录
		log.info("******登录成功，执行session处理");
		tAdminService.updateLogin(oldAdminSession,userDetails,ClientUtil.getIp(request));
		session.setAttribute(SessionConstant.SESSION_ADMIN, oldAdminSession);		
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
