package com.co.example.entity.system.aide;

import java.util.Set;

import com.co.example.entity.system.TMenu;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@SuppressWarnings("serial")
public class TMenuQuery extends TMenu {
	
	private Set<Long> menuIds;
	
	private Long roleId;
}