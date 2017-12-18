package com.co.example.service.admin.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.MD5;
import com.co.example.common.utils.PageReq;
import com.co.example.common.utils.ValidateUtil;
import com.co.example.dao.admin.TAdminDao;
import com.co.example.entity.admin.TAdmin;
import com.co.example.entity.admin.TAdminActive;
import com.co.example.entity.admin.TAdminRole;
import com.co.example.entity.admin.aide.AdminSession;
import com.co.example.entity.admin.aide.TAdminQuery;
import com.co.example.entity.admin.aide.TAdminRoleQuery;
import com.co.example.entity.admin.aide.TAdminVo;
import com.co.example.entity.system.TRole;
import com.co.example.entity.system.aide.TRoleQuery;
import com.co.example.service.admin.TAdminActiveService;
import com.co.example.service.admin.TAdminRoleService;
import com.co.example.service.admin.TAdminService;
import com.co.example.service.system.TRoleService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TAdminServiceImpl extends BaseServiceImpl<TAdmin, Long> implements TAdminService {
    @Resource
    private TAdminDao tAdminDao;
    
    @Resource
    private TRoleService tRoleService;
    
    @Resource
    private TAdminRoleService tAdminRoleService;
    
    @Resource
    private TAdminActiveService tAdminActiveService;

    @Override
    protected BaseDao<TAdmin, Long> getBaseDao() {
        return tAdminDao;
    }

	@Override
	public TAdmin queryByLoginName(String name) {
		TAdminQuery query = new TAdminQuery();
		query.setLoginName(name);
		TAdminVo admin = queryOne(query);
		if(admin != null){
			Long id = admin.getId();
			TRoleQuery tRoleQuery = new TRoleQuery();
			tRoleQuery.setAdminId(id);
			List<TRole> roles = tRoleService.queryList(tRoleQuery);
			admin.setRoles(roles);
		}
		return admin;
	}

	@Override
	public void updateLogin(AdminSession oldSession, TAdmin admin, String ip) {
		//修改用户登录信息
		log.info("***开始更新");
		try {
			Short visitCountInit = 1;
			admin.setLastTime(new Date());
			admin.setLastIp(ip);
			Short visitCount = admin.getVisitCount();
			if(visitCount == null){
				admin.setVisitCount(visitCountInit);
			}else{
				visitCount++;
				admin.setVisitCount(visitCount);
			}
			updateById(admin);
			admin.setPassword(null);
			oldSession.setAdmin(admin);
			oldSession.setLogin(true);
			//查询角色列表
			TAdminRoleQuery tAdminRoleQuery = new TAdminRoleQuery();
			tAdminRoleQuery.setAdminId(admin.getId());
			List<TAdminRole> tAdminRoles = tAdminRoleService.queryList(tAdminRoleQuery);
			List<Long> roles = Lists.newArrayList();
			for (TAdminRole tAdminRole : tAdminRoles) {
				roles.add(tAdminRole.getRoleId());
			}
			oldSession.setRoles(roles);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Page<TAdmin> queryAllInfoList(TAdminQuery query, PageReq pageReq) {
		Page<TAdmin> page = queryPageList(query, pageReq);
		for (TAdmin admin:page.getContent()) {
			TAdminVo tAdminVo = (TAdminVo)admin;
			TAdminRoleQuery tAdminRoleQuery = new TAdminRoleQuery();
			tAdminRoleQuery.setAdminId(tAdminVo.getId());
			List<TAdminRole> adminRoles = tAdminRoleService.queryList();
			tAdminVo.setAdminRoles(adminRoles);
		}
		return page;
	}

	@Override
	public Map<String, Object> addAdmin(TAdminQuery admin, TAdminActive tAdminActive, Long adminId) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		if(queryByLoginName(admin.getLoginName()) != null){
			mapResult.put("code",400);
			mapResult.put("desc", "该用户已存在!");
			return mapResult;
		}
		admin.setIsActive(Constant.STATUS_ACTIVE);
		admin.setCreateTime(new Date());
		admin.setDelFlg(Constant.NO);

		String name = admin.getLoginName();
		if(ValidateUtil.isMobile(name)){
			admin.setMobilePhone(name);
		}
		if(ValidateUtil.isEmail(name)){
			admin.setEmail(name);
		}
		
		//密码加密
		admin.setPassword(MD5.encodeStr(admin.getPassword()));
		add(admin);
		//用户注册信息表,暂时不使用注册功能
		if(tAdminActive != null){
			TAdminActive tAdminActiveTmp = new TAdminActive();
			tAdminActiveTmp.setId(tAdminActive.getId());
			tAdminActiveTmp.setIsUse(Constant.YES);
			tAdminActiveTmp.setUsetime(new Date());	
			tAdminActiveService.updateByIdSelective(tAdminActiveTmp);
		}
		//添加角色
		List<Long> roleIds = admin.getRoleIds();
		if(CollectionUtils.isNotEmpty(roleIds)){
			List<TAdminRole> TAdminRoles = Lists.newArrayList();
			TAdminRole tAdminRole = null;
			for (Long roleId : roleIds) {
				tAdminRole = new TAdminRole();
				tAdminRole.setRoleId(roleId);
				tAdminRole.setAdminId(admin.getId());
				TAdminRoles.add(tAdminRole);
			}
			tAdminRoleService.addInBatch(TAdminRoles);
		}
		
		mapResult.put("code",200);
		mapResult.put("desc", "新增成功！");
		return mapResult;
	}

	@Override
	public Map<String, Object> edit(TAdminQuery admin, TAdminActive tAdminActive, Long adminId) {
		//用户注册信息表,暂时不使用注册功能 tAdminActive
		Map<String,Object> mapResult = new HashMap<String,Object>();

		String name = admin.getLoginName();
		if(ValidateUtil.isMobile(name)){
			admin.setMobilePhone(name);
		}
		if(ValidateUtil.isEmail(name)){
			admin.setEmail(name);
		}
		//密码加密
		admin.setPassword(MD5.encodeStr(admin.getPassword()).toLowerCase());
		updateByIdSelective(admin);
		//删除原角色
		TAdminRoleQuery query = new TAdminRoleQuery();
		query.setId(admin.getId());
		tAdminRoleService.delete(query);
		//添加角色
		List<Long> roleIds = admin.getRoleIds();
		List<TAdminRole> TAdminRoles = Lists.newArrayList();
		TAdminRole tAdminRole = null;
		for (Long roleId : roleIds) {
			tAdminRole = new TAdminRole();
			tAdminRole.setRoleId(roleId);
			tAdminRole.setAdminId(admin.getId());
			TAdminRoles.add(tAdminRole);
		}
		tAdminRoleService.addInBatch(TAdminRoles);
		mapResult.put("code",200);
		mapResult.put("desc", "新增成功！");
		return mapResult;
	}
}