package com.co.example;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.entity.label.TBrLabel;
import com.co.example.service.label.TBrLabelService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest14 {
	
	@Autowired	
	TBrLabelService  tBrLabelService;
	
	//给商品标签做匹配
	@Test
	public void getData() throws InterruptedException{
		List<TBrLabel> list = tBrLabelService.queryList();
		int size = list.size();
		for (TBrLabel t : list) {
			size--;
			int count = tBrLabelService.addConnect2Product(t);
			log.info(size+"");
			log.info(t.getName()+"成功数量："+count);
		}
	}
}




