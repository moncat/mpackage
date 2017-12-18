package com.co.example.service.question;

import java.util.List;

import com.co.example.entity.question.TBrQuestionType;
import com.github.moncat.common.service.BaseService;

public interface TBrQuestionTypeService extends BaseService<TBrQuestionType, Long> {

	List<TBrQuestionType> getQuestionTypeTree();
	
	
}