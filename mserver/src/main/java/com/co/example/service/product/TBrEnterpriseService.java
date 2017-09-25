package com.co.example.service.product;

import org.jsoup.nodes.Document;

import com.co.example.entity.product.TBrEnterprise;
import com.github.moncat.common.service.BaseService;

import us.codecraft.webmagic.proxy.Proxy;

public interface TBrEnterpriseService extends BaseService<TBrEnterprise, Long> {
	
	
	void addMoreEnterpriseInfo(Document doc,String enterpriseName,Long id,Proxy[] proxyArr);
	
	void addMoreEnterpriseInfo(String url,Long id);
	
}