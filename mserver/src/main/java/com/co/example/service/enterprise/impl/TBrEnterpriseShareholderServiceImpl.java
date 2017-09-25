package com.co.example.service.enterprise.impl;

import com.co.example.dao.enterprise.TBrEnterpriseShareholderDao;
import com.co.example.entity.enterprise.TBrEnterpriseShareholder;
import com.co.example.service.enterprise.TBrEnterpriseShareholderService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrEnterpriseShareholderServiceImpl extends BaseServiceImpl<TBrEnterpriseShareholder, Long> implements TBrEnterpriseShareholderService {
    @Resource
    private TBrEnterpriseShareholderDao tBrEnterpriseShareholderDao;

    @Override
    protected BaseDao<TBrEnterpriseShareholder, Long> getBaseDao() {
        return tBrEnterpriseShareholderDao;
    }
}