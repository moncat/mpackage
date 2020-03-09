package com.co.example.service.brand.impl;

import com.co.example.dao.brand.TBrTrademarkDao;
import com.co.example.entity.brand.TBrTrademark;
import com.co.example.service.brand.TBrTrademarkService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrTrademarkServiceImpl extends BaseServiceImpl<TBrTrademark, Long> implements TBrTrademarkService {
    @Resource
    private TBrTrademarkDao tBrTrademarkDao;

    @Override
    protected BaseDao<TBrTrademark, Long> getBaseDao() {
        return tBrTrademarkDao;
    }
}