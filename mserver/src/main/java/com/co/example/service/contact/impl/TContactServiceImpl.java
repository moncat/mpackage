package com.co.example.service.contact.impl;

import com.co.example.common.dao.BaseDao;
import com.co.example.common.service.BaseServiceImpl;
import com.co.example.dao.contact.TContactDao;
import com.co.example.entity.contact.TContact;
import com.co.example.service.contact.TContactService;
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