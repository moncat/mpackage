package com.co.example.service.product.impl;

import com.co.example.dao.product.TBrIngredientAimDao;
import com.co.example.entity.product.TBrIngredientAim;
import com.co.example.service.product.TBrIngredientAimService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrIngredientAimServiceImpl extends BaseServiceImpl<TBrIngredientAim, Long> implements TBrIngredientAimService {
    @Resource
    private TBrIngredientAimDao tBrIngredientAimDao;

    @Override
    protected BaseDao<TBrIngredientAim, Long> getBaseDao() {
        return tBrIngredientAimDao;
    }
}