package com.co.example.service.user.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.example.dao.user.TUserDao;
import com.co.example.entity.system.TRole;
import com.co.example.entity.system.aide.TRoleQuery;
import com.co.example.entity.user.TUser;
import com.co.example.entity.user.TUserRole;
import com.co.example.entity.user.aide.TUserQuery;
import com.co.example.entity.user.aide.TUserRoleQuery;
import com.co.example.entity.user.aide.TUserVo;
import com.co.example.entity.user.aide.UserSession;
import com.co.example.service.system.TRoleService;
import com.co.example.service.user.TUserRoleService;
import com.co.example.service.user.TUserService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TUserServiceImpl extends BaseServiceImpl<TUser, Long> implements TUserService {
    @Resource
    private TUserDao tUserDao;

    @Override
    protected BaseDao<TUser, Long> getBaseDao() {
        return tUserDao;
    }
    
    @Autowired
    TRoleService tRoleService;
    
    @Autowired
    TUserRoleService tUserRoleService;

	@Override
	public TUser queryByLoginName(String name) {
		TUserQuery query = new TUserQuery();
		query.setLoginName(name);
		TUserVo user = queryOne(query);
		if(user != null){
			Long id = user.getId();
			TRoleQuery tRoleQuery = new TRoleQuery();
			tRoleQuery.setUserId(id);
			List<TRole> roles = tRoleService.queryList(tRoleQuery);
			user.setRoles(roles);
		}
		return user;
	}

	@Override
	public void updateLogin(UserSession oldSession, TUser user, String ip) {
		//修改用户登录信息
				log.info("***开始更新");
				try {
					Short visitCountInit = 1;
					user.setLastTime(new Date());
					user.setLastIp(ip);
					Short visitCount = user.getVisitCount();
					if(visitCount == null){
						user.setVisitCount(visitCountInit);
					}else{
						visitCount++;
						user.setVisitCount(visitCount);
					}
					updateById(user);
					user.setPassword(null);
					oldSession.setUser(user);
					oldSession.setLogin(true);
					//查询角色列表
					TUserRoleQuery tUserRoleQuery = new TUserRoleQuery();
					tUserRoleQuery.setUserId(user.getId());
					List<TUserRole> tUserRoles = tUserRoleService.queryList(tUserRoleQuery);
					List<Long> roles = Lists.newArrayList();
					for (TUserRole tUserRole : tUserRoles) {
						roles.add(tUserRole.getRoleId());
					}
					oldSession.setRoles(roles);
				} catch (Exception e) {
					e.printStackTrace();
				}
		
	}

	@Override
	public HashMap<String, Object> updatePwd(HttpSession session, String password1, String password2) {
		HashMap<String, Object> map = Maps.newHashMap();
		Boolean verifyVCode = (Boolean)session.getAttribute("verifyVCode");
		String phoneNum = (String)session.getAttribute("phoneNum");
		if(BooleanUtils.isFalse(verifyVCode)){
			map.put("code", "400");
			map.put("desc", "验证码不正确或已过期！");
			return map;
		}
		if(!StringUtils.equals(password1, password2)){
			map.put("code", "400");
			map.put("desc", "两次密码不匹配！");
			return map;
		}
		session.removeAttribute("verifyVCode");
		session.removeAttribute("phoneNum");
		TUserQuery tUserQuery = new TUserQuery();
		tUserQuery.setLoginName(phoneNum);
		TUser one = queryOne(tUserQuery);
		TUser tUser = new TUser();
		tUser.setId(one.getId());
		tUser.setPassword(password1);
		updateByIdSelective(tUser);
		map.put("code", "200");
		return map;
	}
}