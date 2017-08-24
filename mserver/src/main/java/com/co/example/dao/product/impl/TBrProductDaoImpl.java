package com.co.example.dao.product.impl;

import com.co.example.dao.product.TBrProductDao;
import com.co.example.entity.product.TBrProduct;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrProductDaoImpl extends BaseDaoImpl<TBrProduct, Long> implements TBrProductDao {
}