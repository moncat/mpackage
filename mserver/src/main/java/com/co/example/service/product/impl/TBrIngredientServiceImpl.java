package com.co.example.service.product.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.co.example.dao.product.TBrIngredientDao;
import com.co.example.entity.product.TBrAim;
import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.aide.TBrAimQuery;
import com.co.example.entity.product.aide.TBrIngredientQuery;
import com.co.example.entity.product.aide.TBrIngredientVo;
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
    private TBrIngredientService tBrIngredientService;

    @Override
    protected BaseDao<TBrIngredient, Long> getBaseDao() {
        return tBrIngredientDao;
    }

	@Override
	public void decorateColour(List<TBrIngredient> ingredientList) {
		ingredientList.forEach(ingredient->{
			TBrIngredientVo ingredientVo = (TBrIngredientVo)ingredient;
			ingredientVo.setSafeColour("badge-success");
			String securityRisks = ingredientVo.getSecurityRisks();
			if(StringUtils.isNoneBlank(securityRisks)){
				if(securityRisks.indexOf("-")>0){
					securityRisks = securityRisks.substring(0,securityRisks.lastIndexOf("-"));
				}
				int safeInt = Integer.parseInt(securityRisks);
				if(safeInt>=3 && safeInt<=6){
					ingredientVo.setSafeColour("badge-warning");
				}
				if(safeInt > 6){
					ingredientVo.setSafeColour("badge-danger");
				}
			}
			
			getAims(ingredientVo);
		});
	}

	@Override
	public void  getAims(TBrIngredientVo ingredientVo) {
		TBrAimQuery tBrAimQuery = new TBrAimQuery();
		tBrAimQuery.setIngredientId(ingredientVo.getId());
		tBrAimQuery.setJoinFlg(true);
		List<TBrAim> tBrAims = tBrAimService.queryList(tBrAimQuery);
		ingredientVo.setTBrAims(tBrAims);
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
			if(StringUtils.isNoneBlank(securityRisks)){
				if(securityRisks.indexOf("-")>0){
					securityRisks = securityRisks.substring(0,securityRisks.lastIndexOf("-"));
				}
				safeInt = Integer.parseInt(securityRisks);
				
				if(safeInt<3){
					layer1++;
				}else if(safeInt>=3 && safeInt<=6){
					layer2++;
				}else if(safeInt > 6){
					layer3++;
				}
			}
		}
		map.put("layer1", layer1);
		map.put("layer2", layer2);
		map.put("layer3", layer3);
		return map;
	}

	private static final String[] SAFE_TYPE = {"香精","防腐"};
	private static final String[] EFFECT_TYPE = {"美白","保湿"};
	
	
	
	@Override
	public List<Map<String, Object>>safeAnalyze(Long productId) {
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
		List<TBrIngredient> ingredientList = tBrIngredientService.queryList(tBrIngredientQuery);
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
					if(name.contains(strs[i])){
						num++;
					}
				}
			}
			if(num>0){
				map.put("key", strs[i]);
				map.put("value", num);
				list.add(map);
			}
		}
		return list;
	}

	

	
	
	
	
	
	
}