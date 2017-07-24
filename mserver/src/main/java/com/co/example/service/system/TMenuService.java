package com.co.example.service.system;

import java.util.List;

import com.co.example.common.service.BaseService;
import com.co.example.entity.system.TMenu;

public interface TMenuService extends BaseService<TMenu, Integer> {
	/**
	 * 获得菜单树
	 * @return
	 */
	List<TMenu> getMenuTree();
	
	void deleteAll(Integer id);
}