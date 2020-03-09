package com.co.example.service.admin;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.github.moncat.common.service.BaseService;
import com.co.example.common.utils.PageReq;
import com.co.example.entity.admin.TAdmin;
import com.co.example.entity.admin.TAdminActive;
import com.co.example.entity.admin.aide.AdminSession;
import com.co.example.entity.admin.aide.TAdminQuery;
import com.co.example.entity.user.TUser;
import com.co.example.entity.user.aide.UserSession;

public interface TAdminService extends BaseService<TAdmin, Long> {
	
	
	TAdmin queryByLoginName(String name);

	void updateLogin(AdminSession oldUsersSession, TAdmin admin, String ip);

	Page<TAdmin> queryAllInfoList(TAdminQuery query, PageReq pageReq);

	Map<String, Object> addAdmin(TAdminQuery query, TAdminActive tAdminActive, Long adminId);

	Map<String, Object> edit(TAdminQuery query, TAdminActive tAdminActive, Long adminId);
	
	Boolean updatePwd(String phoneNum, String password1, String password2);

	

	
	
}