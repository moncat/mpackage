package com.co.example;

import java.util.ArrayList;
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
	TBrProductSpecService tBrProductSpecService;

	@Inject
	TBrProductCommentStatisticsService tBrProductCommentStatisticsService;

	@Inject
	TBrProductLabelService tBrProductLabelService;

	@Inject
	TBrLabelService tBrLabelService;

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
	public void delAll() throws Exception {
		log.info("*************start**delAll**************");
		solrService.deleteProducts();
		log.info("**************end**delAll*************");
	}

	@Test
	public void addSolrData() throws Exception {
		log.info("*************start**addSolrData**************");
		TBrProductQuery query = new TBrProductQuery();
		query.setUpdateBy(6l);
		long queryCount = tBrProductService.queryCount(query);
		log.info("solr全量数据--需要同步" + queryCount);
		// 784125
//		int pagesize = 50;
		 int pagesize = 1000;
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
				TBrProductSolr tBrProductSolr = new TBrProductSolr();
				Long id = tBrProduct.getId();
				tBrProductSolr.setId(id);
				tBrProductSolr.setEnterpriseName(tBrProduct.getEnterpriseName());
				tBrProductSolr.setProductName(tBrProduct.getProductName());
				tBrProductSolr.setConfirmDate(tBrProduct.getConfirmDate());
				tBrProductSolr.setApplySn(tBrProduct.getApplySn());
				tBrProductSolr.setBeid(tBrProduct.getEnterpriseId() + ""); // 企业ID
				// 生产企业ids 
				TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
				tBrEnterpriseQuery.setIsProduct(Constant.YES);
				tBrEnterpriseQuery.setJoinFlg(true);
				tBrEnterpriseQuery.setProductId(id);
				List<TBrEnterprise> tBrEnterpriseList = tBrEnterpriseService.queryList(tBrEnterpriseQuery);			
				List<String> enterpriseIdsArr = Lists.newArrayList();
				for (TBrEnterprise tBrEnterprise : tBrEnterpriseList) {
					enterpriseIdsArr.add(tBrEnterprise.getId() + "");
				}			
				tBrProductSolr.setPeids(String.join(",", enterpriseIdsArr));

				// 成分id 成分名称
				TBrIngredientQuery tBrIngredientQuery = new TBrIngredientQuery();
				tBrIngredientQuery.setJoinFlg(true);
				tBrIngredientQuery.setProductId(id);
				List<TBrIngredient> tBrIngredientList = tBrIngredientService.queryList(tBrIngredientQuery);
				List<String> iNameArr = Lists.newArrayList();
				List<String> iIdsArr = Lists.newArrayList();
				for (TBrIngredient tBrIngredient : tBrIngredientList) {
					iNameArr.add(tBrIngredient.getName());
					iIdsArr.add(tBrIngredient.getId() + "");
				}
				tBrProductSolr.setIngredients(String.join(",", iNameArr));
				tBrProductSolr.setIids(String.join(",", iIdsArr));

				//品牌
				TBrBrandQuery tBrBrandQuery = new TBrBrandQuery();
				tBrBrandQuery.setJoinFlg(true);
				tBrBrandQuery.setProductId(id);
				List<TBrBrand> brandList = tBrBrandService.queryList(tBrBrandQuery);
				List<String> bNameArr = Lists.newArrayList();
				List<String> bIdsArr = Lists.newArrayList();
				for (TBrBrand tBrBrand : brandList) {
					bNameArr.add(tBrBrand.getName());
					bIdsArr.add(tBrBrand.getId() + "");
				}
				tBrProductSolr.setBrands(String.join(",", bNameArr));
				tBrProductSolr.setBids(String.join(",", bIdsArr));

				//标签
				TBrLabelQuery tBrLabelQuery = new TBrLabelQuery();
				tBrLabelQuery.setProductJoinFlg(true);
				tBrLabelQuery.setProductId(id);
				List<TBrLabel> labelList = tBrLabelService.queryList(tBrLabelQuery);
				List<String> lIdsArr = Lists.newArrayList();
				for (TBrLabel tBrLabel : labelList) {
					lIdsArr.add(tBrLabel.getId()+"");
				}
				tBrProductSolr.setLids(String.join(",", lIdsArr));
				
				tBrProductSolrList.add(tBrProductSolr);
				
				TBrProduct productTmp = new TBrProduct();
				productTmp.setId(tBrProduct.getId());
				productTmp.setUpdateBy(7l);
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

}
