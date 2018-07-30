package com.co.example.dao.label.impl;

import com.co.example.common.utils.PageReq;
import com.co.example.dao.label.TBrProductLabelDao;
import com.co.example.entity.label.TBrProductLabel;
import com.co.example.entity.label.aide.TBrProductLabelQuery;
import com.github.moncat.common.dao.BaseDaoImpl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public class TBrProductLabelDaoImpl extends BaseDaoImpl<TBrProductLabel, Long> implements TBrProductLabelDao {

	
	String selectCount4label="selectCount4label";
	
	String select4label="select4label";
	
	@Override
	public Page<TBrProductLabel> queryByLabel(Long labelId, Long userId, PageReq pageReq) {
		TBrProductLabelQuery query = new TBrProductLabelQuery();
		query.setLabelId(labelId);
		query.setUserId(userId);
		Page<TBrProductLabel> list = selectPageList(query, pageReq, select4label, selectCount4label);
		return list;
	}
}