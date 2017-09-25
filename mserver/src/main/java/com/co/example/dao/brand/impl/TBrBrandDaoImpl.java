package com.co.example.dao.brand.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.co.example.dao.brand.TBrBrandDao;
import com.co.example.entity.brand.TBrBrand;
import com.co.example.entity.brand.aide.TBrBrandQuery;
import com.github.moncat.common.dao.BaseDaoImpl;

@Repository
public class TBrBrandDaoImpl extends BaseDaoImpl<TBrBrand, Long> implements TBrBrandDao {
	
	String SELECT_BY_NAME_LENGTH="selectByNameLength";
	
	String SELECT_BY_NAME_EN_LENGTH="selectByNameEnLength";

	@Override
	public List<TBrBrand> selectByNameLength() {
		List<TBrBrand> selectList = selectList(new TBrBrandQuery() ,SELECT_BY_NAME_LENGTH);
		return selectList;
	}
	
	@Override
	public List<TBrBrand> selectByNameEnLength() {
		List<TBrBrand> selectList = selectList(new TBrBrandQuery() ,SELECT_BY_NAME_EN_LENGTH);
		return selectList;
	}
	
}