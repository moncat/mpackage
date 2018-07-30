package com.co.example.dao.label;

import org.springframework.data.domain.Page;

import com.co.example.common.utils.PageReq;
import com.co.example.entity.label.TBrProductLabel;
import com.github.moncat.common.dao.BaseDao;

public interface TBrProductLabelDao extends BaseDao<TBrProductLabel, Long> {
	
	Page<TBrProductLabel> queryByLabel(Long labelId,Long userId,PageReq pageReq);
}