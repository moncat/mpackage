package com.co.example.dao.user.impl;

import com.github.moncat.common.dao.BaseDaoImpl;
import com.co.example.dao.user.TUserRoleDao;
import com.co.example.entity.user.TUserRole;
import org.springframework.stereotype.Repository;

@Repository
public class TUserRoleDaoImpl extends BaseDaoImpl<TUserRole, Integer> implements TUserRoleDao {
}