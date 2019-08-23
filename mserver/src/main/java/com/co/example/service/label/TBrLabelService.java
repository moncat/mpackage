package com.co.example.service.label;

import java.util.List;

import com.co.example.entity.label.TBrLabel;
import com.github.moncat.common.service.BaseService;

public interface TBrLabelService extends BaseService<TBrLabel, Long> {
	
	//添加商品标签
	int addConnect2Product(TBrLabel label);
	
	String queryLabelsByProductId(Long id);
	
	List<TBrLabel> queryLabelListByProductId(Long id);
}