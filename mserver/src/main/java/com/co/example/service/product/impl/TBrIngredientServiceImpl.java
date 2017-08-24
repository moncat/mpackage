package com.co.example.service.product.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.co.example.dao.product.TBrIngredientDao;
import com.co.example.entity.product.TBrAim;
import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.aide.TBrAimQuery;
import com.co.example.entity.product.aide.TBrIngredientVo;
import com.co.example.service.product.TBrAimService;
import com.co.example.service.product.TBrIngredientService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;

@Service
public class TBrIngredientServiceImpl extends BaseServiceImpl<TBrIngredient, Long> implements TBrIngredientService {
    @Resource
    private TBrIngredientDao tBrIngredientDao;
    
    @Resource
    private TBrAimService tBrAimService;

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
}