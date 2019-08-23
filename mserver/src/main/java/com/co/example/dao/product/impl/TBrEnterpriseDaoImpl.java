package com.co.example.dao.product.impl;

import com.co.example.dao.product.TBrEnterpriseDao;
import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.aide.TBrEnterpriseCountVo;
import com.github.moncat.common.dao.BaseDaoImpl;
import com.github.moncat.common.exception.DaoException;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class TBrEnterpriseDaoImpl extends BaseDaoImpl<TBrEnterprise, Long> implements TBrEnterpriseDao {

	@Override
	public List<TBrEnterpriseCountVo> selectEnterpriseCount(String limitTime ,String endTime) {
		try {
			HashMap<String, Object> newHashMap = Maps.newHashMap();	 
			newHashMap.put("limitTime", limitTime);
			newHashMap.put("endTime", endTime);
			return this.sqlSession.selectList("selectEnterpriseCount", newHashMap);
		} catch (Exception e) {
			throw new DaoException(String.format("查询企业总数出错！语句：%s", getSqlName("selectEnterpriseCount")), e);
		}
	}
}