package com.co.example.dao.user.impl;

import com.co.example.dao.user.TBrUserApplyDao;
import com.co.example.entity.user.TBrUserApply;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrUserApplyDaoImpl extends BaseDaoImpl<TBrUserApply, Long> implements TBrUserApplyDao {
}