package com.co.example.dao.luban.impl;

import com.co.example.dao.luban.TLubanOrderDao;
import com.co.example.entity.luban.TLubanOrder;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TLubanOrderDaoImpl extends BaseDaoImpl<TLubanOrder, Long> implements TLubanOrderDao {
}