package com.co.example.dao.category.impl;

import com.co.example.dao.category.TBrProductCategoryDao;
import com.co.example.entity.category.TBrProductCategory;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrProductCategoryDaoImpl extends BaseDaoImpl<TBrProductCategory, Long> implements TBrProductCategoryDao {
}