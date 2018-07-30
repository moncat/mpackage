package com.co.example.service.system.impl;

import com.co.example.dao.system.TSystemConfigDao;
import com.co.example.entity.system.TSystemConfig;
import com.co.example.service.system.TSystemConfigService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TSystemConfigServiceImpl extends BaseServiceImpl<TSystemConfig, Long> implements TSystemConfigService {
    @Resource
    private TSystemConfigDao tSystemConfigDao;

    @Override
    protected BaseDao<TSystemConfig, Long> getBaseDao() {
        return tSystemConfigDao;
    }
}