package com.co.example.dao.message.impl;

import com.co.example.dao.message.TBrMessageDao;
import com.co.example.entity.message.TBrMessage;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrMessageDaoImpl extends BaseDaoImpl<TBrMessage, Long> implements TBrMessageDao {
}