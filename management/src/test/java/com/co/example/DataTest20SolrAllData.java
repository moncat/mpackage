package com.co.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.PageReq;
import com.co.example.entity.brand.TBrBrand;
import com.co.example.entity.brand.aide.TBrBrandQuery;
import com.co.example.entity.label.TBrLabel;
import com.co.example.entity.label.aide.TBrLabelQuery;
import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrEnterpriseQuery;
import com.co.example.entity.product.aide.TBrIngredientQuery;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.entity.product.aide.TBrProductSolr;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.brand.TBrProductBrandService;
import com.co.example.service.comment.TBrProductCommentStatisticsService;
import com.co.example.service.enterprise.TBrEnterpriseBaseService;
import com.co.example.service.enterprise.TBrEnterpriseRegisterService;
import com.co.example.service.export.TBrExportService;
import com.co.example.service.label.TBrLabelService;
import com.co.example.service.label.TBrProductLabelService;
import com.co.example.service.product.TBrAimService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrIngredientService;
import com.co.example.service.product.TBrProductEnterpriseService;
import com.co.example.service.product.TBrProductImageService;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.product.TBrProductSpecService;
import com.co.example.service.solr.SolrService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest20SolrAllData {

	@Autowired
	SolrService solrService;

	@Inject
	TBrExportService tBrExportService;

	@Inject
	TBrIngredientService tBrIngredientService;

	@Inject
	TBrProductService tBrProductService;

	@Inject
	TBrAimService tBrAimService;

	@Inject
	TBrProductImageService tBrProductImageService;

	@Inject
	TBrEnterpriseService tBrEnterpriseService;

	@Inject
	TBrProductBrandService tBrProductBrandService;

	@Inject
	TBrBrandService tBrBrandService;
	
	@Inject
	TBrLabelService tBrLabelService;

	@Inject
	TBrProductSpecService tBrProductSpecService;

	@Inject
	TBrProductCommentStatisticsService tBrProductCommentStatisticsService;

	@Inject
	TBrProductLabelService tBrProductLabelService;

	@Inject
	TBrProductEnterpriseService tBrProductEnterpriseService;

	@Inject
	TBrEnterpriseRegisterService tBrEnterpriseRegisterService;

	@Inject
	TBrEnterpriseBaseService tBrEnterpriseBaseService;

	// @Test
	public void getData() throws InterruptedException {
		log.info("*************start****************");
		TBrProductQuery query = new TBrProductQuery();
		Map<String, Object> map = solrService.queryProductSolr(query, 0, 30);
		System.out.println(map);
		log.info("*************end****************");
	}

	 @Test
	public void updateByIdSelective() throws Exception {

		log.info("*************start**updateByIdSelective**************");
		solrService.updateByIdSelective("3549461673213952", "brands", "");
		log.info("**************end**updateByIdSelective*************");
	}

	// @Test
	public void updateProductByIdSelective() throws Exception {
		log.info("*************start**updateByIdSelective**************");

		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setIsChina(Constant.NO);
		List<TBrProduct> queryList = tBrProductService.queryList(tBrProductQuery);
		Long id = 0l;
		// 16489
		String confirmDate = null;
		for (int i = 189; i < queryList.size(); i++) {
			TBrProduct tBrProduct = queryList.get(i);
			id = tBrProduct.getId();
			confirmDate = tBrProduct.getConfirmDate();
			solrService.updateByIdSelective(id + "", "confirmDate", confirmDate);
			log.info("**id**" + id + "**" + confirmDate + "**" + i);
		}

		log.info("**************end**updateByIdSelective*************");
	}

	// @Test
	// public void updateInBatch() throws Exception {
	// log.info("*************start**updateInBatch**************");
	// HashMap<String, String> map = Maps.newHashMap();
	// solrService.updateInBatch("", map);
	// log.info("**************end**updateInBatch*************");
	// }

	// @Test
	public void delAll() throws Exception {
		log.info("*************start**delAll**************");
		solrService.deleteProducts();
		log.info("**************end**delAll*************");
	}

//	@Test
	public void addSolrData() throws Exception {
		log.info("*************start**addSolrData**************");
		TBrProductQuery query = new TBrProductQuery();
		query.setUpdateBy(7l);
		long queryCount = tBrProductService.queryCount(query);
		log.info("solr全量数据--需要同步" + queryCount);
		int pagesize = 1;
		if (queryCount > 0) {
			// while (queryCount > 0) {
			List<TBrProductSolr> tBrProductSolrList = new ArrayList<TBrProductSolr>();
			List<TBrProduct> tBrProductTmpList = new ArrayList<TBrProduct>();
			PageReq pageReq = new PageReq();
			pageReq.setPageSize(pagesize);
			pageReq.setPage(0);
			Page<TBrProduct> page = tBrProductService.queryPageList(query, pageReq);
			List<TBrProduct> content = page.getContent();
			for (TBrProduct tBrProduct : content) {
				TBrProductSolr tBrProductSolr = solrService.getSolrBean(tBrProduct);
				//solr列表
				tBrProductSolrList.add(tBrProductSolr);
				//批量更新列表
				TBrProduct productTmp = new TBrProduct();
				productTmp.setId(tBrProduct.getId());
				productTmp.setUpdateBy(8l);
				tBrProductTmpList.add(productTmp);
				
			}
			solrService.syncProducts(tBrProductSolrList);
			tBrProductService.updateInBatch(tBrProductTmpList);
			tBrProductSolrList.clear();
			tBrProductTmpList.clear();
			queryCount -= pagesize;
			log.info("solr全量数据--同步中" + queryCount);
		}
		log.info("solr全量数据--同步数据完毕");
		log.info("*************end**addSolrData**************");
	}
	
	
//	@Test
	public void addOneData() throws Exception {
		Long id = 3511229152509952l;
		log.info("*************start**addOne**************");
		TBrProduct tBrProduct = tBrProductService.queryById(id);
		Integer syncOne = solrService.syncOne(tBrProduct,true,10l);
		if(syncOne==1){
			System.out.println(syncOne);
		}else if(syncOne==2){
			System.out.println(syncOne);
		}else if(syncOne==3){
			System.out.println(syncOne);
		}else if(syncOne==4){
			System.out.println(syncOne);
		}
		log.info("*************end**addOneData**************");
	}
	
	
	//正在使用
//	@Test
	public void addSolrData2() throws Exception {
		log.info("*************start**addSolrData**************");
		TBrProductQuery query = new TBrProductQuery();
		query.setUpdateBy(7l);
		long queryCount = tBrProductService.queryCount(query);
		log.info("solr全量数据--需要同步" + queryCount);
		int pagesize = 500;
		while (queryCount > 0) {
			PageReq pageReq = new PageReq();
			pageReq.setPageSize(pagesize);
			pageReq.setPage(0);
			Page<TBrProduct> page = tBrProductService.queryPageList(query, pageReq);
			List<TBrProduct> content = page.getContent();
			for (TBrProduct tBrProduct : content) {
				solrService.syncOne(tBrProduct,true,10l);
//				Integer syncOne = solrService.syncOne(tBrProduct,true,10l);
//				System.out.println("*****"+syncOne+"*****"+tBrProduct.getId());				
			}
			queryCount -= pagesize;
			log.info("solr全量数据--同步中" + queryCount);
		}
		log.info("solr全量数据--同步数据完毕");
		log.info("*************end**addSolrData**************");
	}

	
//	@Test
	public void queryCount() throws Exception {
		log.info("*************start**queryCount**************");
		Long queryCount = solrService.queryCount();
		System.out.println("count=="+queryCount);
		log.info("*************end**queryCount**************");
	}

}
