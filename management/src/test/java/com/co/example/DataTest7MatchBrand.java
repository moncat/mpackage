package com.co.example;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.PageReq;
import com.co.example.entity.brand.TBrBrand;
import com.co.example.entity.brand.TBrProductBrand;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.brand.TBrProductBrandService;
import com.co.example.service.product.TBrProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest7MatchBrand {

	@Resource
	TBrBrandService tBrBrandService;

	@Resource
	TBrProductService tBrProductService;

	@Resource
	TBrProductBrandService tBrProductBrandService;


	@Test
	public void test(int k) throws InterruptedException {
		List<TBrBrand> brands = tBrBrandService.queryByNameLength();
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setJoinBrandFlg(true);
		Page<TBrProduct> productPageList  = null;
		PageReq pageReq = new PageReq();
		pageReq.setPageSize(2000);
		pageReq.setPage(1);
		for (; k < brands.size(); k++) {
			long productCount = tBrProductService.queryCount(tBrProductQuery);
			log.info("当前还有未匹配的产品个数"+productCount);
			TBrBrand brand = brands.get(k);
			String brandName  = brand.getName();
			Long brandId = brand.getId();
			int totalPages = 1;
			long totalElements = 0l;
			long tmp =0l;
			if(StringUtils.isNoneBlank(brandName)){
				while (totalPages>0) {
					productPageList = tBrProductService.queryPageList(tBrProductQuery, pageReq);
					totalPages = productPageList.getTotalPages();
					totalElements = tBrProductService.queryCount(tBrProductQuery);
					if(tmp == totalElements){
						log.info("***【"+brandName+"】匹配完毕***");
						break;
					}else{
						log.info("***正在匹配【"+totalPages+"】页***");
						tmp = totalElements;
					}
					String productName = null;
					try {
						List<TBrProduct> content = productPageList.getContent();
						if(CollectionUtils.isNotEmpty(content)){
							for (TBrProduct tBrProduct : content) {
								productName = tBrProduct.getProductName();
								log.info("与品牌"+k+"["+brandName+"]匹配中...");
								if(productName.contains(brandName)){
									TBrProductBrand tBrProductBrand = new TBrProductBrand();
									tBrProductBrand.setBrandId(brandId);
									tBrProductBrand.setProductId(tBrProduct.getId());
									tBrProductBrandService.add(tBrProductBrand);
									log.info("匹配成功！ "+productName+"["+brandName+"]");
									tBrProductService.saveLog(Constant.YES, brandName, k, productName, null);
								}
							}
						}
					} catch (Exception e) {
						tBrProductService.saveLog(Constant.NO, brandName, k, productName, e);
						e.printStackTrace();
					}
				}
			}
		}
	}
}




