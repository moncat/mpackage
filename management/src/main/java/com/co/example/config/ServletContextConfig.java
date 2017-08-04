package com.co.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.co.example.base.interceptor.BaseInterceptor;
import com.co.example.base.interceptor.LoginInterceptor;
import com.co.example.base.interceptor.RemembermeInterceptor;
import com.co.example.base.interceptor.SessionInterceptor;
/**
 * 拦截器配置
 * @author zyl
 *
 */
@Configuration
public class ServletContextConfig extends WebMvcConfigurationSupport {

	@Value("${ueditor.savePath}")
	String savepath;
	
	
	/**
     * 发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。
     * 需要重新指定静态资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/","file:"+savepath)
        
        ;
        registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }


    /**
     * 配置servlet处理
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(new BaseInterceptor()).addPathPatterns("/**");
    	registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**");
    	registry.addInterceptor(new RemembermeInterceptor()).addPathPatterns("/**");
    	registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/identifyCode","/loginInit","/login");
    //	registry.addInterceptor(new RoleInterceptor()).addPathPatterns("/**");
        
        super.addInterceptors(registry);
    }
    
}