package com.co.example.dao.user.impl;

import com.co.example.dao.user.TUserRoleDao;
import com.co.example.entity.user.TUserRole;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TUserRoleDaoImpl extends BaseDaoImpl<TUserRole, Long> implements TUserRoleDao {
}