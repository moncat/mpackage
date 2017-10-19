package com.co.example;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.PageReq;
import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.TBrProductOperEnterprise;
import com.co.example.entity.product.aide.TBrEnterpriseQuery;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrProductOperEnterpriseService;
import com.co.example.service.product.TBrProductService;

import lombok.extern.slf4j.Slf4j;

/**
 * 获得运营企业数据（手动）
 * 主要维护t_br_product_oper_enterprise表（匹配表）
 * 查询出t_br_product（产品表） 在匹配表中未匹配的数据 create_by =0
 * 1根据名称到 t_br_enterprise（企业表）进行匹配，匹配成功则保存到匹配表
 * 2没有匹配成功则到外网抓取
 * 该数据量较大，需要全量抓取，并增量抓取 TODO  
 * @author zyl
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest6GetOperEnterpriseInfo {
		
	@Autowired
	TBrEnterpriseService tBrEnterpriseService;
	
	@Autowired
	TBrProductOperEnterpriseService tBrProductOperEnterpriseService;
	
	@Autowired
	TBrProductService tBrProductService;
	
	@Test
	public void getOperEnterpriseInfo(){
		//1到企业表匹配
		getOperEnterpriseInfoFromTable();
		//2到外网抓取
		getOperEnterpriseInfoFromNet();
		
	}
	/**
	 * 将未匹配的数据的名称保存到 企业表 注意create_by =0 以方便匹配
		1去重 获得要处理的数据
		2到产品表查询出一条数据，并保存到企业表
		3到产品表查询出多条数据，并保存到关联表
		4 企业匹配 到 DataTestGetEnterpriseInfo.class  test2 操作
	 */
	
	private void getOperEnterpriseInfoFromNet() {
		//1去重 获得要处理的数据
		List<String> operEnterpriseNames = tBrProductService.queryOperEnterpriseFromProduct();
		TBrProductQuery tBrProductQuery = null;
		TBrProduct tBrProduct = null;
		TBrProductOperEnterprise tBrProductOperEnterprise = null;
		TBrEnterprise tBrEnterprise = null;
		for (String enterpriseName : operEnterpriseNames) {
			tBrProductQuery = new TBrProductQuery();
			tBrProductQuery.setEnterpriseName(enterpriseName);
			List<TBrProduct> List = tBrProductService.queryList(tBrProductQuery);
			//2到产品表查询出一条数据，并保存到企业表
			tBrProduct = List.get(0);
			tBrEnterprise = new TBrEnterprise();
			tBrEnterprise.setApplySn(tBrProduct.getApplySn());
			tBrEnterprise.setEnterpriseName(tBrProduct.getEnterpriseName());
			tBrEnterprise.setProducingArea(tBrProduct.getProducingArea());
			tBrEnterprise.setCreateBy(0l);
			tBrEnterprise.setCreateTime(new Date());
			tBrEnterprise.setIsActive(Constant.STATUS_ACTIVE);
			tBrEnterprise.setDelFlg(Constant.NO);
			tBrEnterpriseService.add(tBrEnterprise);
			Long enterpriseId = tBrEnterprise.getId();
			for (TBrProduct tBrProductTmp : List) {
				tBrProductOperEnterprise = new TBrProductOperEnterprise();
				tBrProductOperEnterprise.setEnterpriseId(enterpriseId);
				tBrProductOperEnterprise.setProductId(tBrProductTmp.getId());
				tBrProductOperEnterprise.setCreateBy(0l);
				tBrProductOperEnterprise.setCreateTime(new Date());
				tBrProductOperEnterprise.setIsActive(Constant.STATUS_ACTIVE);
				tBrProductOperEnterprise.setDelFlg(Constant.NO);
				tBrProductOperEnterpriseService.add(tBrProductOperEnterprise);
			}
		}
		//3到产品表查询出多条数据，并保存到关联表
		//4 企业匹配 到 DataTestGetEnterpriseInfo.class  test2 操作
	}

	public void getOperEnterpriseInfoFromTable(){
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setCreateBy(0l);
		
		long productCount = tBrProductService.queryCount(tBrProductQuery);
		log.info("当前还有未匹配运营企业的产品个数"+productCount);
		PageReq pageReq = new PageReq();
		pageReq.setPageSize(2000);
		pageReq.setPage(1);
		Page<TBrProduct> productPageList  = null;
		List<TBrProduct> productList  = null;
		String enterpriseName  = null;
		List<TBrEnterprise> enterpriseList = null;
		TBrEnterprise tBrEnterprise = null;
		int totalPages =1;
		TBrProductOperEnterprise tBrProductOperEnterprise = null;
		TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
		Long productId = null;
		Long tmp = 0l;
		int matchCount = 0;
		TBrProduct tBrProductUpdate = null;
		while ( totalPages >0) {
			productCount = tBrProductService.queryCount(tBrProductQuery);
			if(tmp == productCount){
				log.info("***匹配完毕***");
				break;
			}else{
				tmp = productCount;
			}
			productPageList = tBrProductService.queryPageList(tBrProductQuery, pageReq);
			totalPages = productPageList.getTotalPages();
			log.info("总页数***"+totalPages);
			productList = productPageList.getContent();
			
			for (TBrProduct tBrProduct : productList) {
				productId = tBrProduct.getId();
				enterpriseName = tBrProduct.getEnterpriseName();
				tBrEnterpriseQuery.setEnterpriseName(enterpriseName);
				enterpriseList = tBrEnterpriseService.queryList(tBrEnterpriseQuery);
				if(CollectionUtils.isNotEmpty(enterpriseList)){
					tBrEnterprise = enterpriseList.get(0);
					tBrProductOperEnterprise = new TBrProductOperEnterprise();
					tBrProductOperEnterprise.setCreateTime(new Date());
					tBrProductOperEnterprise.setDelFlg(Constant.NO);
					tBrProductOperEnterprise.setIsActive(Constant.STATUS_ACTIVE);
					tBrProductOperEnterprise.setEnterpriseId(tBrEnterprise.getId());
					tBrProductOperEnterprise.setProductId(productId);
					tBrProductOperEnterpriseService.add(tBrProductOperEnterprise);
					matchCount++;
				}
				tBrProductUpdate= new TBrProduct();
				tBrProductUpdate.setCreateBy(1l);
				tBrProductUpdate.setId(productId);
				tBrProductService.updateByIdSelective(tBrProductUpdate);
				
			}
		}
		log.info("匹配完毕，成功匹配个数***"+matchCount);
	}
	
	
	
}