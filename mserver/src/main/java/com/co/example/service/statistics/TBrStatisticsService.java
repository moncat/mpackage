package com.co.example.service.statistics;

import com.co.example.entity.statistics.TBrStatistics;
import com.github.moncat.common.service.BaseService;

public interface TBrStatisticsService extends BaseService<TBrStatistics, Long> {
	
	/**
	 * 产品信息统计
	 */
	void addData();
	/**
	 * 
	 */
	void addUserData();
}