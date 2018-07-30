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
import com.co.example.constant.WechatConstant;
import com.co.example.entity.user.aide.TUserVo;
import com.co.example.entity.user.aide.UserSession;
import com.co.example.service.user.TUserService;
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
	TUserService  tUserService; 
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		HttpSession session = request.getSession();
		Object attribute = session.getAttribute(SessionConstant.SESSION_USER);
		UserSession oldUserSession = null;
		if(attribute == null ){
			oldUserSession = new UserSession();
		}else{
			oldUserSession = (UserSession) attribute;
		}
		log.info("***oldUserSession***"+oldUserSession);
		TUserVo userDetails = (TUserVo) authentication.getPrincipal();
		// 清空验证码
		session.removeAttribute(SessionConstant.SESSION_IDENTITY_CODE);
		//用户登录
		log.info("******登录成功，执行session处理");
		// 添加微信登录，获得到微信openId，从session中获取
		
		String openId = "0";
		try {
			openId = (String)session.getAttribute(WechatConstant.OPEN_ID);
		} catch (Exception e) {
			openId = "0";
			e.printStackTrace();
		}
		
		log.info("******登录成功，准备验证或保存该openId="+openId);
		tUserService.updateLogin(oldUserSession,userDetails,ClientUtil.getIp(request),openId);
		session.setAttribute(SessionConstant.SESSION_USER, oldUserSession);	
		
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
