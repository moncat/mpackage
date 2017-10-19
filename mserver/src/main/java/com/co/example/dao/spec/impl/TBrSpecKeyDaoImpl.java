package com.co.example.dao.spec.impl;

import com.co.example.dao.spec.TBrSpecKeyDao;
import com.co.example.entity.spec.TBrSpecKey;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrSpecKeyDaoImpl extends BaseDaoImpl<TBrSpecKey, Long> implements TBrSpecKeyDao {
}