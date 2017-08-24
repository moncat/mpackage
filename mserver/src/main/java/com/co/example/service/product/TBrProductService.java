package com.co.example.service.product;

import com.co.example.entity.product.TBrProduct;
import com.github.moncat.common.service.BaseService;

public interface TBrProductService extends BaseService<TBrProduct, Long> {
	
	int addProductFromCFDA(String page,String dateStr);
	
	void addProductFromBEVOL(int page , int category);

	/**
	 * 保存日志
	 * @param source 来源
	 * @param url 访问url
	 * @param count 返回数量
	 * @param params
	 * @param e
	 */
	void saveLog(Byte source, String url, Integer count, String params, Exception e);
	
	
	void doSomeThing();
	
	
}