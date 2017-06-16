package com.co.example.dao.user.impl;

import com.co.example.common.dao.BaseDaoImpl;
import com.co.example.dao.user.TUserLoginDao;
import com.co.example.entity.user.TUserLogin;
import org.springframework.stereotype.Repository;

@Repository
public class TUserLoginDaoImpl extends BaseDaoImpl<TUserLogin, Integer> implements TUserLoginDao {
}