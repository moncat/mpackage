package com.co.example.service.abc.impl;

import com.co.example.dao.abc.TBrAbcDao;
import com.co.example.entity.abc.TBrAbc;
import com.co.example.service.abc.TBrAbcService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrAbcServiceImpl extends BaseServiceImpl<TBrAbc, Long> implements TBrAbcService {
    @Resource
    private TBrAbcDao tBrAbcDao;

    @Override
    protected BaseDao<TBrAbc, Long> getBaseDao() {
        return tBrAbcDao;
    }
}