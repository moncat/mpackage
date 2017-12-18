package com.co.example.service.expressage;

import com.co.example.entity.expressage.TBrSfRegion;
import com.github.moncat.common.service.BaseService;

public interface TBrSfRegionService extends BaseService<TBrSfRegion, Long> {
	
	void addSFExpressData();
}