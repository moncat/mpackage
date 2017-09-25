package com.co.example.service.enterprise.impl;

import com.co.example.dao.enterprise.TBrEnterpriseLawsuitDao;
import com.co.example.entity.enterprise.TBrEnterpriseLawsuit;
import com.co.example.service.enterprise.TBrEnterpriseLawsuitService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrEnterpriseLawsuitServiceImpl extends BaseServiceImpl<TBrEnterpriseLawsuit, Long> implements TBrEnterpriseLawsuitService {
    @Resource
    private TBrEnterpriseLawsuitDao tBrEnterpriseLawsuitDao;

    @Override
    protected BaseDao<TBrEnterpriseLawsuit, Long> getBaseDao() {
        return tBrEnterpriseLawsuitDao;
    }
}