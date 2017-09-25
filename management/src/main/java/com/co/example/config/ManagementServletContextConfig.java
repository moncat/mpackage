package com.co.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.co.example.base.interceptor.SessionInterceptor;

//import com.co.example.base.interceptor.SessionInterceptor;
/**
 * 拦截器配置
 * @author zyl
 *
 */
@Configuration
//@EnableWebMvc
public class ManagementServletContextConfig extends ServletContextConfig {

	@Value("${ueditor.savePath}")
	String savepath;
	
	
	@Value("${image.imageSavePath}")
	String imageSavePath;
	
    @Override
	protected void addMoreResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/**").addResourceLocations("classpath:/static/","file:"+savepath,"file:"+imageSavePath);    
	}

    @Bean
    public SessionInterceptor sessionInterceptor(){
    	return new SessionInterceptor();
    }
    
//    @Bean
//    public RemembermeInterceptor remembermeInterceptor(){
//    	return new RemembermeInterceptor();
//    }
    
//    @Bean
//    public LoginInterceptor loginInterceptor(){
//    	return new LoginInterceptor();
//    }
    
    
	@Override
	protected void addMoreInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(sessionInterceptor()).addPathPatterns("/**");
//		registry.addInterceptor(remembermeInterceptor()).addPathPatterns("/**");
	//	registry.addInterceptor(loginInterceptor()).addPathPatterns("/**").excludePathPatterns("/identifyCode","/login");
	}

//	  @Bean(name="messageSource") 
//	    public MessageSource reloadableResourceBundleMessageSource(){
//	    	ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//	    	messageSource.setBasename("classpath:messages/");
//	    	return messageSource;
//	    }
//		
	
}