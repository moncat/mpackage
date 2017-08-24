package com.co.example.service.system;

import java.util.List;

import com.github.moncat.common.service.BaseService;
import com.co.example.entity.system.TMenu;

public interface TMenuService extends BaseService<TMenu, Long> {
	/**
	 * 获得菜单树
	 * @return
	 */
	List<TMenu> getMenuTree();
	
	void deleteAll(Long id);
}