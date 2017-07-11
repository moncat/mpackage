package com.co.example.entity.user.aide;

import java.util.List;

import com.co.example.entity.user.TRole;
import com.co.example.entity.user.TUsers;

public class TUsersVo extends TUsers {
	
	private List<TRole> Roles;

	public List<TRole> getRoles() {
		return Roles;
	}

	public void setRoles(List<TRole> roles) {
		Roles = roles;
	}
	
}