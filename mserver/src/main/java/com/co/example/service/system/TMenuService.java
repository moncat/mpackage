package com.co.example.service.system;

import java.util.List;

import com.co.example.entity.system.TMenu;
import com.co.example.entity.system.TRole;
import com.github.moncat.common.service.BaseService;

public interface TMenuService extends BaseService<TMenu, Long> {
	/**
	 *  获得菜单树
	 * @param roles 角色列表
	 * @param flg 是否根据角色过滤
	 * @return
	 */
	List<TMenu> getMenuTree(List<TRole>  roles,Boolean flg);
	
	
	
	
	void deleteAll(Long id);
	
	/**
	 * 添加菜单，并默认赋权给超级管理员
	 */
	void addMenuAndSaPermission(TMenu menu);
	
}