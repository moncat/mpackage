package com.co.example.service.brand.impl;

import com.co.example.dao.brand.TBrProductBrandDao;
import com.co.example.entity.brand.TBrProductBrand;
import com.co.example.service.brand.TBrProductBrandService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrProductBrandServiceImpl extends BaseServiceImpl<TBrProductBrand, Long> implements TBrProductBrandService {
    @Resource
    private TBrProductBrandDao tBrProductBrandDao;

    @Override
    protected BaseDao<TBrProductBrand, Long> getBaseDao() {
        return tBrProductBrandDao;
    }
}