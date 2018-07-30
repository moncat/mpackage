package com.co.example.dao.system.impl;

import com.co.example.dao.system.TSystemConfigDao;
import com.co.example.entity.system.TSystemConfig;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TSystemConfigDaoImpl extends BaseDaoImpl<TSystemConfig, Long> implements TSystemConfigDao {
}