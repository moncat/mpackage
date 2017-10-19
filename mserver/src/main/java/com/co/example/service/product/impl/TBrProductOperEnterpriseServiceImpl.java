package com.co.example.service.product.impl;

import com.co.example.dao.product.TBrProductOperEnterpriseDao;
import com.co.example.entity.product.TBrProductOperEnterprise;
import com.co.example.service.product.TBrProductOperEnterpriseService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrProductOperEnterpriseServiceImpl extends BaseServiceImpl<TBrProductOperEnterprise, Long> implements TBrProductOperEnterpriseService {
    @Resource
    private TBrProductOperEnterpriseDao tBrProductOperEnterpriseDao;

    @Override
    protected BaseDao<TBrProductOperEnterprise, Long> getBaseDao() {
        return tBrProductOperEnterpriseDao;
    }
}