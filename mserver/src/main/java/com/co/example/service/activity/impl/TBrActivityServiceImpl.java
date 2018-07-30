package com.co.example.service.activity.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.co.example.dao.activity.TBrActivityDao;
import com.co.example.entity.activity.TBrActivity;
import com.co.example.entity.activity.aide.TBrActivityQuery;
import com.co.example.service.activity.TBrActivityService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;

@Service
public class TBrActivityServiceImpl extends BaseServiceImpl<TBrActivity, Long> implements TBrActivityService {
    @Resource
    private TBrActivityDao tBrActivityDao;

    @Override
    protected BaseDao<TBrActivity, Long> getBaseDao() {
        return tBrActivityDao;
    }

	@Override
	public void updateApplyUserNum(Long id) {
		TBrActivityQuery tBrActivityQuery = new TBrActivityQuery();
		tBrActivityQuery.setId(id);
		tBrActivityQuery.setForUpdate(true);
		TBrActivity one = queryOne(tBrActivityQuery);
		Integer userNum = one.getUserNum();
		if(userNum == null){
			userNum = 0;
		}
		userNum++;
		TBrActivity tBrActivity = new TBrActivity();
		tBrActivity.setId(id);
		tBrActivity.setUserNum(userNum);
		updateByIdSelective(tBrActivity);
	}
}



