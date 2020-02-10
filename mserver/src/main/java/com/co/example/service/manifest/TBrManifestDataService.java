package com.co.example.service.manifest;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.co.example.entity.manifest.TBrManifestData;
import com.github.moncat.common.service.BaseService;

public interface TBrManifestDataService extends BaseService<TBrManifestData, Long> {
	
	Map<String, Object> connManifest(Long createBy, Long mid, Long[] ids);
}