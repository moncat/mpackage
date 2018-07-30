package com.co.example.service.admin;

import com.github.moncat.common.service.BaseService;
import com.co.example.entity.admin.TAdminRole;

public interface TAdminRoleService extends BaseService<TAdminRole, Long> {
	
	/**
	 * 角色关联管理员
	 * @param roleId
	 * @param adminIds
	 */
	void addAdminConn(Long roleId, Long[] adminIds);
}