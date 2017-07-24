package com.co.example.dao.contact.impl;

import com.co.example.common.dao.BaseDaoImpl;
import com.co.example.dao.contact.TContactDao;
import com.co.example.entity.contact.TContact;
import org.springframework.stereotype.Repository;

@Repository
public class TContactDaoImpl extends BaseDaoImpl<TContact, Long> implements TContactDao {
}