package com.co.example.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * 从csdn获得的demo代码
 * 
 * 使用注解标注过滤器
 * @WebFilter将一个实现了javax.servlet.Filter接口的类定义为过滤器
 * 属性filterName声明过滤器的名称,可选
 * 属性urlPatterns指定要过滤 的URL模式,也可使用属性value来声明.(指定要过滤的URL模式是必选属性)
 */
@Slf4j
//@Configuration
//@WebFilter(filterName="myFilter",urlPatterns="/*")
public class MyFilter implements Filter {


    @Override
    public void init(FilterConfig config) throws ServletException {
        System.out.println("过滤器初始化");
        log.debug("过滤器初始化");
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
    		FilterChain chain) throws IOException, ServletException {
    	System.out.println("执行过滤操作");
    	log.debug("执行过滤操作");
    	chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    	log.debug("过滤器销毁");
    	System.out.println("过滤器销毁");
    }
    
}