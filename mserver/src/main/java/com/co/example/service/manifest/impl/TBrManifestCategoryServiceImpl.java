package com.co.example.service.manifest.impl;

import com.co.example.dao.manifest.TBrManifestCategoryDao;
import com.co.example.entity.manifest.TBrManifestCategory;
import com.co.example.service.manifest.TBrManifestCategoryService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrManifestCategoryServiceImpl extends BaseServiceImpl<TBrManifestCategory, Long> implements TBrManifestCategoryService {
    @Resource
    private TBrManifestCategoryDao tBrManifestCategoryDao;

    @Override
    protected BaseDao<TBrManifestCategory, Long> getBaseDao() {
        return tBrManifestCategoryDao;
    }
}