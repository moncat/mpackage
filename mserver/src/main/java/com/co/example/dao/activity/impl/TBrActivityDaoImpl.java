package com.co.example.dao.activity.impl;

import com.co.example.dao.activity.TBrActivityDao;
import com.co.example.entity.activity.TBrActivity;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrActivityDaoImpl extends BaseDaoImpl<TBrActivity, Long> implements TBrActivityDao {
}