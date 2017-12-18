package com.co.example.dao.information.impl;

import com.co.example.dao.information.TBrInformationTypeDao;
import com.co.example.entity.information.TBrInformationType;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrInformationTypeDaoImpl extends BaseDaoImpl<TBrInformationType, Long> implements TBrInformationTypeDao {
}