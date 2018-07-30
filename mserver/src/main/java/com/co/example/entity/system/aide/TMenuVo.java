package com.co.example.entity.system.aide;

import java.util.List;

import com.co.example.entity.system.TMenu;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class TMenuVo extends TMenu {
	
	/**
	 * 子菜单
	 */
	 private List<TMenu> menus;
	 
	 /**
	  * 关联某角色id
	  */
	 private Long roleId;
	 
	 /**
	  * menu树是否选中
	  */
	 private Boolean checked = false;
	
}