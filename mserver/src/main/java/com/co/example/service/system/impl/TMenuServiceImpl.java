package com.co.example.service.system.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.co.example.common.constant.Constant;
import com.co.example.common.dao.BaseDao;
import com.co.example.common.service.BaseServiceImpl;
import com.co.example.dao.system.TMenuDao;
import com.co.example.entity.system.TMenu;
import com.co.example.entity.system.aide.TMenuQuery;
import com.co.example.entity.system.aide.TMenuVo;
import com.co.example.service.system.TMenuService;

@Service
public class TMenuServiceImpl extends BaseServiceImpl<TMenu, Integer> implements TMenuService {
    @Resource
    private TMenuDao tMenuDao;

    @Override
    protected BaseDao<TMenu, Integer> getBaseDao() {
        return tMenuDao;
    }

	@Override
	public List<TMenu> getMenuTree() {
		//根id为0
		return getMenuTree(0);
	}
	
	public List<TMenu> getMenuTree(Integer parentId) {
		TMenuQuery tMenuQuery = new TMenuQuery();
		tMenuQuery.setParentId(parentId);
		tMenuQuery.setDelFlg(Constant.NO);
		List<TMenu> list = queryList(tMenuQuery);
		if(CollectionUtils.isNotEmpty(list)){
			for(int i=0;i<list.size();i++){
				TMenuVo menuVo = (TMenuVo) list.get(i);
				Integer id = menuVo.getId();
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
	public void deleteAll(Integer id) {
		TMenuQuery tMenuQuery = new TMenuQuery();
		tMenuQuery.setParentId(id);
		delete(tMenuQuery);
		deleteById(id);
	}
}