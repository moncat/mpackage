package com.co.example.dao.spec.impl;

import com.co.example.dao.spec.TBrSpecValueDao;
import com.co.example.entity.spec.TBrSpecValue;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrSpecValueDaoImpl extends BaseDaoImpl<TBrSpecValue, Long> implements TBrSpecValueDao {
}