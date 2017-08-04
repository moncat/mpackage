package com.co.example.utils;

import javax.servlet.http.HttpServletRequest;

public class PathUtil {
	
	/**
	 * 获取根Path
	 * @param request
	 * @return
	 */
	public static String getBasePath(HttpServletRequest request){
		return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	}
}
