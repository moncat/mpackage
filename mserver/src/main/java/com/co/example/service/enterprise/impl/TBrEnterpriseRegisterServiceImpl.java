package com.co.example.service.enterprise.impl;

import com.co.example.dao.enterprise.TBrEnterpriseRegisterDao;
import com.co.example.entity.enterprise.TBrEnterpriseRegister;
import com.co.example.service.enterprise.TBrEnterpriseRegisterService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrEnterpriseRegisterServiceImpl extends BaseServiceImpl<TBrEnterpriseRegister, Long> implements TBrEnterpriseRegisterService {
    @Resource
    private TBrEnterpriseRegisterDao tBrEnterpriseRegisterDao;

    @Override
    protected BaseDao<TBrEnterpriseRegister, Long> getBaseDao() {
        return tBrEnterpriseRegisterDao;
    }
}