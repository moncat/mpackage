package com.co.example.entity.system.aide;

import java.util.List;

import com.co.example.entity.system.TMenu;

import lombok.Getter;
import lombok.Setter;

public class TMenuVo extends TMenu {
	
	@Getter @Setter private List<TMenu> menus;
	
}