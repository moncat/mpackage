package com.co.example.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	
	public static final int DEFAULT_COOKIE_MAX_DAYS = 30;

	/**
	 * 设置cookie
	 * @param response
	 * @param name  cookie名字
	 * @param value cookie值
	 * @param maxAge cookie生命周期  以秒为单位
	 */
	public static void addCookie(HttpServletResponse response, String name,String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (maxAge > 0) {
			cookie.setMaxAge(maxAge);
		}
		response.addCookie(cookie);
	}
	
	/**
	 * 设置cookie
	 * @param response
	 * @param name  cookie名字
	 * @param value cookie值
	 */
	public static void addCookieDefaultAge(HttpServletResponse response, String name,String value) {
		addCookie(response, name, value, 24 * 3600 * DEFAULT_COOKIE_MAX_DAYS);
	}
	
	/**
	 * 删除cookie
	 * @param response
	 * @param name  cookie名字
	 */
	public static void removeCookie(HttpServletResponse response, String name) {
		Cookie cookie = new Cookie(name, null);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
	
	/**
	 * 根据名字获取cookie
	 * @param request
	 * @param name cookie名字
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		return (Cookie) getCookieMap(request).get(name);
	}
	
	/**
	 * 根据名字获取cookie值
	 * @param request
	 * @param name cookie名字
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String name) {
		String cookieValue = null;
		Cookie cookie = (Cookie) getCookieMap(request).get(name);
		if(cookie == null){
			cookieValue = null;
		}else{
			cookieValue = cookie.getValue();
		}
		return cookieValue;
	}
	
	/**
	 * 将cookie封装到Map里面
	 * @param request
	 * @return
	 */
	private static Map<String, Cookie> getCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
}
