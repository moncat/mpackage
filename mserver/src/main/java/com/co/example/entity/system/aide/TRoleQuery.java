package com.co.example.entity.system.aide;

import com.co.example.entity.system.TRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TRoleQuery extends TRole {
	
	private Long userId;
	
	private Long adminId;
}