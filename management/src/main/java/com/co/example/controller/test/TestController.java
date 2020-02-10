package com.co.example.controller.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.controller.product.ProductController;
import com.co.example.entity.brand.TBrBrand;
import com.co.example.entity.brand.TBrProductBrand;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.brand.TBrProductBrandService;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.solr.SolrService;
import com.co.example.utils.BaseDataUtil;
import com.google.common.io.Files;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("test")
public class TestController {
	@Inject
	TBrProductService tBrProductService;
	
	@Inject
	TBrBrandService tBrBrandService;

	@Inject
	TBrProductBrandService tBrProductBrandService;
	
	@Inject
	SolrService solrService;
	
	
	@ResponseBody
	@RequestMapping(value = "/t1", method = { RequestMethod.GET, RequestMethod.POST })
	public void getPBFromFile(int index){
		String bcName = "";
		String beName = "";
		String pName = "";
		String sn = "";
		Long pid = 0l;
		Long bid = 0l;
		TBrBrand tBrBrand;
		int suc = 0;
		List<TBrProduct> pList =  null;
				String bcNameTmp = "";
		String beNameTmp = "";
		Long bidTmp = 0l;
		TBrProductQuery  tmp =null;
		int i = index;	
		try {
			List<String> readLines = Files.readLines(new File("/root/cfdatest/t_br_brand_product.txt"), 
					Charset.forName("gbk"));
			log.info("size:"+readLines.size());
			for (; i < readLines.size(); i++) {
				String str = readLines.get(i);
//				Thread.sleep(1000l);
				String[] split = str.split(",");
				try {
					pName =  split[1];
					sn =  split[2];
					bcName = split[0];
					beName =  split[3];
				} catch (Exception e) {
					log.info("字段长度不足");
//					e.printStackTrace();
				}
				//根据名称查产品
				if(StringUtils.isNotBlank(pName)){
					TBrProductQuery query = new TBrProductQuery();
					query.setProductNameLike(pName);
					pList = tBrProductService.queryList(query);
				}
				//根据备案号查产品
				if(pList.size() == 0 && StringUtils.isNotBlank(sn)){
					TBrProductQuery query = new TBrProductQuery();
					query.setApplySn(sn);
					pList = tBrProductService.queryList(query);
				}
				//pid 多个      
				if(pList.size() != 0){
					//计算品牌id
					log.info("查询到了"+pName);
					if(bcName.equals(bcNameTmp) || beName.equals(beNameTmp)){
						bid = bidTmp;
					}
					if(bid == 0 && StringUtils.isNotBlank(bcName)){
						TBrBrand query = new TBrBrand();
						query.setName(bcName);
						List<TBrBrand> bList = tBrBrandService.queryList(query);
						if(bList.size()>0){
							tBrBrand = bList.get(0);
							bid = tBrBrand.getId();
							bcNameTmp = bcName;
							bidTmp = bid;
						}
					}
					if(bid == 0 && StringUtils.isNotBlank(beName)){
						TBrBrand query = new TBrBrand();
						query.setNameEn(beName);
						List<TBrBrand> bList = tBrBrandService.queryList(query);
						if(bList.size()>0){
							tBrBrand = bList.get(0);
							bid = tBrBrand.getId();
						}
					}
					if(bid != 0){
						log.info("查询到了品牌"+bcName+"("+beName+")"+"开始关联");
						//查看是否关联，未关联则关联。
						for(TBrProduct p :pList){
							pid = p.getId();
							TBrProductBrand tBrProductBrand = new TBrProductBrand();
							tBrProductBrand.setBrandId(bid);
							tBrProductBrand.setProductId(pid);
							long queryCount = tBrProductBrandService.queryCount(tBrProductBrand);
							
							if(queryCount>0){
								log.info("已经有关联关系，不必再进行关联。");
							}else{
								BaseDataUtil.setDefaultData(tBrProductBrand);
								tBrProductBrandService.add(tBrProductBrand);
								//做品牌冗余
								tmp = new TBrProductQuery();
								tmp.setId(pid);
								tmp.setProductBrandId(bid);
								tmp.setProductBrandName(bcName);
								tBrProductService.updateByIdSelective(tmp);
//								solrService.updateByIdSelective(pid+"", "brands", bcName);
								log.info("关联成功"+suc++);	
							}
						}
					}
				}
				bid = 0l;
				pid = 0l;
				log.info("已经匹配到i行="+i);
			}
		} catch (IOException e) {
			log.info("getPBFromFile error!");
			e.printStackTrace();
		}
	}
	
}
