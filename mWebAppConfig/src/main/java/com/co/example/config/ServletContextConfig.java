package com.co.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.co.example.interceptor.BaseInterceptor;
/**
 * 拦截器配置
 * @author zyl
 *
 */
//@Configuration 在子类中添加
public class ServletContextConfig extends WebMvcConfigurationSupport {

	/**
     * 发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。
     * 需要重新指定静态资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        addMoreResourceHandlers(registry);
        super.addResourceHandlers(registry);
    }
    //用于扩展
    protected void addMoreResourceHandlers(ResourceHandlerRegistry registry) {
	}


    /**
     * 配置servlet处理
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    	configurer.enable();
    }
    
    @Bean
    public BaseInterceptor baseInterceptor(){
    	return new BaseInterceptor();
    }
    

    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(baseInterceptor()).addPathPatterns("/**");
    	addMoreInterceptors(registry);
        super.addInterceptors(registry);
    }
    //用于扩展
    protected void addMoreInterceptors(InterceptorRegistry registry) {
    }
}