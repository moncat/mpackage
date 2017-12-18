package com.co.example.service.message.impl;

import com.co.example.dao.message.TBrMessageTemplateDao;
import com.co.example.entity.message.TBrMessageTemplate;
import com.co.example.service.message.TBrMessageTemplateService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrMessageTemplateServiceImpl extends BaseServiceImpl<TBrMessageTemplate, Long> implements TBrMessageTemplateService {
    @Resource
    private TBrMessageTemplateDao tBrMessageTemplateDao;

    @Override
    protected BaseDao<TBrMessageTemplate, Long> getBaseDao() {
        return tBrMessageTemplateDao;
    }
}