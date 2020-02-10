package com.co.example.service.product.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.co.example.common.utils.DateUtil;
import com.co.example.dao.product.TBrIngredientDao;
import com.co.example.entity.label.TBrIngredientLabel;
import com.co.example.entity.label.aide.TBrIngredientLabelQuery;
import com.co.example.entity.product.TBrAim;
import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.aide.TBrAimQuery;
import com.co.example.entity.product.aide.TBrIngredientCountVo;
import com.co.example.entity.product.aide.TBrIngredientQuery;
import com.co.example.entity.product.aide.TBrIngredientVo;
import com.co.example.service.label.TBrIngredientLabelService;
import com.co.example.service.product.TBrAimService;
import com.co.example.service.product.TBrIngredientService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class TBrIngredientServiceImpl extends BaseServiceImpl<TBrIngredient, Long> implements TBrIngredientService {
	@Resource
	private TBrIngredientDao tBrIngredientDao;

	@Resource
	private TBrAimService tBrAimService;

	@Resource
	private TBrIngredientLabelService tBrIngredientLabelService;

	@Override
	protected BaseDao<TBrIngredient, Long> getBaseDao() {
		return tBrIngredientDao;
	}

	@Override
	public void decorateColour(List<TBrIngredient> ingredientList) {
		ingredientList.forEach(ingredient -> {
			TBrIngredientVo ingredientVo = (TBrIngredientVo) ingredient;
			ingredientVo.setSafeColour("badge-success");
			String securityRisks = ingredientVo.getSecurityRisks();
			if (StringUtils.isNoneBlank(securityRisks)) {
				if (securityRisks.indexOf("-") > 0) {
					securityRisks = securityRisks.substring(0, securityRisks.lastIndexOf("-"));
				}
				int safeInt = Integer.parseInt(securityRisks);
				if (safeInt >= 3 && safeInt <= 6) {
					ingredientVo.setSafeColour("badge-warning");
				}
				if (safeInt > 6) {
					ingredientVo.setSafeColour("badge-danger");
				}
			}

			getAims(ingredientVo);
		});
	}

	@Override
	public void getAims(TBrIngredientVo ingredientVo) {
		TBrAimQuery tBrAimQuery = new TBrAimQuery();
		tBrAimQuery.setIngredientId(ingredientVo.getId());
		tBrAimQuery.setJoinFlg(true);
		List<TBrAim> tBrAims = tBrAimService.queryList(tBrAimQuery);
		ingredientVo.setTBrAims(tBrAims);
		String aimsStr = "";
		for (TBrAim tBrAim : tBrAims) {
			aimsStr += tBrAim.getName() + " ";
		}
		ingredientVo.setAims(aimsStr);
	}

	@Override
	public Map<String, Integer> ingredientAnalyze(Long productId) {
		Map<String, Integer> map = Maps.newHashMap();
		TBrIngredientQuery query = new TBrIngredientQuery();
		query.setProductId(productId);
		query.setJoinFlg(true);
		List<TBrIngredient> list = queryList(query);
		String securityRisks = null;
		int safeInt = 0;
		int layer1 = 0;
		int layer2 = 0;
		int layer3 = 0;

		for (TBrIngredient tBrIngredient : list) {
			securityRisks = tBrIngredient.getSecurityRisks();
			if (StringUtils.isNoneBlank(securityRisks)) {
				if (securityRisks.indexOf("-") > 0) {
					securityRisks = securityRisks.substring(0, securityRisks.lastIndexOf("-"));
				}
				safeInt = Integer.parseInt(securityRisks);

				if (safeInt < 3) {
					layer1++;
				} else if (safeInt >= 3 && safeInt <= 6) {
					layer2++;
				} else if (safeInt > 6) {
					layer3++;
				}
			}
		}
		map.put("layer1", layer1);
		map.put("layer2", layer2);
		map.put("layer3", layer3);
		return map;
	}

	private static final String[] SAFE_TYPE = { "香精", "防腐" };
	private static final String[] EFFECT_TYPE = { "美白", "保湿" };

	@Override
	public List<Map<String, Object>> safeAnalyze(Long productId) {
		return analyzeCount(productId, SAFE_TYPE);
	}

	@Override
	public List<Map<String, Object>> effectAnalyze(Long productId) {
		return analyzeCount(productId, EFFECT_TYPE);
	}

	private List<Map<String, Object>> analyzeCount(Long productId, String[] strs) {
		List<Map<String, Object>> list = Lists.newArrayList();
		TBrIngredientQuery tBrIngredientQuery = new TBrIngredientQuery();
		tBrIngredientQuery.setProductId(productId);
		tBrIngredientQuery.setJoinFlg(true);
		List<TBrIngredient> ingredientList = queryList(tBrIngredientQuery);
		for (int i = 0; i < strs.length; i++) {
			int num = 0;
			Map<String, Object> map = Maps.newHashMap();
			for (TBrIngredient ingredient : ingredientList) {
				TBrAimQuery tBrAimQuery = new TBrAimQuery();
				tBrAimQuery.setIngredientId(ingredient.getId());
				tBrAimQuery.setJoinFlg(true);
				List<TBrAim> tBrAims = tBrAimService.queryList(tBrAimQuery);
				for (TBrAim tBrAim : tBrAims) {
					String name = tBrAim.getName();
					if (name.contains(strs[i])) {
						num++;
					}
				}
			}
			if (num > 0) {
				map.put("key", strs[i]);
				map.put("value", num);
				list.add(map);
			}
		}
		return list;
	}

	@Override
	public List<TBrIngredientCountVo> queryIngredientCount(String limitTime, String endTime) {
		return tBrIngredientDao.selectIngredientCount(limitTime, endTime);
	}

	@Override
	public List<TBrIngredient> queryTBrIngredientList(Long productId) {
		return queryTBrIngredientList(productId, false);
	}

	@Override
	public List<TBrIngredient> queryTBrIngredientList(Long productId, Boolean simple) {
		TBrIngredientQuery tBrIngredientQuery = new TBrIngredientQuery();
		tBrIngredientQuery.setJoinFlg(true);
		tBrIngredientQuery.setProductId(productId);
		List<TBrIngredient> selectList = tBrIngredientDao.selectList(tBrIngredientQuery);
		if (!simple) {
			TBrIngredientVo ingredientVo;
			for (TBrIngredient ingredient : selectList) {
				ingredientVo = (TBrIngredientVo) ingredient;
				getAims(ingredientVo);
			}
		}
		return selectList;
	}

	@Override
	public Float getProductScore(Long productId) {
		List<TBrIngredient> selectList = queryTBrIngredientList(productId);
		return getProductScore(selectList);
	}

	@Override
	public Float getProductScore(List<TBrIngredient> selectList) {
		int safeInt, index = 0;
		for (TBrIngredient tBrIngredient : selectList) {
			String securityRisks = tBrIngredient.getSecurityRisks();
			if (StringUtils.isNoneBlank(securityRisks)) {
				if (securityRisks.indexOf("-") > 0) {
					securityRisks = securityRisks.substring(0, securityRisks.lastIndexOf("-"));
				}
				safeInt = Integer.parseInt(securityRisks);
				if (safeInt < 3) {
					index++;
				}
			}
		}
		int size = selectList.size();
		if (size == 0) {
			size = 1;
		}
		float safe = index * 5 / size;
		return safe;
	}

	@Override
	public Map<String, Object> getIngredientTrend(Collection<TBrIngredient> list) {
		HashMap<String, Object> map = Maps.newHashMap();
		List<String> months = DateUtil.getMonths();
		List<Date> in = DateUtil.getDateMonthsOnYear();
		HashMap<String, Integer> dataMap = Maps.newHashMap();
		for (int i = 0; i < 12; i++) {
			String key = "key" + (i + 1);
			for (TBrIngredient tBrIngredient : list) {
				Date createTime = tBrIngredient.getCreateTime();
				int c1 = createTime.compareTo(in.get(i));
				int c2 = createTime.compareTo(in.get(i + 1));
				if (c1 > 0 && c2 < 0) {
					Integer integer = dataMap.get(key);
					if (integer == null) {
						dataMap.put(key, 1);
					} else {
						dataMap.put(key, 1 + integer);
					}
				}
			}
			Integer tmp = dataMap.get(key);
			if (tmp == null) {
				dataMap.put(key, 0);
			}

		}
		map.put("legend", months);
		map.put("series", dataMap.values());
		return map;
	}

	@Override
	public List<TBrIngredientLabel> getLabelListById(Long ingredientId) {
		TBrIngredientLabelQuery tBrIngredientLabelQuery = new TBrIngredientLabelQuery();
		tBrIngredientLabelQuery.setJoinIngredientFlg(true);
		tBrIngredientLabelQuery.setIngredientId(ingredientId);
		List<TBrIngredientLabel> list = tBrIngredientLabelService.queryList(tBrIngredientLabelQuery);
		return list;
	}

}