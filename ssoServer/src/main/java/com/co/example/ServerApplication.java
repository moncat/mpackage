package com.co.example;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.ComponentScan;

/*
注意官方例子 https://github.com/spring-cloud-samples/authserver 没有改sessionID，
而是用了@SessionAttributes("authorizationRequest"),待研究......
 */

@SpringBootApplication
@ComponentScan(basePackageClasses = ServerApplication.class)
public class ServerApplication 
implements ServletContextInitializer 
{

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}


	@Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.getSessionCookieConfig().setName("SESSIONID");//这很重要!
    }


}

