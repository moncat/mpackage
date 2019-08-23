package com.co.example.dao.product.impl;

import com.co.example.dao.product.TBrIngredientDao;
import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.aide.TBrIngredientCountVo;
import com.github.moncat.common.dao.BaseDaoImpl;
import com.github.moncat.common.exception.DaoException;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class TBrIngredientDaoImpl extends BaseDaoImpl<TBrIngredient, Long> implements TBrIngredientDao {

	@Override
	public List<TBrIngredientCountVo> selectIngredientCount(String limitTime,String endTime) {
		// TODO  limitTime
		try {
			HashMap<String, Object> newHashMap = Maps.newHashMap();	 
			newHashMap.put("limitTime", limitTime);
			newHashMap.put("endTime", endTime);
			return this.sqlSession.selectList("selectIngredientCount", newHashMap);
		} catch (Exception e) {
			throw new DaoException(String.format("查询成分出错！语句：%s", getSqlName("selectIngredientCount")), e);
		}
	}
}