package com.co.example.security;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * 处理spring security csrf 拦截 多部件上传问题
 * @author zyl
 *
 */
public class CsrfSecurityRequestMatcher implements RequestMatcher {

	
	private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");

	@Override
	public boolean matches(HttpServletRequest request) {
		List<String> execludeUrls = new ArrayList<>();
		// 允许post请求的url路径
		execludeUrls.add("ueditor/add");
		if (execludeUrls != null && execludeUrls.size() > 0) {
			String servletPath = request.getServletPath();
			for (String url : execludeUrls) {
				if (servletPath.contains(url)) {
					return false;
				}
			}
		}
		return !allowedMethods.matcher(request.getMethod()).matches();

	}

}