package com.co.example.service.question;

import java.util.List;

import com.co.example.entity.question.TBrQuestion;
import com.co.example.entity.question.aide.TBrQuestionQuery;
import com.co.example.entity.question.aide.TBrQuestionVo;
import com.github.moncat.common.service.BaseService;

public interface TBrQuestionService extends BaseService<TBrQuestion, Long> {

	int addData(TBrQuestionQuery t);

	void editData(TBrQuestionQuery t);
	/**
	 * 获得一类测试的全部问题及选项（e.g. 敏感皮肤测试）
	 * @param type
	 * @return
	 */
	List<TBrQuestionVo> getTopicByType(Byte type);
}