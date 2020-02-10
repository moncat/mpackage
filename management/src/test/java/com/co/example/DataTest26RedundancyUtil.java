package com.co.example;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.entity.brand.TBrBrand;
import com.co.example.service.brand.TBrBrandService;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest26RedundancyUtil {
	

	@Inject
	TBrBrandService  tBrBrandService;
	//冗余企业和品牌关系表  暂时搁置
//	@Test
	public void RedundancyEB(){
		TBrBrand tBrBrand = new TBrBrand();
		tBrBrand.setUpdateBy(1l);
		List<TBrBrand> brands = tBrBrandService.queryList(tBrBrand);
		int size = brands.size();
		for (TBrBrand bTmp : brands) {
			tBrBrandService.addTBrEnterpriseBrandByBrand(bTmp);
			log.info("join-"+size--+"--"+bTmp.getId()+"---"+bTmp.getName());
		}
	}
		
	
}
