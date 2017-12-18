package com.co.example.service.message.impl;

import com.co.example.dao.message.TBrMessageTypeDao;
import com.co.example.entity.message.TBrMessageType;
import com.co.example.service.message.TBrMessageTypeService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrMessageTypeServiceImpl extends BaseServiceImpl<TBrMessageType, Long> implements TBrMessageTypeService {
    @Resource
    private TBrMessageTypeDao tBrMessageTypeDao;

    @Override
    protected BaseDao<TBrMessageType, Long> getBaseDao() {
        return tBrMessageTypeDao;
    }
}