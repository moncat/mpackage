package com.co.example.service.product.impl;

import com.co.example.dao.product.TBrAimDao;
import com.co.example.entity.product.TBrAim;
import com.co.example.service.product.TBrAimService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrAimServiceImpl extends BaseServiceImpl<TBrAim, Long> implements TBrAimService {
    @Resource
    private TBrAimDao tBrAimDao;

    @Override
    protected BaseDao<TBrAim, Long> getBaseDao() {
        return tBrAimDao;
    }
}