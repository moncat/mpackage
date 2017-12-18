package com.co.example.dao.message.impl;

import com.co.example.dao.message.TBrMessageTemplateDao;
import com.co.example.entity.message.TBrMessageTemplate;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrMessageTemplateDaoImpl extends BaseDaoImpl<TBrMessageTemplate, Long> implements TBrMessageTemplateDao {
}