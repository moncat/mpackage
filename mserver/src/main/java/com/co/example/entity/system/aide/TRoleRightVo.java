package com.co.example.entity.system.aide;

import com.co.example.entity.system.TRoleRight;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class TRoleRightVo extends TRoleRight {
	
	private String roleName;
	
	private String name;

    private String url;
	
}