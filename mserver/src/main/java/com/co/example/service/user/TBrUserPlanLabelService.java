package com.co.example.service.user;

import java.util.Map;

import com.co.example.entity.user.TBrUserPlanLabel;
import com.github.moncat.common.service.BaseService;

public interface TBrUserPlanLabelService extends BaseService<TBrUserPlanLabel, Long> {
	
	
	/**
	 * 肤质匹配算法，根据标签进行匹配
	 * @param userId
	 * @param productId
	 * @return
	 */
	Map<String,Object> getMatching(Long userId,Long productId);
	
	
}