package com.co.example.dao.user.impl;

import com.github.moncat.common.dao.BaseDaoImpl;
import com.co.example.dao.user.TOrgDao;
import com.co.example.entity.user.TOrg;
import org.springframework.stereotype.Repository;

@Repository
public class TOrgDaoImpl extends BaseDaoImpl<TOrg, Integer> implements TOrgDao {
}