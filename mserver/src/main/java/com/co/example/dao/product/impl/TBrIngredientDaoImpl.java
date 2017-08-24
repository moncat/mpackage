package com.co.example.dao.product.impl;

import com.co.example.dao.product.TBrIngredientDao;
import com.co.example.entity.product.TBrIngredient;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrIngredientDaoImpl extends BaseDaoImpl<TBrIngredient, Long> implements TBrIngredientDao {
}