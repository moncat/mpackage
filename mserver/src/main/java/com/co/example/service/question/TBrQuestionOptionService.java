package com.co.example.service.question;

import com.co.example.entity.question.TBrQuestionOption;
import com.co.example.entity.question.aide.TBrQuestionQuery;
import com.github.moncat.common.service.BaseService;

public interface TBrQuestionOptionService extends BaseService<TBrQuestionOption, Long> {
	
	int addQuestionOption(TBrQuestionQuery question);
}