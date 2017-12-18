package com.co.example.service.user.impl;

import com.co.example.dao.user.TBrUserPlanDao;
import com.co.example.entity.user.TBrUserPlan;
import com.co.example.service.user.TBrUserPlanService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrUserPlanServiceImpl extends BaseServiceImpl<TBrUserPlan, Long> implements TBrUserPlanService {
    @Resource
    private TBrUserPlanDao tBrUserPlanDao;

    @Override
    protected BaseDao<TBrUserPlan, Long> getBaseDao() {
        return tBrUserPlanDao;
    }
}