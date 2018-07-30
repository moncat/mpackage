package com.co.example.entity.user.aide;

import java.util.List;

import com.co.example.entity.system.TRole;
import com.co.example.entity.user.TUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TUserVo extends TUser {
	/** 权限列表 */
	private List<TRole>  roles;
}