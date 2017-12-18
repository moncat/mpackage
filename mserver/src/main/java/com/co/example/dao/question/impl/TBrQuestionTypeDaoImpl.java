package com.co.example.dao.question.impl;

import com.co.example.dao.question.TBrQuestionTypeDao;
import com.co.example.entity.question.TBrQuestionType;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrQuestionTypeDaoImpl extends BaseDaoImpl<TBrQuestionType, Long> implements TBrQuestionTypeDao {
}