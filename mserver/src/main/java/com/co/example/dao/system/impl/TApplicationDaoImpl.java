package com.co.example.dao.system.impl;

import com.co.example.dao.system.TApplicationDao;
import com.co.example.entity.system.TApplication;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TApplicationDaoImpl extends BaseDaoImpl<TApplication, Long> implements TApplicationDao {
}