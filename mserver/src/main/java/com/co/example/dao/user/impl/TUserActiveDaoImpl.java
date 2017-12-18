package com.co.example.dao.user.impl;

import com.co.example.dao.user.TUserActiveDao;
import com.co.example.entity.user.TUserActive;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TUserActiveDaoImpl extends BaseDaoImpl<TUserActive, Long> implements TUserActiveDao {
}