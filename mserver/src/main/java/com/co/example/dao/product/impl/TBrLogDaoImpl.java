package com.co.example.dao.product.impl;

import com.co.example.dao.product.TBrLogDao;
import com.co.example.entity.product.TBrLog;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrLogDaoImpl extends BaseDaoImpl<TBrLog, Long> implements TBrLogDao {
}