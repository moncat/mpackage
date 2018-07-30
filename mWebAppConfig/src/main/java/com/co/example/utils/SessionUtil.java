package com.co.example.utils;

import javax.servlet.http.HttpSession;

import com.co.example.constant.SessionConstant;
import com.co.example.entity.user.TUser;
import com.co.example.entity.user.aide.UserSession;


public class SessionUtil {

	//获得管理员session
	public static UserSession getUserSession(HttpSession session){
		UserSession userSession = (UserSession) session.getAttribute(SessionConstant.SESSION_USER);
		return userSession;
	}
	//获得管理员
	public static TUser getUser(HttpSession session){
		UserSession userSession = getUserSession(session);
		TUser user = userSession.getUser();
		if(user !=null){
			user.setPassword(null);
		}
		return user;
	}
	//获得管理员Id
	public static Long getUserId(HttpSession session){
		TUser user = getUser(session);
		if(user !=null){
			Long userId = user.getId();
			return userId;
		}
		return null;
	}
	//获得管理员Id
	public static boolean  getIsLogin(HttpSession session){
		UserSession userSession = getUserSession(session);
		boolean isLogin = userSession.isLogin();
		return isLogin;
	}
	
	//将管理员保存到session
	public static void setUser(TUser user,HttpSession session){
		UserSession userSession = getUserSession(session);
		userSession.setUser(user);
		session.setAttribute(SessionConstant.SESSION_USER, userSession);
	}
	
}
