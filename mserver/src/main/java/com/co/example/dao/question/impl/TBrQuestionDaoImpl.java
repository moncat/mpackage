package com.co.example.dao.question.impl;

import com.co.example.dao.question.TBrQuestionDao;
import com.co.example.entity.question.TBrQuestion;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrQuestionDaoImpl extends BaseDaoImpl<TBrQuestion, Long> implements TBrQuestionDao {
}