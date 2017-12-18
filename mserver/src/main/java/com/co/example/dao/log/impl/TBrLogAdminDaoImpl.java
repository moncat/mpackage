package com.co.example.dao.log.impl;

import com.co.example.dao.log.TBrLogAdminDao;
import com.co.example.entity.log.TBrLogAdmin;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrLogAdminDaoImpl extends BaseDaoImpl<TBrLogAdmin, Long> implements TBrLogAdminDao {
}