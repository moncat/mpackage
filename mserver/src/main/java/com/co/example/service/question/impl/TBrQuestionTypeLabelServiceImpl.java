package com.co.example.service.question.impl;

import com.co.example.dao.question.TBrQuestionTypeLabelDao;
import com.co.example.entity.question.TBrQuestionTypeLabel;
import com.co.example.service.question.TBrQuestionTypeLabelService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrQuestionTypeLabelServiceImpl extends BaseServiceImpl<TBrQuestionTypeLabel, Long> implements TBrQuestionTypeLabelService {
    @Resource
    private TBrQuestionTypeLabelDao tBrQuestionTypeLabelDao;

    @Override
    protected BaseDao<TBrQuestionTypeLabel, Long> getBaseDao() {
        return tBrQuestionTypeLabelDao;
    }
}