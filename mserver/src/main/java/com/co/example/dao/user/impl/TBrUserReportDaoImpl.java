package com.co.example.dao.user.impl;

import com.co.example.dao.user.TBrUserReportDao;
import com.co.example.entity.user.TBrUserReport;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrUserReportDaoImpl extends BaseDaoImpl<TBrUserReport, Long> implements TBrUserReportDao {
}