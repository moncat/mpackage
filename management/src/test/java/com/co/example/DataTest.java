package com.co.example;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.entity.contact.TContact;
import com.co.example.service.contact.TContactService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest {
	
	@Resource
    private TContactService service;
	
	@Test
	public void testOne() {
		TContact t = new TContact();
		t.setCompanyName("lzx");
		service.add(t);
		log.debug("******debug");
	}
	
	public static void main(String[] args) throws InterruptedException {
//		String a="123";
//		System.out.println(a.replace("1", "5").replace("2", "6"));
		System.out.println(1);
		Thread.sleep(2000);
		System.out.println(2);
		Thread.sleep(4000);
		System.out.println(3);
	}
	
	
}

