package com.co.example.dao.manifest.impl;

import com.co.example.dao.manifest.TBrManifestDao;
import com.co.example.entity.manifest.TBrManifest;
import com.github.moncat.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class TBrManifestDaoImpl extends BaseDaoImpl<TBrManifest, Long> implements TBrManifestDao {
}