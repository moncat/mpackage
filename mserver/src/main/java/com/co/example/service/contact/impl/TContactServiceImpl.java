package com.co.example.service.contact.impl;

import com.co.example.dao.contact.TContactDao;
import com.co.example.entity.contact.TContact;
import com.co.example.service.contact.TContactService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TContactServiceImpl extends BaseServiceImpl<TContact, Long> implements TContactService {
    @Resource
    private TContactDao tContactDao;

    @Override
    protected BaseDao<TContact, Long> getBaseDao() {
        return tContactDao;
    }
}