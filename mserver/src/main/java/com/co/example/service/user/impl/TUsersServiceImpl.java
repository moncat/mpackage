package com.co.example.service.user.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.co.example.common.dao.BaseDao;
import com.co.example.common.service.BaseServiceImpl;
import com.co.example.dao.user.TUsersDao;
import com.co.example.entity.user.TUserLogin;
import com.co.example.entity.user.TUsers;
import com.co.example.entity.user.aide.TUsersQuery;
import com.co.example.service.user.TUserLoginService;
import com.co.example.service.user.TUsersService;

@Service
public class TUsersServiceImpl extends BaseServiceImpl<TUsers, Integer> implements TUsersService {
    @Resource
    private TUsersDao tUsersDao;
    

	@Resource
	TUserLoginService tUserLoginService;
    

    @Override
    protected BaseDao<TUsers, Integer> getBaseDao() {
        return tUsersDao;
    }

	public TUsers queryByLoginName(String username) {
		TUsersQuery query = new TUsersQuery();
		query.setUserName(username);
		return queryOne(query);
	}

	@Override
	public void addUser() {
		TUsers user = new TUsers();
		user.setUserName("lisi");
		user.setUserPassword("4");
		TUserLogin tUserLogin = new TUserLogin();
		tUserLogin.setUserId(1);
		tUserLogin.setLoginIp("1.1.1.2");
		add(user);
		tUserLoginService.add(tUserLogin);
		System.out.println("yes");
	}

	
}