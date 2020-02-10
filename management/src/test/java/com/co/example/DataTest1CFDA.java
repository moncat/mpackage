package com.co.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.co.example.common.utils.HttpUtils;
import com.co.example.common.utils.PageReq;
import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.ProductConstant;
import com.co.example.entity.product.aide.TBrEnterpriseQuery;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrProductService;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest1CFDA {
	
	@Resource
    private TBrProductService service;
	
	@Resource
	private TBrEnterpriseService tBrEnterpriseService;
	
	public void test2() throws InterruptedException {
		for (int i = 1009; i <= 1020; i++) {
			service.addProductFromCFDA(i+"","");
			log.info("******"+i);
		}
	}
	
	public void test3() throws InterruptedException {
		service.addProductFromBEVOL(6,8);
	}
	
	Byte cfda =1;
	
	//@Test
	public void test4() throws InterruptedException {
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setSource(cfda);
		tBrProductQuery.setUpdateBy(0l);
		PageReq pageReq = new PageReq();
		pageReq.setPageSize(2000);
		pageReq.setPage(1);
		String cfdaProcessid =  null;
		String text = null;
		String oldName = null;
		String newName = null;
		long queryCount = service.queryCount(tBrProductQuery);
		log.info("待处理**"+queryCount);
		List<TBrProduct> tmpList= new ArrayList<TBrProduct>();
		//658606
		int i =0;
		int j =0;
		while(queryCount >0){
			Page<TBrProduct> list = service.queryPageList(tBrProductQuery, pageReq);
			TBrProduct tBrProduct4Update = null;
			for (TBrProduct tBrProduct : list) {
				try {
					i++;
					cfdaProcessid = tBrProduct.getCfdaProcessid();
					if(StringUtils.isBlank(cfdaProcessid)){
						continue;
					}
					text = HttpUtils.postData(ProductConstant.CFDA_INGREDIENT_URL, "processid="+cfdaProcessid);
					JSONObject  base =  JSON.parseObject(text);
					JSONObject unitInfo = base.getJSONObject("scqyUnitinfo");
					oldName = tBrProduct.getEnterpriseName();
					newName = unitInfo.getString("enterprise_name");
					tBrProduct4Update = new TBrProduct();
					tBrProduct4Update.setId(tBrProduct.getId());
					// update_by   0未处理    1运营即生产      3 运营更换好名称      6美丽修行
					tBrProduct4Update.setUpdateBy(2l);
					if(StringUtils.isNoneBlank(newName) && !StringUtils.equals(oldName, newName)){
						tBrProduct4Update.setEnterpriseName(newName);
						j++;
					}
					tmpList.add(tBrProduct4Update);
					if(tmpList.size()%100 == 0){
						service.updateInBatch(tmpList);
					}
					if(i%100 == 0){
						log.info("已处理**"+i);
					}
					if(j%100 == 0){
						log.info("已匹配**"+j);
					}
				} catch (Exception e) {
					continue;
				}
			}
			queryCount = service.queryCount(tBrProductQuery);
		}
		if(tmpList.size()>0){
			log.info("***快结束**");
			service.updateInBatch(tmpList);
		}
		log.info("***结束！！**");
	}
	

	//SELECT count(*)
	//FROM `t_br_product` t 
	//where  t.enterprise_name  = '11111'
	//特殊处理单条数据                                                          over!!!!!!!!!!!!!!!!!!!!!!!!
	public void test5() {
		HashMap<String, String> params = Maps.newHashMap();
		params.put("on", "true");
		params.put("page", "1"); //67133
		params.put("pageSize", "15");
		params.put("conditionType", "1");
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setEnterpriseName("11111");
		List<TBrProduct> list = service.queryList(tBrProductQuery);
		String productName = null;
		TBrProduct tBrProductTmp = null;
		int i= list.size();
		for (TBrProduct tBrProduct : list) {
			i--;
			System.out.println("*******"+i);
			try {
				productName = tBrProduct.getProductName();
				params.put("productName", productName);
				String text = HttpUtils.postData(ProductConstant.CFDA_PRODUCT_URL, params);
				JSONObject  base =  JSON.parseObject(text);
				JSONArray productList =  base.getJSONArray("list");
				JSONObject product =  productList.getJSONObject(0);
				String name = product.getString("enterpriseName");
				tBrProductTmp = new TBrProduct();
				tBrProductTmp.setId(tBrProduct.getId());
				tBrProductTmp.setEnterpriseName(name);
				System.out.println("succcccccccc");
				service.updateByIdSelective(tBrProductTmp);
			} catch (Exception e) {
				System.out.println("errrrrrrrr");
				continue;
			}
		}
	}
	
	//实际企业表中的地址和产品表相匹配                                  over!!!!!!!!!!!!!!!!!!!!!!!!
//	@Test  
	public void test6() {
		TBrEnterprise tBrEnterprise  = null;
		String producingArea = null;
		int num =0;
		int count =0;
		List<TBrEnterprise> list = tBrEnterpriseService.queryList();
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		int size = list.size();
		System.out.println("***size***"+size);
		for (int i = 162; i < 2000; i++) {
			tBrEnterprise = list.get(i);
			producingArea = tBrEnterprise.getProducingArea();
			System.out.println("***i***"+i+"***"+producingArea);
			if(StringUtils.isNoneBlank(producingArea)){
				tBrProductQuery.setProducingArea(producingArea);
				num = service.updateByArea(tBrProductQuery);
				System.out.println("***update***"+num);
				count =count+num;
				System.out.println("***updateAll***"+count);
			}
		}
		
	}
		
	
//		String text = HttpUtils.postData(ProductConstant.CFDA_INGREDIENT_URL, "processid="+"20170811142327xr9ot");
//		System.out.println(text);

}














