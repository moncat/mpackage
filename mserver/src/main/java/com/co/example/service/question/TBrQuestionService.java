package com.co.example.service.question;

import com.co.example.entity.question.TBrQuestion;
import com.co.example.entity.question.aide.TBrQuestionQuery;
import com.github.moncat.common.service.BaseService;

public interface TBrQuestionService extends BaseService<TBrQuestion, Long> {

	int addData(TBrQuestionQuery t);

	void editData(TBrQuestionQuery t);
}