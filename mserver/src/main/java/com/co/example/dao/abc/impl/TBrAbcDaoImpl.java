package com.co.example.dao.abc.impl;

import com.co.example.dao.abc.TBrAbcDao;
import com.co.example.entity.abc.TBrAbc;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrAbcDaoImpl extends BaseDaoImpl<TBrAbc, Long> implements TBrAbcDao {
}