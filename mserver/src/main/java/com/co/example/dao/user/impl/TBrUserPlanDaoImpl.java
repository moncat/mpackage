package com.co.example.dao.user.impl;

import com.co.example.dao.user.TBrUserPlanDao;
import com.co.example.entity.user.TBrUserPlan;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrUserPlanDaoImpl extends BaseDaoImpl<TBrUserPlan, Long> implements TBrUserPlanDao {
}