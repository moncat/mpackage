package com.co.example.service.enterprise.impl;

import com.co.example.dao.enterprise.TBrEnterpriseBaseDao;
import com.co.example.entity.enterprise.TBrEnterpriseBase;
import com.co.example.service.enterprise.TBrEnterpriseBaseService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrEnterpriseBaseServiceImpl extends BaseServiceImpl<TBrEnterpriseBase, Long> implements TBrEnterpriseBaseService {
    @Resource
    private TBrEnterpriseBaseDao tBrEnterpriseBaseDao;

    @Override
    protected BaseDao<TBrEnterpriseBase, Long> getBaseDao() {
        return tBrEnterpriseBaseDao;
    }
}