package com.co.example.entity.admin.aide;

import java.util.List;

import com.co.example.entity.admin.TAdmin;
import com.co.example.entity.admin.TAdminRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TAdminVo extends TAdmin {
	/** 权限列表 */
	private List<TAdminRole>  adminRoles;
}