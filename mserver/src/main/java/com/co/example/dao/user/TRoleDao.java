package com.co.example.dao.user;

import java.util.List;

//import org.apache.ibatis.annotations.Select;

import com.co.example.common.dao.BaseDao;
import com.co.example.entity.user.TRole;

public interface TRoleDao extends BaseDao<TRole, Integer> {
	
	//@Select("select * from users")  
	List<TRole> queryRolesByUserId(Integer userId);
}