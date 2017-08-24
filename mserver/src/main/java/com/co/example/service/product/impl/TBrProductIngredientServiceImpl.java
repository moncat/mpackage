package com.co.example.service.product.impl;

import com.co.example.dao.product.TBrProductIngredientDao;
import com.co.example.entity.product.TBrProductIngredient;
import com.co.example.service.product.TBrProductIngredientService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrProductIngredientServiceImpl extends BaseServiceImpl<TBrProductIngredient, Long> implements TBrProductIngredientService {
    @Resource
    private TBrProductIngredientDao tBrProductIngredientDao;

    @Override
    protected BaseDao<TBrProductIngredient, Long> getBaseDao() {
        return tBrProductIngredientDao;
    }
}