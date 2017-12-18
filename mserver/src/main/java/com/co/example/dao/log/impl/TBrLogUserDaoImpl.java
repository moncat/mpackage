package com.co.example.dao.log.impl;

import com.co.example.dao.log.TBrLogUserDao;
import com.co.example.entity.log.TBrLogUser;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrLogUserDaoImpl extends BaseDaoImpl<TBrLogUser, Long> implements TBrLogUserDao {
}