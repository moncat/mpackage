package com.co.example.service.manifest.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.co.example.common.constant.Constant;
import com.co.example.constant.ManifestConstant;
import com.co.example.dao.manifest.TBrManifestDataDao;
import com.co.example.entity.manifest.TBrManifest;
import com.co.example.entity.manifest.TBrManifestData;
import com.co.example.service.manifest.TBrManifestAuthService;
import com.co.example.service.manifest.TBrManifestDataService;
import com.co.example.service.manifest.TBrManifestService;
import com.co.example.utils.BaseDataUtil;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;

@Service
public class TBrManifestDataServiceImpl extends BaseServiceImpl<TBrManifestData, Long> implements TBrManifestDataService {
    @Resource
    private TBrManifestDataDao tBrManifestDataDao;
    @Resource
    private TBrManifestService tBrManifestService;
    @Resource
    private TBrManifestAuthService tBrManifestAuthService;

    @Override
    protected BaseDao<TBrManifestData, Long> getBaseDao() {
        return tBrManifestDataDao;
    }

	@Override
	public Map<String, Object> connManifest(Long createBy , Long mid, Long[] ids) {
		Map<String, Object> result =  new HashMap<String, Object>();
		result.put("code", "200");		
		TBrManifestData tBrManifestData = null;
		TBrManifest one = tBrManifestService.queryById(mid);
		Byte status = one.getStatus();
		if(status != Constant.TASK_STATUS_WAITTING){
			result.put("code", "400");
			result.put("info", "该清单已开始计算，请关联其他清单！");
			return result;
		}
		
//		ArrayList<TBrManifestData> list = Lists.newArrayList();
		try {
			for (int j = 0; j < ids.length; j++) {
				tBrManifestData = new TBrManifestData();
				tBrManifestData.setConnId(ids[j]);
				tBrManifestData.setManifestId(mid);
				long queryCount = queryCount(tBrManifestData);
				if(queryCount==0){
					//二者没有关联关系时，关联，防止重复关联。
					tBrManifestData.setCreateBy(createBy);
					BaseDataUtil.setDefaultData(tBrManifestData);
					add(tBrManifestData);
				}
//				list.add(tBrManifestData);
			}
//			addInBatch(list);
		} catch (Exception e) {
			result.put("code", "400");
			result.put("info", "关联失败！");
		}
		result.put("info", "关联完成！");
		return result;
	}
}