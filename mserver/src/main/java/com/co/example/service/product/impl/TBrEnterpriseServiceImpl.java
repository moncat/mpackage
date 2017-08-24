package com.co.example.service.product.impl;

import com.co.example.dao.product.TBrEnterpriseDao;
import com.co.example.entity.product.TBrEnterprise;
import com.co.example.service.product.TBrEnterpriseService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrEnterpriseServiceImpl extends BaseServiceImpl<TBrEnterprise, Long> implements TBrEnterpriseService {
    @Resource
    private TBrEnterpriseDao tBrEnterpriseDao;

    @Override
    protected BaseDao<TBrEnterprise, Long> getBaseDao() {
        return tBrEnterpriseDao;
    }
}