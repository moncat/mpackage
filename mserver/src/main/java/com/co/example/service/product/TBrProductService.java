package com.co.example.service.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrProductQuery;
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
	
	/**
	 * 对成分进行统计分析
	 * @param tBrProduct
	 * @param ingredientList
	 * @return
	 */
	TBrProduct getStatisticsInfo(TBrProduct tBrProduct,List<TBrIngredient> ingredientList);
	
	
	/**
	 * 获取仅id 产品名称 的简易结果
	 * @param query
	 * @param pageable
	 * @return
	 */
	Page<TBrProduct> querySimplePageList(TBrProduct query, Pageable pageable);
	
	/**
	 * 仅运营企业的名称（从产品表获得，并去重）
	 * @return
	 */
	List<String> queryOperEnterpriseFromProduct();
	
	int updateByArea(TBrProductQuery query);
	
	
	
}