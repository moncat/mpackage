package com.co.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = { "com.co" })
public class SampleMwebApp {
    public static void main(String[] args) throws Exception {
    	SpringApplication.run(SampleMwebApp.class, args);
    }
}
