package com.co.example.entity.admin.aide;

import java.util.List;

import com.co.example.entity.admin.TAdmin;

import lombok.Data;


@Data
public class AdminSession {
	/** 用户 */
	private TAdmin admin;
	/** 是否登录 */
	private boolean login = false;
	/** 是否检查免登陆 */
	private boolean remembermeCheck = true;
	/** 角色列表  */
	private List<Long> roles ;

}
