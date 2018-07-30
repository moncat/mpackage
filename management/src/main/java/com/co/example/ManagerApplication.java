package com.co.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


@SpringBootApplication
@EnableScheduling //支持定时器
@EnableGlobalMethodSecurity(securedEnabled = true ,jsr250Enabled = true,prePostEnabled = true)
@ComponentScan(basePackages = "com.co")
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
    
 
    
}

// 使用如下
//@EnableGlobalMethodSecurity(securedEnabled = true ,jsr250Enabled = true,prePostEnabled = true)
//@Secured({"ROLE_sa","ROLE_admin"})
//@RolesAllowed({"ROLE_user","ROLE_admin"})
//@PreAuthorize("hasRole('ROLE_sa') or hasRole('ROLE_user')")