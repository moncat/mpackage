package com.co.example.service.message.impl;

import com.co.example.dao.message.TBrMessageDao;
import com.co.example.entity.message.TBrMessage;
import com.co.example.service.message.TBrMessageService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrMessageServiceImpl extends BaseServiceImpl<TBrMessage, Long> implements TBrMessageService {
    @Resource
    private TBrMessageDao tBrMessageDao;

    @Override
    protected BaseDao<TBrMessage, Long> getBaseDao() {
        return tBrMessageDao;
    }
}