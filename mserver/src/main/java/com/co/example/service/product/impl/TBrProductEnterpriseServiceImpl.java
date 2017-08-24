package com.co.example.service.product.impl;

import com.co.example.dao.product.TBrProductEnterpriseDao;
import com.co.example.entity.product.TBrProductEnterprise;
import com.co.example.service.product.TBrProductEnterpriseService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrProductEnterpriseServiceImpl extends BaseServiceImpl<TBrProductEnterprise, Long> implements TBrProductEnterpriseService {
    @Resource
    private TBrProductEnterpriseDao tBrProductEnterpriseDao;

    @Override
    protected BaseDao<TBrProductEnterprise, Long> getBaseDao() {
        return tBrProductEnterpriseDao;
    }
}