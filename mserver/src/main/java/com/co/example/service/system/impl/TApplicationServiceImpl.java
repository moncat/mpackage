package com.co.example.service.system.impl;

import com.co.example.dao.system.TApplicationDao;
import com.co.example.entity.system.TApplication;
import com.co.example.service.system.TApplicationService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TApplicationServiceImpl extends BaseServiceImpl<TApplication, Long> implements TApplicationService {
    @Resource
    private TApplicationDao tApplicationDao;

    @Override
    protected BaseDao<TApplication, Long> getBaseDao() {
        return tApplicationDao;
    }
}