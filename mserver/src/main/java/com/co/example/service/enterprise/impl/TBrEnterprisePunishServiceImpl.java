package com.co.example.service.enterprise.impl;

import com.co.example.dao.enterprise.TBrEnterprisePunishDao;
import com.co.example.entity.enterprise.TBrEnterprisePunish;
import com.co.example.service.enterprise.TBrEnterprisePunishService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrEnterprisePunishServiceImpl extends BaseServiceImpl<TBrEnterprisePunish, Long> implements TBrEnterprisePunishService {
    @Resource
    private TBrEnterprisePunishDao tBrEnterprisePunishDao;

    @Override
    protected BaseDao<TBrEnterprisePunish, Long> getBaseDao() {
        return tBrEnterprisePunishDao;
    }
}