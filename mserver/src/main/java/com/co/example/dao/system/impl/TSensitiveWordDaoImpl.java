package com.co.example.dao.system.impl;

import com.co.example.dao.system.TSensitiveWordDao;
import com.co.example.entity.system.TSensitiveWord;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TSensitiveWordDaoImpl extends BaseDaoImpl<TSensitiveWord, Long> implements TSensitiveWordDao {
}