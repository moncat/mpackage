package com.co.example.service.user.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.MD5;
import com.co.example.dao.user.TUserDao;
import com.co.example.entity.system.TRole;
import com.co.example.entity.system.aide.TRoleQuery;
import com.co.example.entity.user.TUser;
import com.co.example.entity.user.TUserRole;
import com.co.example.entity.user.aide.TBrUserStatisticsConstant;
import com.co.example.entity.user.aide.TUserQuery;
import com.co.example.entity.user.aide.TUserRoleQuery;
import com.co.example.entity.user.aide.TUserVo;
import com.co.example.entity.user.aide.UserSession;
import com.co.example.service.log.TBrLogUserService;
import com.co.example.service.system.TRoleService;
import com.co.example.service.user.TBrUserStatisticsService;
import com.co.example.service.user.TUserActiveService;
import com.co.example.service.user.TUserRoleService;
import com.co.example.service.user.TUserService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import com.google.common.collect.Lists;

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
    
    @Autowired
    TBrLogUserService tBrLogUserService;
    
    @Autowired
    TUserActiveService tUserActiveService;  
    
    @Autowired
    TBrUserStatisticsService tBrUserStatisticsService;  

	@Override
	public TUser queryByLoginName(String name) {
		TUserQuery query = new TUserQuery();
		query.setLoginName(name);
		query.setDelFlg(Constant.NO);
		query.setIsActive(Constant.STATUS_ACTIVE);
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
	public void updateLogin(UserSession oldSession, TUser user, String ip,String openId) {
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
			//登录成功：用当前openId查询一条数据   如果没数据,则更新关联关系，如果有则不动，不与前数据解绑
			if(StringUtils.isNoneBlank(openId)&& !openId.equals("0")){
				TUserQuery tUserQuery = new TUserQuery();
				tUserQuery.setWxOpenId(openId);
				TUser one = queryOne(tUserQuery);
				if(one ==null){
					user.setWxOpenId(openId);
					log.info("用户登录user对象中添加openId="+openId);
				}
			}else{
				log.info("用户登录user对象中没有openId="+openId);
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
	public Boolean updatePwd(String ip ,String phoneNum, String password1, String password2,String vcode) {
		if(StringUtils.equals(password1, password2)){
			TUserQuery tUserQuery = new TUserQuery();
			tUserQuery.setLoginName(phoneNum);
			TUser one = queryOne(tUserQuery);
			TUser tUser = new TUser();
			tUser.setId(one.getId());
			tUser.setPassword(MD5.encodeStr(password1));
			updateByIdSelective(tUser);
			//验证码已用
			if(vcode !=null){
				tUserActiveService.updateVCodeUsed(phoneNum, vcode);
			}
			//保存日志			
			tBrLogUserService.updatePwd(phoneNum, ip);
			return true;
		}
		return false;
	}

	@Override
	public Boolean addUser(String vcode ,String ip ,String phoneNum, String password1,String openId) {
		TUser tUser = new TUser();
		tUser.setLoginName(phoneNum);
		tUser.setMobilePhone(phoneNum);
		tUser.setPassword(MD5.encodeStr(password1));
		tUser.setCreateTime(new Date());
		tUser.setLastIp(ip);
		tUser.setLastTime(new Date());
		tUser.setIsActive(Constant.STATUS_ACTIVE);
		tUser.setDelFlg(Constant.NO);
		//注册保存：用当前openId查询一条数据   如果没数据,则保存关联关系，如果有则不动，不与前数据解绑
		if(StringUtils.isNoneBlank(openId)&& !openId.equals("0")){
			TUserQuery tUserQuery = new TUserQuery();
			tUserQuery.setWxOpenId(openId);
			TUser one = queryOne(tUserQuery);
			if(one ==null){
				tUser.setWxOpenId(openId);
				log.info("用户注册user对象中添加openId="+openId);
			}
		}else{
			log.info("用户注册user对象中没有openId="+openId);
		}
		add(tUser);
		//统计用户注册
		tBrUserStatisticsService.addUserStatistics(tUser.getId(), TBrUserStatisticsConstant.REGISTER);
		//保存日志
		tBrLogUserService.addAccount(phoneNum, ip);
		
		//验证码已用
		tUserActiveService.updateVCodeUsed(phoneNum, vcode);
		
		return true;
	}

	
}


