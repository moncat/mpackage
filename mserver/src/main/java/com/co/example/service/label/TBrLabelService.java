package com.co.example.service.label;

import com.co.example.entity.label.TBrLabel;
import com.github.moncat.common.service.BaseService;

public interface TBrLabelService extends BaseService<TBrLabel, Long> {
	
	int addConnect2Product(TBrLabel label);
}