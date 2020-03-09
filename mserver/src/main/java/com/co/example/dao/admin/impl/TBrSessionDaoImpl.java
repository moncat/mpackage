package com.co.example.dao.admin.impl;

import com.co.example.dao.admin.TBrSessionDao;
import com.co.example.entity.admin.TBrSession;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrSessionDaoImpl extends BaseDaoImpl<TBrSession, Long> implements TBrSessionDao {
}