package com.co.example.service.user.impl;

import com.co.example.common.dao.BaseDao;
import com.co.example.common.service.BaseServiceImpl;
import com.co.example.dao.user.TUserLoginDao;
import com.co.example.entity.user.TUserLogin;
import com.co.example.service.user.TUserLoginService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TUserLoginServiceImpl extends BaseServiceImpl<TUserLogin, Integer> implements TUserLoginService {
    @Resource
    private TUserLoginDao tUserLoginDao;

    @Override
    protected BaseDao<TUserLogin, Integer> getBaseDao() {
        return tUserLoginDao;
    }
}