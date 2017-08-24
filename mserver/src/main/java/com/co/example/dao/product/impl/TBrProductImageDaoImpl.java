package com.co.example.dao.product.impl;

import com.co.example.dao.product.TBrProductImageDao;
import com.co.example.entity.product.TBrProductImage;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrProductImageDaoImpl extends BaseDaoImpl<TBrProductImage, Long> implements TBrProductImageDao {
}