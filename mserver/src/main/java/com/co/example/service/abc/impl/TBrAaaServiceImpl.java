package com.co.example.service.abc.impl;

import com.co.example.dao.abc.TBrAaaDao;
import com.co.example.entity.abc.TBrAaa;
import com.co.example.service.abc.TBrAaaService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrAaaServiceImpl extends BaseServiceImpl<TBrAaa, Long> implements TBrAaaService {
    @Resource
    private TBrAaaDao tBrAaaDao;

    @Override
    protected BaseDao<TBrAaa, Long> getBaseDao() {
        return tBrAaaDao;
    }
}