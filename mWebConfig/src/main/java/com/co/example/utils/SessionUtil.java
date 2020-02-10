package com.co.example.utils;

import javax.servlet.http.HttpSession;

import com.co.example.constant.SessionConstant;
import com.co.example.entity.admin.TAdmin;
import com.co.example.entity.admin.aide.AdminSession;

public class SessionUtil {

	//获得管理员session
	public static AdminSession getAdminSession(HttpSession session){
		AdminSession adminSession = (AdminSession) session.getAttribute(SessionConstant.SESSION_ADMIN);
		return adminSession;
	}
	//获得管理员
	public static TAdmin getAdmin(HttpSession session){
		AdminSession adminSession = getAdminSession(session);
		TAdmin admin = adminSession.getAdmin();
		admin.setPassword(null);
		return admin;
	}
	
	//获得管理员Id
	public static Long getAdminId(HttpSession session){
		TAdmin tAdmin = getAdmin(session);
		Long adminId = tAdmin.getId();
		return adminId;
	}
	
	//获得是否登录
	public static boolean  getIsLogin(HttpSession session){
		AdminSession adminSession = getAdminSession(session);
		boolean isLogin = adminSession.isLogin();
		return isLogin;
	}
	
	//获得版本
	public static String getPageVersion(HttpSession session){
		TAdmin tAdmin = getAdmin(session);
		String pageVersion = tAdmin.getPageVersion();
		if(pageVersion == null || pageVersion.equals("1")){
			return "";
		}else{
			return "V"+pageVersion;
		}
	}
	
	//将管理员保存到session
	public static void setAdmin(TAdmin admin,HttpSession session){
		AdminSession adminSession = getAdminSession(session);
		adminSession.setAdmin(admin);
		session.setAttribute(SessionConstant.SESSION_ADMIN, adminSession);
	}
	
}
