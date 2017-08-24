package com.co.example.service.product.impl;

import com.co.example.dao.product.TBrLogDao;
import com.co.example.entity.product.TBrLog;
import com.co.example.service.product.TBrLogService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrLogServiceImpl extends BaseServiceImpl<TBrLog, Long> implements TBrLogService {
    @Resource
    private TBrLogDao tBrLogDao;

    @Override
    protected BaseDao<TBrLog, Long> getBaseDao() {
        return tBrLogDao;
    }
}