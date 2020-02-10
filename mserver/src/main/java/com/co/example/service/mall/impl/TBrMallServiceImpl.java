package com.co.example.service.mall.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.co.example.dao.mall.TBrMallDao;
import com.co.example.entity.mall.TBrMall;
import com.co.example.service.mall.TBrMallService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;

@Service
public class TBrMallServiceImpl extends BaseServiceImpl<TBrMall, Long> implements TBrMallService {
    @Resource
    private TBrMallDao tBrMallDao;

    @Override
    protected BaseDao<TBrMall, Long> getBaseDao() {
        return tBrMallDao;
    }

	
}








