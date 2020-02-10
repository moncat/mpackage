package com.co.example.service.manifest.impl;

import com.co.example.dao.manifest.TBrManifestBrandDao;
import com.co.example.entity.manifest.TBrManifestBrand;
import com.co.example.service.manifest.TBrManifestBrandService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrManifestBrandServiceImpl extends BaseServiceImpl<TBrManifestBrand, Long> implements TBrManifestBrandService {
    @Resource
    private TBrManifestBrandDao tBrManifestBrandDao;

    @Override
    protected BaseDao<TBrManifestBrand, Long> getBaseDao() {
        return tBrManifestBrandDao;
    }
}