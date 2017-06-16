package com.co.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = { "com.co" })
@EnableScheduling //支持定时器
public class SampleScheduleApp {
    public static void main(String[] args) throws Exception {
    	SpringApplication.run(SampleScheduleApp.class, args);
    }
}
