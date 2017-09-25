package com.co.example.entity.admin.aide;

import java.util.List;

import com.co.example.entity.admin.TAdmin;
import com.co.example.entity.admin.TAdminRole;
import com.co.example.entity.system.TRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TAdminVo extends TAdmin {
	/** 权限列表 */
	private List<TRole>  roles;
	
	/** 管理员和权限关联列表 */
	private List<TAdminRole> AdminRoles;
	
}