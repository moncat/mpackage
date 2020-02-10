package com.co.example.service.manifest.impl;

import com.co.example.dao.manifest.TBrManifestProductDao;
import com.co.example.entity.manifest.TBrManifestProduct;
import com.co.example.service.manifest.TBrManifestProductService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrManifestProductServiceImpl extends BaseServiceImpl<TBrManifestProduct, Long> implements TBrManifestProductService {
    @Resource
    private TBrManifestProductDao tBrManifestProductDao;

    @Override
    protected BaseDao<TBrManifestProduct, Long> getBaseDao() {
        return tBrManifestProductDao;
    }
}