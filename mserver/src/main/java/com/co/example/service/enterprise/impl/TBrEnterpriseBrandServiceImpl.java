package com.co.example.service.enterprise.impl;

import com.co.example.dao.enterprise.TBrEnterpriseBrandDao;
import com.co.example.entity.enterprise.TBrEnterpriseBrand;
import com.co.example.service.enterprise.TBrEnterpriseBrandService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrEnterpriseBrandServiceImpl extends BaseServiceImpl<TBrEnterpriseBrand, Long> implements TBrEnterpriseBrandService {
    @Resource
    private TBrEnterpriseBrandDao tBrEnterpriseBrandDao;

    @Override
    protected BaseDao<TBrEnterpriseBrand, Long> getBaseDao() {
        return tBrEnterpriseBrandDao;
    }
}