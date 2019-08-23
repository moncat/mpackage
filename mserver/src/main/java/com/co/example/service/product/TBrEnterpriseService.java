package com.co.example.service.product;

import java.util.List;

import org.jsoup.nodes.Document;

import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.aide.TBrEnterpriseCountVo;
import com.github.moncat.common.service.BaseService;

import us.codecraft.webmagic.proxy.Proxy;

public interface TBrEnterpriseService extends BaseService<TBrEnterprise, Long> {
	
	
	void addMoreEnterpriseInfo(Document doc,String enterpriseName,Long id,Proxy[] proxyArr);
	
	void addMoreEnterpriseInfo(String url,Long id);
	
	List<TBrEnterpriseCountVo> queryEnterpriseCount(String limitTime,String endTime);
	
}