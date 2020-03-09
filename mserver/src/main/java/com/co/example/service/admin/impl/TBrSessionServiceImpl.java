package com.co.example.service.admin.impl;

import com.co.example.dao.admin.TBrSessionDao;
import com.co.example.entity.admin.TBrSession;
import com.co.example.service.admin.TBrSessionService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrSessionServiceImpl extends BaseServiceImpl<TBrSession, Long> implements TBrSessionService {
    @Resource
    private TBrSessionDao tBrSessionDao;

    @Override
    protected BaseDao<TBrSession, Long> getBaseDao() {
        return tBrSessionDao;
    }
}