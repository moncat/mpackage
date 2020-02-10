package com.co.example.dao.manifest.impl;

import com.co.example.dao.manifest.TBrManifestProductDao;
import com.co.example.entity.manifest.TBrManifestProduct;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrManifestProductDaoImpl extends BaseDaoImpl<TBrManifestProduct, Long> implements TBrManifestProductDao {
}