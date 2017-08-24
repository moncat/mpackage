package com.co.example.dao.system.impl;

import com.co.example.dao.system.TRoleDao;
import com.co.example.entity.system.TRole;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TRoleDaoImpl extends BaseDaoImpl<TRole, Long> implements TRoleDao {
}