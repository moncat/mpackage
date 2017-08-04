package com.co.example.dao.system.impl;

import com.co.example.common.dao.BaseDaoImpl;
import com.co.example.dao.system.TMenuDao;
import com.co.example.entity.system.TMenu;
import org.springframework.stereotype.Repository;

@Repository
public class TMenuDaoImpl extends BaseDaoImpl<TMenu, Long> implements TMenuDao {
}