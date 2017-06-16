package com.co.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = { "com.co" })
public class SampleJspApp extends SpringBootServletInitializer{
	
    @Override  
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {  
        return application.sources(SampleJspApp.class);  
    }  
	
    public static void main(String[] args) throws Exception {
    	SpringApplication.run(SampleJspApp.class, args);
    }
}
