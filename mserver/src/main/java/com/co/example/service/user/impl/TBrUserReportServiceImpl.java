package com.co.example.service.user.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.co.example.dao.user.TBrUserReportDao;
import com.co.example.entity.user.TBrUserReport;
import com.co.example.entity.user.TBrUserReportItem;
import com.co.example.entity.user.aide.TBrUserReportVo;
import com.co.example.service.user.TBrUserReportItemService;
import com.co.example.service.user.TBrUserReportService;
import com.co.example.utils.BaseDataUtil;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;

@Service
public class TBrUserReportServiceImpl extends BaseServiceImpl<TBrUserReport, Long> implements TBrUserReportService {
    @Resource
    private TBrUserReportDao tBrUserReportDao;
    
    @Resource
    TBrUserReportItemService tBrUserReportItemService;

    @Override
    protected BaseDao<TBrUserReport, Long> getBaseDao() {
        return tBrUserReportDao;
    }

	@Override
	public Long addReport(TBrUserReportVo vo,Long userId) {
		vo.setCreateBy(userId);
		BaseDataUtil.setDefaultData(vo);
		add(vo);
		Long id = vo.getId();
		List<TBrUserReportItem> list = vo.getList();
		for (TBrUserReportItem tBrUserReportItem : list) {
			tBrUserReportItem.setReportId(id);
			tBrUserReportItem.setCreateBy(userId);
			BaseDataUtil.setDefaultData(tBrUserReportItem);
		}
		tBrUserReportItemService.addInBatch(list);
		return id;
	}
}