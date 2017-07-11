package com.co.example.service.user;

import java.util.List;

import com.co.example.common.service.BaseService;
import com.co.example.entity.user.TRole;

public interface TRoleService extends BaseService<TRole, Integer> {
	
	List<TRole> queryRolesByUserId(Integer userId);
}