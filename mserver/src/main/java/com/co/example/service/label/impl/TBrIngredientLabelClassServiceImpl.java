package com.co.example.service.label.impl;

import com.co.example.dao.label.TBrIngredientLabelClassDao;
import com.co.example.entity.label.TBrIngredientLabelClass;
import com.co.example.service.label.TBrIngredientLabelClassService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrIngredientLabelClassServiceImpl extends BaseServiceImpl<TBrIngredientLabelClass, Long> implements TBrIngredientLabelClassService {
    @Resource
    private TBrIngredientLabelClassDao tBrIngredientLabelClassDao;

    @Override
    protected BaseDao<TBrIngredientLabelClass, Long> getBaseDao() {
        return tBrIngredientLabelClassDao;
    }
}