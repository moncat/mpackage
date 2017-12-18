package com.co.example.service.system;

import java.util.List;

import com.co.example.entity.admin.TAdmin;
import com.co.example.entity.system.TMenu;
import com.co.example.entity.system.TRole;
import com.github.moncat.common.service.BaseService;

public interface TMenuService extends BaseService<TMenu, Long> {
	/**
	 * 获得菜单树
	 * @return
	 */
	List<TMenu> getMenuTree(List<TRole>  roles);
	
	void deleteAll(Long id);
	
	/**
	 * 根据用户获得角色，根据角色获得权限
	 * @param admin
	 * @return
	 */
	List<TMenu> queryMenuByAdmin(TAdmin admin);
	
}