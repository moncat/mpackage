package com.co.example.service.spec.impl;

import com.co.example.dao.spec.TBrSpecKeyDao;
import com.co.example.entity.spec.TBrSpecKey;
import com.co.example.service.spec.TBrSpecKeyService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrSpecKeyServiceImpl extends BaseServiceImpl<TBrSpecKey, Long> implements TBrSpecKeyService {
    @Resource
    private TBrSpecKeyDao tBrSpecKeyDao;

    @Override
    protected BaseDao<TBrSpecKey, Long> getBaseDao() {
        return tBrSpecKeyDao;
    }
}