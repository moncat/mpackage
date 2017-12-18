package com.co.example.service.question.impl;

import com.co.example.dao.question.TBrQuestionPlanSpecDao;
import com.co.example.entity.question.TBrQuestionPlanSpec;
import com.co.example.service.question.TBrQuestionPlanSpecService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrQuestionPlanSpecServiceImpl extends BaseServiceImpl<TBrQuestionPlanSpec, Long> implements TBrQuestionPlanSpecService {
    @Resource
    private TBrQuestionPlanSpecDao tBrQuestionPlanSpecDao;

    @Override
    protected BaseDao<TBrQuestionPlanSpec, Long> getBaseDao() {
        return tBrQuestionPlanSpecDao;
    }
}