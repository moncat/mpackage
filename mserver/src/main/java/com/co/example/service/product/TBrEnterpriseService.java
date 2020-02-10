package com.co.example.service.product;

import java.util.List;

import org.openqa.selenium.WebDriver;

import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.aide.TBrEnterpriseCountVo;
import com.co.example.entity.product.aide.TBrEnterpriseVo;
import com.github.moncat.common.service.BaseService;

public interface TBrEnterpriseService extends BaseService<TBrEnterprise, Long> {
	
	
	void addMoreEnterpriseInfo(String urlSearch,TBrEnterprise tBrEnterprise,WebDriver chrome) throws Exception; 
	
	void addMoreEnterpriseInfo(String url,Long id);
	
	List<TBrEnterpriseCountVo> queryEnterpriseCount(String limitTime,String endTime);
	
	List<TBrEnterpriseVo> queryEnterpriseListByProductId(Long id);
	
}