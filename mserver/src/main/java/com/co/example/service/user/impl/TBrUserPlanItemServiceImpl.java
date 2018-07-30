package com.co.example.service.user.impl;

import com.co.example.dao.user.TBrUserPlanItemDao;
import com.co.example.entity.user.TBrUserPlanItem;
import com.co.example.service.user.TBrUserPlanItemService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrUserPlanItemServiceImpl extends BaseServiceImpl<TBrUserPlanItem, Long> implements TBrUserPlanItemService {
    @Resource
    private TBrUserPlanItemDao tBrUserPlanItemDao;

    @Override
    protected BaseDao<TBrUserPlanItem, Long> getBaseDao() {
        return tBrUserPlanItemDao;
    }
}