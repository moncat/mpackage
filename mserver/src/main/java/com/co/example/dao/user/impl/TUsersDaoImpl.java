package com.co.example.dao.user.impl;

import com.co.example.common.dao.BaseDaoImpl;
import com.co.example.dao.user.TUsersDao;
import com.co.example.entity.user.TUsers;
import org.springframework.stereotype.Repository;

@Repository
public class TUsersDaoImpl extends BaseDaoImpl<TUsers, Integer> implements TUsersDao {
}