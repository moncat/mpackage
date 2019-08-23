package com.co.example.dao.product;

import java.util.List;

import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.aide.TBrEnterpriseCountVo;
import com.github.moncat.common.dao.BaseDao;

public interface TBrEnterpriseDao extends BaseDao<TBrEnterprise, Long> {
	
	List<TBrEnterpriseCountVo> selectEnterpriseCount(String limitTime,String endTime);
	
}