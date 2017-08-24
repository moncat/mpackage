package com.co.example.service.admin;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.github.moncat.common.service.BaseService;
import com.co.example.common.utils.PageReq;
import com.co.example.entity.admin.TAdmin;
import com.co.example.entity.admin.TAdminActive;
import com.co.example.entity.admin.aide.AdminSession;
import com.co.example.entity.admin.aide.TAdminQuery;

public interface TAdminService extends BaseService<TAdmin, Long> {
	
	
	TAdmin queryByLoginName(String name);

	void updateLogin(AdminSession oldUsersSession, TAdmin admin, String ip);

	Page<TAdmin> queryAllInfoList(TAdminQuery query, PageReq pageReq);

	Map<String, Object> addAdmin(TAdminQuery query, TAdminActive tAdminActive, Long adminId);

	Map<String, Object> edit(TAdminQuery query, TAdminActive tAdminActive, Long adminId);
	
}