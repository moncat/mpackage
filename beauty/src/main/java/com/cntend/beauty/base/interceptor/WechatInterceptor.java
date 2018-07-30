package com.cntend.beauty.base.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.co.example.common.utils.HttpClientUtils;
import com.co.example.constant.WechatConstant;
import com.co.example.service.user.TUserService;

import lombok.extern.slf4j.Slf4j;


//wechat 拦截器 用来获取openid
//@Slf4j
//public class WechatInterceptor  extends HandlerInterceptorAdapter {
//
//	@Resource
//	TUserService tUserService;
//	
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object arg) throws Exception {
//		HttpSession session = request.getSession();
//		//log.info("wechat拦截器运行");
//		String openId = (String)session.getAttribute(WechatConstant.OPEN_ID);
//		//如果session 中没有 openId，去获取
//		if(StringUtils.isBlank(openId)){
//			log.info("wechat的openId为空");
//			String code_url = WechatConstant.CODE_URL.replace("APPID", WechatConstant.APPID)
//					.replace("REDIRECT_URI", WechatConstant.REDIRECT_URI).replace("SCOPE", WechatConstant.SCOPE);
//			int r = HttpClientUtils.doPost(code_url);
//			if(r!=200){
//				session.setAttribute(WechatConstant.OPEN_ID, "0");
//			}
////			response.sendRedirect(code_url);
//			//不是微信内置浏览器，防止不停拦截
//		}
//		return true;
//	}
//	
//}
