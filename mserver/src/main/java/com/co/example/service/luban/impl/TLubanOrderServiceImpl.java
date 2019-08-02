package com.co.example.service.luban.impl;

import com.co.example.dao.luban.TLubanOrderDao;
import com.co.example.entity.luban.TLubanOrder;
import com.co.example.service.luban.TLubanOrderService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TLubanOrderServiceImpl extends BaseServiceImpl<TLubanOrder, Long> implements TLubanOrderService {
    @Resource
    private TLubanOrderDao tLubanOrderDao;

    @Override
    protected BaseDao<TLubanOrder, Long> getBaseDao() {
        return tLubanOrderDao;
    }
}