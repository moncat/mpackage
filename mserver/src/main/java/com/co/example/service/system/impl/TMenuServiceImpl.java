package com.co.example.service.system.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.co.example.common.constant.Constant;
import com.co.example.dao.system.TMenuDao;
import com.co.example.entity.system.TMenu;
import com.co.example.entity.system.TRole;
import com.co.example.entity.system.TRoleRight;
import com.co.example.entity.system.aide.TMenuConstant;
import com.co.example.entity.system.aide.TMenuQuery;
import com.co.example.entity.system.aide.TMenuVo;
import com.co.example.entity.system.aide.TRoleRightQuery;
import com.co.example.service.system.TMenuService;
import com.co.example.service.system.TRoleRightService;
import com.co.example.utils.BaseDataUtil;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Service
public class TMenuServiceImpl extends BaseServiceImpl<TMenu, Long> implements TMenuService {
    @Resource
    private TMenuDao tMenuDao;

    @Override
    protected BaseDao<TMenu, Long> getBaseDao() {
        return tMenuDao;
    }

    @Resource
    private TRoleRightService tRoleRightService;
    
    
    
	@Override
	public List<TMenu> getMenuTree(List<TRole> roles,Boolean flg) {
		//根id为0
		if(flg){
			Set<Long> menuIds = getMenuIdsByRoles(roles);
			return getMenuTree(0l,menuIds);
		}else{
			return getMenuTree(0l);
		}
		
	}
	
	//递归获取数据
	public List<TMenu> getMenuTree(Long parentId ,Set<Long> menuIds) {
		if(CollectionUtils.isEmpty(menuIds)){
			return Lists.newArrayList();
		}
		TMenuQuery tMenuQuery = new TMenuQuery();
		tMenuQuery.setParentId(parentId);
		tMenuQuery.setDelFlg(Constant.NO);
		tMenuQuery.setMenuIds(menuIds);
		List<TMenu> list = queryList(tMenuQuery ,new Sort(Direction.DESC ,"t.item_order"));
		if(CollectionUtils.isNotEmpty(list)){
			for(int i=0;i<list.size();i++){
				TMenuVo menuVo = (TMenuVo) list.get(i);
				Long id = menuVo.getId();
				if(id!=null){
					//递归遍历
					List<TMenu> subList= getMenuTree(id,menuIds);
					menuVo.setMenus(subList);
				}
			}
		}
		return list;
	}
	
	
	Set<Long> getMenuIdsByRoles(List<TRole> roles){
		HashSet<Long> menuIds = Sets.newHashSet();
		Long id = 0l;
		TRoleRightQuery tRoleRightQuery = new TRoleRightQuery();
		for (TRole tRole : roles) {
			id = tRole.getId();
			tRoleRightQuery.setRoleId(id);
			List<TRoleRight> roleRightList = tRoleRightService.queryList(tRoleRightQuery);
			for (TRoleRight tRoleRight : roleRightList) {
				menuIds.add(tRoleRight.getRightId());
			}
		}
		return menuIds;
	}
	
	
	
	public List<TMenu> getMenuTree(Long parentId) {
		TMenuQuery tMenuQuery = new TMenuQuery();
		tMenuQuery.setParentId(parentId);
		tMenuQuery.setDelFlg(Constant.NO);
		List<TMenu> list = queryList(tMenuQuery ,new Sort(Direction.DESC ,"t.item_order"));
		if(CollectionUtils.isNotEmpty(list)){
			for(int i=0;i<list.size();i++){
				TMenuVo menuVo = (TMenuVo) list.get(i);
				Long id = menuVo.getId();
				if(id!=null){
					//递归遍历
					List<TMenu> subList= getMenuTree(id);
					menuVo.setMenus(subList);
				}
			}
		}
		return list;
	}
	
	
	

	@Override
	public void deleteAll(Long id) {
		TMenuQuery tMenuQuery = new TMenuQuery();
		tMenuQuery.setParentId(id);
		delete(tMenuQuery);
		deleteById(id);
	}

	@Override
	public void addMenuAndSaPermission(TMenu menu) {
		add(menu);
		TRoleRight tRoleRight = new TRoleRight();
		tRoleRight.setRoleId(TMenuConstant.SUPER_ADMIN_ROLE_ID);
		Long rightId = menu.getId();
		tRoleRight.setRightId(rightId);
		BaseDataUtil.setDefaultData(tRoleRight);
		tRoleRightService.add(tRoleRight);
	}

}