package com.co.example.dao.user.impl;

import com.co.example.dao.user.TUserDao;
import com.co.example.entity.user.TUser;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TUserDaoImpl extends BaseDaoImpl<TUser, Long> implements TUserDao {
}