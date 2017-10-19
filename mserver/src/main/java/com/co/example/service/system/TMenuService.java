package com.co.example.service.system;

import java.util.List;

import com.github.moncat.common.service.BaseService;
import com.co.example.entity.system.TMenu;
import com.co.example.entity.system.TRole;

public interface TMenuService extends BaseService<TMenu, Long> {
	/**
	 * 获得菜单树
	 * @return
	 */
	List<TMenu> getMenuTree(List<TRole>  roles);
	
	void deleteAll(Long id);
}