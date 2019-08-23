package com.co.example.dao.product;

import java.util.List;

import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.aide.TBrIngredientCountVo;
import com.github.moncat.common.dao.BaseDao;

public interface TBrIngredientDao extends BaseDao<TBrIngredient, Long> {
	
	List<TBrIngredientCountVo> selectIngredientCount(String limitTime,String endTime);
}