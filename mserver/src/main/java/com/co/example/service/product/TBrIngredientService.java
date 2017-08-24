package com.co.example.service.product;

import java.util.List;

import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.aide.TBrIngredientVo;
import com.github.moncat.common.service.BaseService;

public interface TBrIngredientService extends BaseService<TBrIngredient, Long> {
	
	/**
	 * 装饰成分信息，添加安全等级颜色，添加成分目的
	 */
	void decorateColour(List<TBrIngredient> ingredientList);
	
	void  getAims(TBrIngredientVo ingredientVo);
}