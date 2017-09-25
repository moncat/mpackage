package com.co.example.dao.brand;

import java.util.List;

import com.co.example.entity.brand.TBrBrand;
import com.github.moncat.common.dao.BaseDao;

public interface TBrBrandDao extends BaseDao<TBrBrand, Long> {
	
	List<TBrBrand> selectByNameLength();
	
	List<TBrBrand> selectByNameEnLength();
	
}