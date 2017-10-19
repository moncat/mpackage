package com.co.example.service.system.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.co.example.common.constant.Constant;
import com.co.example.dao.system.TMenuDao;
import com.co.example.entity.system.TMenu;
import com.co.example.entity.system.TRole;
import com.co.example.entity.system.aide.TMenuQuery;
import com.co.example.entity.system.aide.TMenuVo;
import com.co.example.service.system.TMenuService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;

@Service
public class TMenuServiceImpl extends BaseServiceImpl<TMenu, Long> implements TMenuService {
    @Resource
    private TMenuDao tMenuDao;

    @Override
    protected BaseDao<TMenu, Long> getBaseDao() {
        return tMenuDao;
    }

	@Override
	public List<TMenu> getMenuTree(List<TRole> roles) {
		//根id为0
		return getMenuTree(0l,roles);
	}
	
	//////////////TODO 权限
	public List<TMenu> getMenuTree(Long parentId ,List<TRole> roles) {
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
					List<TMenu> subList= getMenuTree(id,roles);
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
}