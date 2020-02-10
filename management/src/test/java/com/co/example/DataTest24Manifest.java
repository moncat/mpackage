package com.co.example;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.common.constant.Constant;
import com.co.example.entity.admin.TAdmin;
import com.co.example.entity.admin.aide.TAdminQuery;
import com.co.example.entity.brand.TBrBrand;
import com.co.example.entity.manifest.TBrManifest;
import com.co.example.entity.manifest.TBrManifestResult;
import com.co.example.entity.manifest.aide.TBrManifestAuthQuery;
import com.co.example.entity.manifest.aide.TBrManifestAuthVo;
import com.co.example.entity.manifest.aide.TBrManifestResultQuery;
import com.co.example.service.admin.TAdminService;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.manifest.TBrManifestAuthService;
import com.co.example.service.manifest.TBrManifestResultService;
import com.co.example.service.manifest.TBrManifestService;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest24Manifest {

	
	@Inject
	TBrManifestAuthService tBrManifestAuthService;
	@Inject
	TBrManifestResultService tBrManifestResultService;
	@Inject
	TAdminService tAdminService;
	@Inject
	TBrManifestService tBrManifestService;
	@Inject
	TBrBrandService tBrBrandService;
	//###################测试数据
	@Test
	public void pie(){
//		TBrManifest tBrManifest = tBrManifestService.queryById(3432835235921920l); //产品
//		TBrManifest tBrManifest = tBrManifestService.queryById(3432831795265536l); //品牌
//		TBrManifest tBrManifest = tBrManifestService.queryById(3432857769639936l); //供应商
//		TBrManifest tBrManifest = tBrManifestService.queryById(3440173151584256l); //成分
		
//		TBrManifest tBrManifest = tBrManifestService.queryById(3453142097903616l); //e3
//		TBrManifest tBrManifest = tBrManifestService.queryById(3458725969133568l); //p1104
//		TBrManifest tBrManifest = tBrManifestService.queryById(3458807460773888l); //b1104
//		TBrManifest tBrManifest = tBrManifestService.queryById(3458808264572928l); //b1104
//		TBrManifest tBrManifest = tBrManifestService.queryById(3458754428059648l); //admin 二线品牌清单
//		TBrManifest tBrManifest = tBrManifestService.queryById(3462578502205440l); //admin 顶级品牌清单
		TBrManifest tBrManifest = tBrManifestService.queryById(3465437063692288l); //admin 成分测试
		tBrManifestService.queryManifest(tBrManifest);
	}
		
	
//	@Test
	public void test2(){
		TBrBrand queryByProductId = tBrBrandService.queryByProductId(2327568749281280l);
		log.info("123");
	}
		
	//########################
	public void getData1() {
		TAdminQuery tAdminQuery = new TAdminQuery();
		tAdminQuery.setDelFlg(Constant.NO);
		List<TAdmin> queryList = tAdminService.queryList(tAdminQuery);
		List<TAdmin> list = queryList.stream().filter(s-> s.getId()!=2316245057224704l).collect(Collectors.toList());
		log.info("123");
	}

		
	public void getData() {
		try {
			TBrManifestAuthQuery query = new TBrManifestAuthQuery();
			Long adminId = 1l;
			query.setUsingBy(adminId);
			query.setJoinManifestFlg(true);
			query.setDelFlg(Constant.NO);
			query.setIsTop(Constant.YES);
			List<TBrManifestAuthVo> queryList = tBrManifestAuthService.queryList(query);	
			for (TBrManifestAuthVo one : queryList) {
				int keyId = one.getType().intValue()*100;
				TBrManifestResultQuery tBrManifestResultQuery = new TBrManifestResultQuery();
				tBrManifestResultQuery.setManifestId(one.getManifestId());
				tBrManifestResultQuery.setKeyId(keyId);
				TBrManifestResult result = tBrManifestResultService.queryOne(tBrManifestResultQuery);
				one.setResult(result);
			}
			log.info("完毕");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
