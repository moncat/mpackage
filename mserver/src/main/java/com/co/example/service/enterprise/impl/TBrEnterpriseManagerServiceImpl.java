package com.co.example.service.enterprise.impl;

import com.co.example.dao.enterprise.TBrEnterpriseManagerDao;
import com.co.example.entity.enterprise.TBrEnterpriseManager;
import com.co.example.service.enterprise.TBrEnterpriseManagerService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrEnterpriseManagerServiceImpl extends BaseServiceImpl<TBrEnterpriseManager, Long> implements TBrEnterpriseManagerService {
    @Resource
    private TBrEnterpriseManagerDao tBrEnterpriseManagerDao;

    @Override
    protected BaseDao<TBrEnterpriseManager, Long> getBaseDao() {
        return tBrEnterpriseManagerDao;
    }
}