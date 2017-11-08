package com.co.example.dao.statistics.impl;

import com.co.example.dao.statistics.TBrStatisticsDao;
import com.co.example.entity.statistics.TBrStatistics;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrStatisticsDaoImpl extends BaseDaoImpl<TBrStatistics, Long> implements TBrStatisticsDao {
}