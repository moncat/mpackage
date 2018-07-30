package com.co.example.service.user.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.co.example.common.utils.PageReq;
import com.co.example.dao.user.TBrUserPlanDao;
import com.co.example.entity.question.TBrQuestionPlan;
import com.co.example.entity.question.TBrQuestionTypeLabel;
import com.co.example.entity.question.aide.TBrQuestionPlanQuery;
import com.co.example.entity.question.aide.TBrQuestionTypeLabelQuery;
import com.co.example.entity.question.aide.TBrQuestionTypeLabelVo;
import com.co.example.entity.question.aide.TBrQuestionTypeVo;
import com.co.example.entity.user.TBrUserPlan;
import com.co.example.entity.user.TBrUserPlanItem;
import com.co.example.entity.user.TBrUserPlanLabel;
import com.co.example.entity.user.TUser;
import com.co.example.entity.user.aide.TBrUserPlanLabelQuery;
import com.co.example.entity.user.aide.TBrUserPlanQuery;
import com.co.example.entity.user.aide.TBrUserStatisticsConstant;
import com.co.example.service.log.TBrLogUserService;
import com.co.example.service.question.TBrQuestionPlanService;
import com.co.example.service.question.TBrQuestionTypeLabelService;
import com.co.example.service.question.TBrQuestionTypeService;
import com.co.example.service.user.TBrUserPlanItemService;
import com.co.example.service.user.TBrUserPlanLabelService;
import com.co.example.service.user.TBrUserPlanService;
import com.co.example.service.user.TBrUserStatisticsService;
import com.co.example.utils.BaseDataUtil;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Service
public class TBrUserPlanServiceImpl extends BaseServiceImpl<TBrUserPlan, Long> implements TBrUserPlanService {
    @Resource
    private TBrUserPlanDao tBrUserPlanDao;

    @Override
    protected BaseDao<TBrUserPlan, Long> getBaseDao() {
        return tBrUserPlanDao;
    }
    
    @Resource
    TBrQuestionTypeService tBrQuestionTypeService;
    
    @Resource
    TBrQuestionPlanService tBrQuestionPlanService;
    
    @Resource
    TBrUserPlanItemService tBrUserPlanItemService;
    
    @Resource
    TBrQuestionTypeLabelService tBrQuestionTypeLabelService;
    
    @Resource
    TBrUserPlanLabelService tBrUserPlanLabelService;
    
    @Resource
    TBrLogUserService tBrLogUserService;
    
    @Autowired
    TBrUserStatisticsService tBrUserStatisticsService;

	@Override
	public void saveUserPlan(String ip ,TUser user,List<TBrQuestionTypeVo> beanList) {
		String flags = "";
		Long userId = user.getId();
		for (TBrQuestionTypeVo tBrQuestionType : beanList) {
			flags+= tBrQuestionType.getFlag();
		}		
		TBrQuestionPlanQuery TBrQuestionPlanQuery = new TBrQuestionPlanQuery();
		TBrQuestionPlanQuery.setFlags(flags);
		TBrQuestionPlan one = tBrQuestionPlanService.queryOne(TBrQuestionPlanQuery);
		
		//保存该用户的方案
		TBrUserPlan tBrUserPlan = new TBrUserPlan();		
		tBrUserPlan.setPlanId(one.getId());
		tBrUserPlan.setCreateBy(userId);
		tBrUserPlan.setCreateTime(new Date());
		BaseDataUtil.setDefaultData(tBrUserPlan);
		add(tBrUserPlan);
		Long planId = tBrUserPlan.getId();
		//用户的方案类目
		TBrUserPlanItem tBrUserPlanItem  = null;
		List<Long> typeIds = Lists.newArrayList();
		Long typeId = 0l;
		
		for (TBrQuestionTypeVo questionType : beanList) {
			typeId = questionType.getId();
			typeIds.add(typeId);
			TBrQuestionTypeVo vo = (TBrQuestionTypeVo)questionType;
			tBrUserPlanItem = new TBrUserPlanItem();
			tBrUserPlanItem.setUserPlanId(planId);
			tBrUserPlanItem.setTypeId(typeId);
			tBrUserPlanItem.setTypeName(questionType.getName());
			tBrUserPlanItem.setFlag(questionType.getFlag());
			tBrUserPlanItem.setGradeCount(vo.getGradeCount());
			tBrUserPlanItem.setCreateBy(userId);
			BaseDataUtil.setDefaultData(tBrUserPlanItem);
			tBrUserPlanItemService.add(tBrUserPlanItem);
		}		
		//保存用户肤质冗余数据(标签)
		//获得该肤质所需要的标签
		TBrQuestionTypeLabelQuery tBrQuestionTypeLabelQuery = new TBrQuestionTypeLabelQuery();
		tBrQuestionTypeLabelQuery.setTypeIds(typeIds);
		tBrQuestionTypeLabelQuery.setJoinLabelFlg(true);
		List<TBrQuestionTypeLabel> labelList = tBrQuestionTypeLabelService.queryList(tBrQuestionTypeLabelQuery);
		//删除旧标签
		TBrUserPlanLabelQuery queryOld = new TBrUserPlanLabelQuery();
		queryOld.setCreateBy(userId);
		tBrUserPlanLabelService.delete(queryOld);
		//保存这些标签
		ArrayList<TBrUserPlanLabel> userLabelList = Lists.newArrayList();
		TBrUserPlanLabel tBrUserPlanLabel = null;
		HashSet<Long> idSet = Sets.newHashSet();
		
		for (TBrQuestionTypeLabel tBrQuestionTypeLabel : labelList) {
			Long labelId = tBrQuestionTypeLabel.getLabelId();
			if(!idSet.contains(labelId)){
				TBrQuestionTypeLabelVo vo = (TBrQuestionTypeLabelVo) tBrQuestionTypeLabel;
				tBrUserPlanLabel = new TBrUserPlanLabel();
				tBrUserPlanLabel.setLabelId(labelId);
				tBrUserPlanLabel.setName(vo.getName());
				tBrUserPlanLabel.setCreateBy(userId);
				BaseDataUtil.setDefaultData(tBrQuestionTypeLabel);
				userLabelList.add(tBrUserPlanLabel);
				idSet.add(labelId);
			}
		}
		tBrUserPlanLabelService.addInBatch(userLabelList);
				
		//保存测试日志
		tBrLogUserService.addSkinTest(user.getMobilePhone(), ip);
		//统计用户测试次数
		tBrUserStatisticsService.addUserStatistics(userId, TBrUserStatisticsConstant.EXAM);
	}

	@Override
	public TBrUserPlan getUserPlan(Long userId) {
		TBrUserPlan tBrUserPlan = getUserPlan(userId,1);
		return tBrUserPlan;
	}


	@Override
	public TBrUserPlan getLastUserPlan(Long userId) {
		TBrUserPlan tBrUserPlan = getUserPlan(userId,2);
		return tBrUserPlan;
	}

	private TBrUserPlan getUserPlan(Long userId,Integer page) {
		TBrUserPlanQuery tBrUserPlanQuery = new TBrUserPlanQuery();
		tBrUserPlanQuery.setCreateBy(userId);
		PageReq pageReq = new PageReq();
		pageReq.setPageSize(1);
		pageReq.setPage(page);
		pageReq.setSort(new Sort(Direction.DESC,"t.create_time"));
		Page<TBrUserPlan> list = queryPageList(tBrUserPlanQuery, pageReq);
		if(list.getContent().size()==0){
			return null;
		}
		TBrUserPlan tBrUserPlan = list.getContent().get(0);
		return tBrUserPlan;
	}
	
}