package com.co.example.dao.order.impl;

import com.co.example.dao.order.TBrOrderDao;
import com.co.example.entity.order.TBrOrder;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrOrderDaoImpl extends BaseDaoImpl<TBrOrder, Long> implements TBrOrderDao {
}