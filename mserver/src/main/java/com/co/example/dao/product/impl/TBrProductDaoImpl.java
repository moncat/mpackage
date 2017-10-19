package com.co.example.dao.product.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.co.example.dao.product.TBrProductDao;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.github.moncat.common.dao.BaseDaoImpl;
import com.github.moncat.common.dao.BaseSqlId;
import com.github.moncat.common.exception.DaoException;

@Repository
public class TBrProductDaoImpl extends BaseDaoImpl<TBrProduct, Long> implements TBrProductDao {

	
	String  SELECT_OPER_ENTERPRISE ="selectOperEnterprise";
	
	String  UPDATE_BY_AREA ="updateByArea";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> selectOperEnterpriseFromProduct() {
		List<String> list = (List<String>)selectList(SELECT_OPER_ENTERPRISE,null);
		return list;
	}
	

	@Override
	public int updateByArea(TBrProductQuery query) {
		notNull(query);
		try {
			return this.sqlSession.update(UPDATE_BY_AREA,query);
		} catch (Exception e) {
			throw new DaoException(String.format("根据ID更新对象出错！语句：%s", getSqlName(UPDATE_BY_AREA)), e);
		}
	}
	
	
}