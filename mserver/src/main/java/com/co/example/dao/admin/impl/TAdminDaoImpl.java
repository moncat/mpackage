package com.co.example.dao.admin.impl;

import com.co.example.common.dao.BaseDaoImpl;
import com.co.example.dao.admin.TAdminDao;
import com.co.example.entity.admin.TAdmin;
import org.springframework.stereotype.Repository;

@Repository
public class TAdminDaoImpl extends BaseDaoImpl<TAdmin, Long> implements TAdminDao {
}