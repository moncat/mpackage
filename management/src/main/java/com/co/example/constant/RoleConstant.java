package com.co.example.constant;

import java.util.ArrayList;
import java.util.List;

public class RoleConstant {
	/**
	 * 管理员
	 */
	public static final Long SUPER_ADMIN = 1l;
	/**
	 * 主管
	 */
	public static final Long ADMIN = 2l;
	
	
	public static final List<Long> ROLES = new ArrayList<Long>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		{
			add(SUPER_ADMIN);
			add(ADMIN);
		}
	};
	
	
}
