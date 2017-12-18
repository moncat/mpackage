package com.co.example.dao.user.impl;

import com.co.example.dao.user.TBrUserStatisticsDao;
import com.co.example.entity.user.TBrUserStatistics;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrUserStatisticsDaoImpl extends BaseDaoImpl<TBrUserStatistics, Long> implements TBrUserStatisticsDao {
}