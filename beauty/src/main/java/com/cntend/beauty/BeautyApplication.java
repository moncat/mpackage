package com.cntend.beauty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling //支持定时器
@ComponentScan(basePackages ={"com.cntend","com.co"})
public class BeautyApplication {
    public static void main(String[] args) {
        SpringApplication.run(BeautyApplication.class, args);
    }
}

