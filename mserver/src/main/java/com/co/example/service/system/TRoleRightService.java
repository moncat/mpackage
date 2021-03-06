package com.co.example.service.system;

import com.co.example.entity.system.TRoleRight;
import com.github.moncat.common.service.BaseService;

public interface TRoleRightService extends BaseService<TRoleRight, Long> {
	
	/**
	 * 角色关联菜单
	 * @param roleId
	 * @param menuIds
	 */
	void addConn(Long roleId,Long[] menuIds);
	
}