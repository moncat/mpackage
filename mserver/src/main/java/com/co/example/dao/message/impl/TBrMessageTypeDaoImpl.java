package com.co.example.dao.message.impl;

import com.co.example.dao.message.TBrMessageTypeDao;
import com.co.example.entity.message.TBrMessageType;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrMessageTypeDaoImpl extends BaseDaoImpl<TBrMessageType, Long> implements TBrMessageTypeDao {
}