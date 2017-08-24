package com.co.example.service.admin;

import com.github.moncat.common.service.BaseService;
import com.co.example.entity.admin.TAdminLogin;

public interface TAdminLoginService extends BaseService<TAdminLogin, Long> {

	TAdminLogin queryByRememberKey(String userKey);


}