package com.co.example.dao.user.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.co.example.common.dao.BaseDaoImpl;
import com.co.example.dao.user.TRoleDao;
import com.co.example.entity.user.TRole;
import com.co.example.entity.user.aide.TRoleQuery;

@Repository
public class TRoleDaoImpl extends BaseDaoImpl<TRole, Integer> implements TRoleDao {

	@Override
	public List<TRole> queryRolesByUserId(Integer userId) {
		TRoleQuery query = new TRoleQuery();
		query.setUserId(userId);
		List<TRole> roles = selectList(query);
		return roles;
	}
}