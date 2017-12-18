package com.co.example.dao.information.impl;

import com.co.example.dao.information.TBrInformationDao;
import com.co.example.entity.information.TBrInformation;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrInformationDaoImpl extends BaseDaoImpl<TBrInformation, Long> implements TBrInformationDao {
}