package com.co.example.dao.admin.impl;

import com.github.moncat.common.dao.BaseDaoImpl;
import com.co.example.dao.admin.TAdminLoginDao;
import com.co.example.entity.admin.TAdminLogin;
import org.springframework.stereotype.Repository;

@Repository
public class TAdminLoginDaoImpl extends BaseDaoImpl<TAdminLogin, Long> implements TAdminLoginDao {
}