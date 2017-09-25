package com.co.example.service.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.co.example.entity.product.TBrIngredient;
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
	
	TBrProduct getStatisticsInfo(TBrProduct tBrProduct,List<TBrIngredient> ingredientList);
	
	
	Page<TBrProduct> querySimplePageList(TBrProduct query, Pageable pageable);
	
}