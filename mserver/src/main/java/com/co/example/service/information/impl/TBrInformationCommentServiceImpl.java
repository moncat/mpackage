package com.co.example.service.information.impl;

import com.co.example.dao.information.TBrInformationCommentDao;
import com.co.example.entity.information.TBrInformationComment;
import com.co.example.service.information.TBrInformationCommentService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrInformationCommentServiceImpl extends BaseServiceImpl<TBrInformationComment, Long> implements TBrInformationCommentService {
    @Resource
    private TBrInformationCommentDao tBrInformationCommentDao;

    @Override
    protected BaseDao<TBrInformationComment, Long> getBaseDao() {
        return tBrInformationCommentDao;
    }
}