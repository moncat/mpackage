package com.co.example.base.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.co.example.base.controller.BaseController;

import lombok.extern.slf4j.Slf4j;

/**
 * 配合 baseController使用的请求路径拦截器
 * @author zyl
 *
 */
@Slf4j
public class BaseInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg) throws Exception {
		if (arg instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) arg;
			RequestMapping annotation = hm.getBeanType().getAnnotation(RequestMapping.class);
			if (annotation != null) {
				String value = annotation.value()[0];
				log.debug("BaseInterceptor",value);
				Object bean = hm.getBean();
				if (bean instanceof BaseController) {
					BaseController<?> bci = ((BaseController<?>) bean);
					bci.setReturnPath(value);
				}
			}
		}
		return true;
	}
}