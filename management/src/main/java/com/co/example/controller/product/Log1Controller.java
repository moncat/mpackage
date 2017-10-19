package com.co.example.controller.product;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.co.example.common.utils.HttpUtils;
import com.co.example.common.utils.PageReq;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.ProductConstant;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("log1")
public class Log1Controller {
	
	@Resource
	TBrProductService service;
	@Resource
	TBrEnterpriseService tBrEnterpriseService;
	
	Byte cfda =1;
	//临时使用，修正产品的运营企业名称  已操作完毕  禁止继续访问
	//@RequestMapping(value = "/ename", method = { RequestMethod.GET, RequestMethod.POST })
	public void ename() throws InterruptedException {
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setSource(cfda);
		tBrProductQuery.setUpdateBy(0l);
		PageReq pageReq = new PageReq();
		pageReq.setPageSize(2000);
		pageReq.setPage(1);
		pageReq.setSort(new Sort(Direction.ASC,"t.confirm_date"));
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
					if(j>0 && j%100 == 0){
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
		
}
