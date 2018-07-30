package com.co.example.service.user.impl;

import com.co.example.dao.user.TBrUserStatisticsDao;
import com.co.example.entity.user.TBrUserStatistics;
import com.co.example.service.user.TBrUserStatisticsService;
import com.co.example.utils.BaseDataUtil;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrUserStatisticsServiceImpl extends BaseServiceImpl<TBrUserStatistics, Long> implements TBrUserStatisticsService {
    @Resource
    private TBrUserStatisticsDao tBrUserStatisticsDao;

    @Override
    protected BaseDao<TBrUserStatistics, Long> getBaseDao() {
        return tBrUserStatisticsDao;
    }

	@Override
	public int addUserStatistics(Long userId, Integer type) {
		if(userId !=null){
			TBrUserStatistics tBrUserStatistics = new TBrUserStatistics();
			tBrUserStatistics.setUid(userId);
			tBrUserStatistics.setCreateBy(userId);
			tBrUserStatistics.setType(type);
			BaseDataUtil.setDefaultData(tBrUserStatistics);
			add(tBrUserStatistics);
			return 1;
		}
		return 0;
	}
}