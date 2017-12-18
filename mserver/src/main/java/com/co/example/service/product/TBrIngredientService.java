package com.co.example.service.product;

import java.util.List;
import java.util.Map;

import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.aide.TBrIngredientVo;
import com.github.moncat.common.service.BaseService;

public interface TBrIngredientService extends BaseService<TBrIngredient, Long> {
	
	/**
	 * 装饰成分信息，添加安全等级颜色，添加成分目的
	 */
	void decorateColour(List<TBrIngredient> ingredientList);
	
	void  getAims(TBrIngredientVo ingredientVo);

	/**
	 *  安全数量（10种） 比较安全（3种） 危险（1种）
	 * @param productId
	 * @return
	 */
	Map<String, Integer> ingredientAnalyze(Long productId);

	/**
	 * 安全分析  香精（1种） 防腐剂（2种）
	 * @param productId
	 * @return
	 */
	Map<String, Integer> safeAnalyze(Long productId);
	/**
	 * 功效分析 美白（1种） 保湿（2种）
	 * @param productId
	 * @return
	 */
	Map<String, Integer> effectAnalyze(Long productId);
	
	
}