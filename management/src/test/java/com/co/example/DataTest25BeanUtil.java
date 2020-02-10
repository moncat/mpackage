package com.co.example;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.common.constant.Constant;
import com.co.example.entity.admin.TAdmin;
import com.co.example.entity.admin.aide.TAdminQuery;
import com.co.example.entity.brand.TBrBrand;
import com.co.example.entity.manifest.TBrManifest;
import com.co.example.entity.manifest.TBrManifestProduct;
import com.co.example.entity.manifest.TBrManifestResult;
import com.co.example.entity.manifest.aide.TBrManifestAuthQuery;
import com.co.example.entity.manifest.aide.TBrManifestAuthVo;
import com.co.example.entity.manifest.aide.TBrManifestResultQuery;
import com.co.example.entity.product.TBrProduct;
import com.co.example.service.admin.TAdminService;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.manifest.TBrManifestAuthService;
import com.co.example.service.manifest.TBrManifestResultService;
import com.co.example.service.manifest.TBrManifestService;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest25BeanUtil {
	

	@Test
	public void testBean(){
		TBrProduct tBrProduct = new TBrProduct();
		tBrProduct.setId(1234l);
		tBrProduct.setProductName("pppName");
		tBrProduct.setEnterpriseNameEn("english");
		tBrProduct.setTaobaoSale(1000);
		TBrManifestProduct tBrManifestProduct = new TBrManifestProduct();
		BeanUtils.copyProperties( tBrProduct, tBrManifestProduct);
		System.out.println(tBrManifestProduct);
	}
		

	
	
	

}
