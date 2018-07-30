package com.co.example.service.user;

import com.co.example.entity.user.TBrUserStatistics;
import com.github.moncat.common.service.BaseService;

public interface TBrUserStatisticsService extends BaseService<TBrUserStatistics, Long> {
	
	
	int addUserStatistics(Long userId,Integer type);
	
}