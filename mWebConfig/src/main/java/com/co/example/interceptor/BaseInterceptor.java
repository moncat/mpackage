package com.co.example.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.co.example.controller.BaseController;
import com.co.example.utils.SessionUtil;

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

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if(modelAndView !=null){
			String viewName = modelAndView.getViewName();
			if(viewName !=null){
				if(!viewName.contains("login") && !viewName.contains("error") && !viewName.contains("logout")){
					String pageVersion = SessionUtil.getPageVersion(request.getSession());
					if(viewName.contains("redirect")){
						modelAndView.setViewName("redirect:/"+pageVersion+viewName.substring(viewName.indexOf("/")));
					}else{
						modelAndView.setViewName("/"+pageVersion+"/"+viewName);
					}
				}
			}
		}
		super.postHandle(request, response, handler, modelAndView);
	}
	
	
	
	
}