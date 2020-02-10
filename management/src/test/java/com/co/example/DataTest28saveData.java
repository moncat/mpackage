package com.co.example;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.entity.abc.TBrAbc;
import com.co.example.entity.brand.TBrBrand;
import com.co.example.entity.brand.TBrProductBrand;
import com.co.example.entity.brand.aide.TBrProductBrandQuery;
import com.co.example.entity.category.TBrCategory;
import com.co.example.entity.category.TBrProductCategory;
import com.co.example.entity.category.aide.TBrProductCategoryQuery;
import com.co.example.service.abc.TBrAbcService;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.brand.TBrProductBrandService;
import com.co.example.service.category.TBrCategoryService;
import com.co.example.service.category.TBrProductCategoryService;
import com.co.example.service.product.TBrIngredientService;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.solr.SolrService;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest28saveData {
	

	
	@Inject
	TBrAbcService  tBrAbcService;
	
	@Inject
	SolrService  SolrService;
	
	
	@Inject
	TBrProductCategoryService  categoryService;
	
	@Inject
	TBrCategoryService  cService;
	
	@Inject
	TBrProductBrandService  brandService;
	
	@Inject
	TBrBrandService  bService;
	
	@Inject
	TBrProductService  pService;
	@Inject
	TBrIngredientService  iService;
	

	
//	@Test
	public void saveData(){
		List<TBrAbc> queryList = tBrAbcService.queryList();
		int size = queryList.size();
		log.info("size:"+size);
		for(TBrAbc tBrAbc : queryList){
			Long id = tBrAbc.getId();
			
			TBrAbc up = new TBrAbc();
			up.setId(id);
			
			TBrProductCategoryQuery cq = new TBrProductCategoryQuery();
			cq.setProductId(id);
			List<TBrProductCategory> queryList2 = categoryService.queryList(cq);			
			if(queryList2.size()>0){
				TBrProductCategory tBrProductCategory = queryList2.get(0);
				up.setCategoryId(tBrProductCategory.getCategoryId());
				TBrCategory queryById = cService.queryById(tBrProductCategory.getCategoryId());
				up.setCategoryName(queryById.getName());
			}
			
			TBrProductBrandQuery cb = new TBrProductBrandQuery();
			cb.setProductId(id);
			List<TBrProductBrand> queryList3 = brandService.queryList(cb);
			if(queryList3.size()>0){
				TBrProductBrand tBrProductBrand = queryList3.get(0);
				up.setProductBrandId(tBrProductBrand.getBrandId());
				TBrBrand queryById = bService.queryById(tBrProductBrand.getBrandId());
				up.setProductBrandName(queryById.getName());
			}			
			Float productScore = iService.getProductScore(id);
			up.setIScore(BigDecimal.valueOf(productScore));
			tBrAbcService.updateByIdSelective(up);
			
			size--;
			if(size%100==0){
				log.info("size now:"+size);
			}
		}
		
	}
		
	
}













