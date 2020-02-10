package com.co.example;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.co.example.common.constant.Constant;
import com.co.example.common.utils.excel.ExcelUtil;
import com.co.example.entity.enterprise.TBrEnterprisePermission;
import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.aide.BeVo;
import com.co.example.entity.product.aide.ProductConstant;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.enterprise.TBrEnterpriseBaseService;
import com.co.example.service.enterprise.TBrEnterprisePermissionService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrProductService;
import com.co.example.simulateLogin.BrowserFactory;
import com.co.example.utils.BaseDataUtil;
import com.google.common.collect.Maps;
import com.google.common.io.Files;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest3GetEnterpriseInfo {

	@Autowired
	TBrEnterpriseService tBrEnterpriseService;
	@Autowired
	TBrEnterpriseBaseService tBrEnterpriseBaseService;
	@Autowired	
	TBrProductService tBrProductService;
	@Autowired	
	TBrEnterprisePermissionService tBrEnterprisePermissionService;
	
	WebDriver chrome ;
	String enterpriseName,producingArea;
	String url = "https://www.tianyancha.com/search?key=";
	Long id = 0l;
	
//	@Test
	public void getBeData() throws Exception {
		List<BeVo> list = tBrProductService.queryBeData();
		int size = list.size();
		BeVo one = null;
		chrome = BrowserFactory.getChrome();
		Thread.sleep(40*1000);
		log.info("睡眠40s，请手动登录");
		log.info("***需要获取的企业数据数量***" + size);
		if (size>0) {
			int index = size;
			for (int i = 0; i < size; i++) {
				one = list.get(i);
				enterpriseName = one.getEName();
				producingArea = one.getEArea();
				String newurl = url + enterpriseName;
				Boolean flg = getDoc(newurl);
				if(!flg){
					log.info("***40s内并未手动设置验证码，回滚事务，停机休息***");
					break;
				}
				System.out.println("******" + i+"*****"+index--);
			}
		}
		log.info("全部抓取完毕");
		
	}
	
//	@Test
	public void getBeData2() throws Exception {
		enterpriseName = "广州旭升化工科技有限公司";
		producingArea = "";
		String newurl = url + enterpriseName;
		chrome = BrowserFactory.getChrome();
		Thread.sleep(40*1000);
		log.info("睡眠40s，请手动登录");
		getDoc(newurl);
	}

	private Boolean getDoc(String urlSearch) {		
		
		TBrEnterprise tBrEnterprise = new TBrEnterprise();
		tBrEnterprise.setEnterpriseName(enterpriseName);
		tBrEnterprise.setProducingArea(producingArea);
		tBrEnterprise.setIsBus(Constant.YES);
		BaseDataUtil.setDefaultData(tBrEnterprise);
		try {
			tBrEnterpriseService.addMoreEnterpriseInfo(urlSearch, tBrEnterprise,chrome);
		} catch (Exception e) {
			log.info("抛出异常----"+e.getMessage());
			e.printStackTrace();
			return false;
		}
		System.out.println("本次抓取完成");
		return true;
	}
	
	//*************2019年9月4日****************************
	
//	@Test
	public void getEnterprisePermission() throws Exception {
		Integer startPage =1;
		Integer endPage =335;
		if (startPage != null && endPage != null) {
			for (int i = startPage; i <= endPage; i++) {
				tBrEnterprisePermissionService.getEnterprisePermission(i, "","");
				log.info("抓取中，抓取页数*****"+i);
			}
		}  
		log.info("抓取完毕");
	}
	
	//*************2019年11月30日****************************
	
	public void getEnterprisePermissionFromWebFile() throws Exception {
		List<String> readLines = Files.readLines(new File("D:\\Desktop\\t_br_xk_web.csv"),Charset.forName("gbk"));
		log.info("size:"+readLines.size());
		int saveNum = 0;
		for (int i = 560; i < readLines.size(); i++) {
			String str = readLines.get(i);
			int enterprisePermission = tBrEnterprisePermissionService.getEnterprisePermission(str);
			if(enterprisePermission ==1){
				saveNum++;
				log.info("当前保存个数webFile=="+saveNum);
			}
			log.info("当前解析webFile=="+i);
		}			
		log.info("抓取完毕webFile");
	}
	
	@Test
	public void getEnterprisePermissionJsonFromWebFile() throws Exception {
		List<Map<String, String>> list = ExcelUtil.getList();
		log.info("size:"+list.size());
		int saveNum = 0;
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			int enterprisePermission = tBrEnterprisePermissionService.getEnterprisePermissionJson(map);
			if(enterprisePermission ==1){
				saveNum++;
				log.info("当前保存个数webFile=="+saveNum);
			}
			log.info("当前解析webFile=="+i);
		}			
		log.info("抓取完毕webFile");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}