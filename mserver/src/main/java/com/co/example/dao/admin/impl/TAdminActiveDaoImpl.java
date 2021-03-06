package com.co.example.dao.admin.impl;

import com.co.example.dao.admin.TAdminActiveDao;
import com.co.example.entity.admin.TAdminActive;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TAdminActiveDaoImpl extends BaseDaoImpl<TAdminActive, Long> implements TAdminActiveDao {
}