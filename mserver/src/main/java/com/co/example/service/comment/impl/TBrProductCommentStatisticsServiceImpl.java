package com.co.example.service.comment.impl;

import com.co.example.dao.comment.TBrProductCommentStatisticsDao;
import com.co.example.entity.comment.TBrProductCommentStatistics;
import com.co.example.service.comment.TBrProductCommentStatisticsService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrProductCommentStatisticsServiceImpl extends BaseServiceImpl<TBrProductCommentStatistics, Long> implements TBrProductCommentStatisticsService {
    @Resource
    private TBrProductCommentStatisticsDao tBrProductCommentStatisticsDao;

    @Override
    protected BaseDao<TBrProductCommentStatistics, Long> getBaseDao() {
        return tBrProductCommentStatisticsDao;
    }
}