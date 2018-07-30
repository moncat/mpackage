package com.co.example.dao.user.impl;

import com.co.example.dao.user.TBrUserPlanItemDao;
import com.co.example.entity.user.TBrUserPlanItem;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrUserPlanItemDaoImpl extends BaseDaoImpl<TBrUserPlanItem, Long> implements TBrUserPlanItemDao {
}