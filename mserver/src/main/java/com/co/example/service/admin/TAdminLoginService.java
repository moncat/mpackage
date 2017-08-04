package com.co.example.service.admin;

import com.co.example.common.service.BaseService;
import com.co.example.entity.admin.TAdminLogin;

public interface TAdminLoginService extends BaseService<TAdminLogin, Long> {

	TAdminLogin queryByRememberKey(String userKey);


}