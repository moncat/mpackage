package com.co.example.dao.category.impl;

import com.co.example.dao.category.TBrCategoryDao;
import com.co.example.entity.category.TBrCategory;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrCategoryDaoImpl extends BaseDaoImpl<TBrCategory, Long> implements TBrCategoryDao {
}