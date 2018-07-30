package com.co.example.service.user;

import java.util.List;

import com.co.example.entity.question.aide.TBrQuestionTypeVo;
import com.co.example.entity.user.TBrUserPlan;
import com.co.example.entity.user.TUser;
import com.github.moncat.common.service.BaseService;

public interface TBrUserPlanService extends BaseService<TBrUserPlan, Long> {
	
	
	void saveUserPlan(String ip ,TUser user,List<TBrQuestionTypeVo> beanList);
	
	TBrUserPlan getUserPlan(Long userId) ;
	
	TBrUserPlan getLastUserPlan(Long userId) ;
}