package com.co.example.service.manifest.impl;

import com.co.example.dao.manifest.TBrManifestIngredientDao;
import com.co.example.entity.manifest.TBrManifestIngredient;
import com.co.example.service.manifest.TBrManifestIngredientService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrManifestIngredientServiceImpl extends BaseServiceImpl<TBrManifestIngredient, Long> implements TBrManifestIngredientService {
    @Resource
    private TBrManifestIngredientDao tBrManifestIngredientDao;

    @Override
    protected BaseDao<TBrManifestIngredient, Long> getBaseDao() {
        return tBrManifestIngredientDao;
    }
}