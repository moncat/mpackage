package com.co.example.service.label.impl;

import com.co.example.dao.label.TBrIngredientLabelDao;
import com.co.example.entity.label.TBrIngredientLabel;
import com.co.example.service.label.TBrIngredientLabelService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrIngredientLabelServiceImpl extends BaseServiceImpl<TBrIngredientLabel, Long> implements TBrIngredientLabelService {
    @Resource
    private TBrIngredientLabelDao tBrIngredientLabelDao;

    @Override
    protected BaseDao<TBrIngredientLabel, Long> getBaseDao() {
        return tBrIngredientLabelDao;
    }
}