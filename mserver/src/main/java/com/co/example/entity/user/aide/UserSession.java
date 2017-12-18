package com.co.example.entity.user.aide;

import java.util.List;

import com.co.example.entity.user.TUser;

import lombok.Data;


@Data
public class UserSession {
	/** 用户 */
	private TUser user;
	/** 是否登录 */
	private boolean login = false;
	/** 是否检查免登陆 */
	private boolean remembermeCheck = true;
	/** 角色列表  */
	private List<Long> roles ;

}
