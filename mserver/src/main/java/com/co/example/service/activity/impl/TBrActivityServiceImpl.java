package com.co.example.service.activity.impl;

import com.co.example.dao.activity.TBrActivityDao;
import com.co.example.entity.activity.TBrActivity;
import com.co.example.service.activity.TBrActivityService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrActivityServiceImpl extends BaseServiceImpl<TBrActivity, Long> implements TBrActivityService {
    @Resource
    private TBrActivityDao tBrActivityDao;

    @Override
    protected BaseDao<TBrActivity, Long> getBaseDao() {
        return tBrActivityDao;
    }
}