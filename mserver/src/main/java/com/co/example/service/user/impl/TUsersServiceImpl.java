package com.co.example.service.user.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.co.example.common.dao.BaseDao;
import com.co.example.common.service.BaseServiceImpl;
import com.co.example.dao.user.TUsersDao;
import com.co.example.entity.user.TRole;
import com.co.example.entity.user.TUsers;
import com.co.example.entity.user.aide.TRoleQuery;
import com.co.example.entity.user.aide.TUsersQuery;
import com.co.example.entity.user.aide.TUsersVo;
import com.co.example.service.user.TRoleService;
import com.co.example.service.user.TUsersService;

@Service
public class TUsersServiceImpl extends BaseServiceImpl<TUsers, Integer> implements TUsersService {
    @Resource
    private TUsersDao tUsersDao;
    
    @Resource
    private TRoleService tRoleService;
    

    @Override
    protected BaseDao<TUsers, Integer> getBaseDao() {
        return tUsersDao;
    }

	public TUsers queryByLoginName(String username) {
		TUsersVo userVo = new TUsersVo();
		TUsersQuery query = new TUsersQuery();
		query.setUserName(username);
		userVo = queryOne(query);
		if(userVo !=null){
			TRoleQuery tRoleQuery = new TRoleQuery();
			tRoleQuery.setUserId(userVo.getUserId());
			List<TRole> roles= tRoleService.queryList(tRoleQuery);
			userVo.setRoles(roles);
		}
		return userVo;
	}

	@Override
	public void addUser() {
		TUsers user = new TUsers();
		user.setUserName("lisi");
		user.setUserPassword("4");
		add(user);
		System.out.println("yes");
	}

	
}