package com.co.example.dao.admin.impl;

import com.co.example.common.dao.BaseDaoImpl;
import com.co.example.dao.admin.TAdminRoleDao;
import com.co.example.entity.admin.TAdminRole;
import org.springframework.stereotype.Repository;

@Repository
public class TAdminRoleDaoImpl extends BaseDaoImpl<TAdminRole, Long> implements TAdminRoleDao {
}