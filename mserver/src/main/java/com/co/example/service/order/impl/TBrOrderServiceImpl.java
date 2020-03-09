package com.co.example.service.order.impl;

import com.co.example.dao.order.TBrOrderDao;
import com.co.example.entity.order.TBrOrder;
import com.co.example.service.order.TBrOrderService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrOrderServiceImpl extends BaseServiceImpl<TBrOrder, Long> implements TBrOrderService {
    @Resource
    private TBrOrderDao tBrOrderDao;

    @Override
    protected BaseDao<TBrOrder, Long> getBaseDao() {
        return tBrOrderDao;
    }
}