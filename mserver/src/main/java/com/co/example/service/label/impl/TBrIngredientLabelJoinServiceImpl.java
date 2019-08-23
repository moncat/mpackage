package com.co.example.service.label.impl;

import com.co.example.dao.label.TBrIngredientLabelJoinDao;
import com.co.example.entity.label.TBrIngredientLabelJoin;
import com.co.example.service.label.TBrIngredientLabelJoinService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrIngredientLabelJoinServiceImpl extends BaseServiceImpl<TBrIngredientLabelJoin, Long> implements TBrIngredientLabelJoinService {
    @Resource
    private TBrIngredientLabelJoinDao tBrIngredientLabelJoinDao;

    @Override
    protected BaseDao<TBrIngredientLabelJoin, Long> getBaseDao() {
        return tBrIngredientLabelJoinDao;
    }
}