package com.co.example.service.question.impl;

import com.co.example.dao.question.TBrQuestionPlanDao;
import com.co.example.entity.question.TBrQuestionPlan;
import com.co.example.service.question.TBrQuestionPlanService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrQuestionPlanServiceImpl extends BaseServiceImpl<TBrQuestionPlan, Long> implements TBrQuestionPlanService {
    @Resource
    private TBrQuestionPlanDao tBrQuestionPlanDao;

    @Override
    protected BaseDao<TBrQuestionPlan, Long> getBaseDao() {
        return tBrQuestionPlanDao;
    }
}