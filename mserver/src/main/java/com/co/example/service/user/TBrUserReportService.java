package com.co.example.service.user;

import com.co.example.entity.user.TBrUserReport;
import com.co.example.entity.user.aide.TBrUserReportVo;
import com.github.moncat.common.service.BaseService;

public interface TBrUserReportService extends BaseService<TBrUserReport, Long> {

	Long addReport(TBrUserReportVo vo,Long userId);
}