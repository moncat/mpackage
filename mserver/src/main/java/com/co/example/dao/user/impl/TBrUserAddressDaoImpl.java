package com.co.example.dao.user.impl;

import com.co.example.dao.user.TBrUserAddressDao;
import com.co.example.entity.user.TBrUserAddress;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrUserAddressDaoImpl extends BaseDaoImpl<TBrUserAddress, Long> implements TBrUserAddressDao {
}