package com.cntend.beauty.controller.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.constant.HttpStatusCode;
import com.co.example.entity.question.TBrQuestionPlan;
import com.co.example.entity.user.TBrUserPlan;
import com.co.example.entity.user.TBrUserPlanItem;
import com.co.example.entity.user.aide.TBrUserPlanItemQuery;
import com.co.example.service.question.TBrQuestionPlanService;
import com.co.example.service.user.TBrUserPlanItemService;
import com.co.example.service.user.TBrUserPlanService;
import com.co.example.utils.SessionUtil;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("plan")
public class PlanController {
	
	@Autowired
	TBrUserPlanService  tBrUserPlanService;	
	
	@Autowired
	TBrQuestionPlanService  tBrQuestionPlanService;	
	
	@Autowired
	TBrUserPlanItemService  tBrUserPlanItemService;	
	
	@RequestMapping(value="init",method = { RequestMethod.GET})
	public String plan(Model model, HttpSession session) {
		Long userId = SessionUtil.getUserId(session);
		TBrUserPlan tBrUserPlan = tBrUserPlanService.getUserPlan(userId);
		//从未测试，需要跳转到测试
		if(tBrUserPlan == null){
			return "question/init";
		}
		TBrUserPlanItemQuery tBrUserPlanItemQuery = new TBrUserPlanItemQuery();
		tBrUserPlanItemQuery.setUserPlanId(tBrUserPlan.getId());
		List<TBrUserPlanItem> planItem = tBrUserPlanItemService.queryList(tBrUserPlanItemQuery);
		model.addAttribute("planItem", planItem);
		return "plan/init";
	}
	
	@ResponseBody
	@RequestMapping(value="flag",method = { RequestMethod.POST})
	public Map<String, Object> flag(Model model, HttpSession session) {
		Map<String, Object> map = Maps.newHashMap();
		//获得用户计划
		Long userId = SessionUtil.getUserId(session);
		TBrUserPlan tBrUserPlan = tBrUserPlanService.getUserPlan(userId);
		if(tBrUserPlan == null){
			map.put("code", HttpStatusCode.CODE_ERROR);
			map.put("desc", "请先进行肤质测试");
			return map;
		}else{
			map.put("code", HttpStatusCode.CODE_SUCCESS);
		}
		return map;
	}
	
	
	@ResponseBody
	@RequestMapping(value="index",method = { RequestMethod.POST})
	public Map<String, Object> indexPlan(Model model, HttpSession session) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("code", HttpStatusCode.CODE_ERROR);
		//获得用户计划
		Long userId = SessionUtil.getUserId(session);
		TBrUserPlan tBrUserPlan = tBrUserPlanService.getUserPlan(userId);
		if(tBrUserPlan == null){
			return map;
		}
		//获得用户计划类目
		TBrUserPlanItemQuery tBrUserPlanItemQuery = new TBrUserPlanItemQuery();
		tBrUserPlanItemQuery.setUserPlanId(tBrUserPlan.getId());
		List<TBrUserPlanItem> planItem = tBrUserPlanItemService.queryList(tBrUserPlanItemQuery);
		if(4 == planItem.size()){
			map.put("code", HttpStatusCode.CODE_SUCCESS);
			//组合首页描述信息
			String desc ="";
			String flags ="";
			String typeNames ="";
			for (TBrUserPlanItem tBrUserPlanItem : planItem) {
				flags += tBrUserPlanItem.getFlag().toUpperCase();
				typeNames += tBrUserPlanItem.getTypeName()+".";
			}
			desc=flags+"("+typeNames+")";
			map.put("desc", desc);
		}
		return map;
	}
	
	@RequestMapping(value="detail",method = { RequestMethod.GET})
	public String detail(Model model, HttpSession session) {
		Long userId = SessionUtil.getUserId(session);
		TBrUserPlan tBrUserPlan = tBrUserPlanService.getUserPlan(userId);
		TBrQuestionPlan plan = tBrQuestionPlanService.queryById(tBrUserPlan.getPlanId());
		//本次测试方案
		TBrUserPlanItemQuery tBrUserPlanItemQuery = new TBrUserPlanItemQuery();
		tBrUserPlanItemQuery.setUserPlanId(tBrUserPlan.getId());
		List<TBrUserPlanItem> planItem = tBrUserPlanItemService.queryList(tBrUserPlanItemQuery);
		model.addAttribute("plan", plan);
		model.addAttribute("planItem", planItem);
		return "plan/detail";
	}
	
	@ResponseBody
	@RequestMapping(value="chart",method = { RequestMethod.POST})
	public Map<String, Object> chart(Model model, HttpSession session) {
		Map<String, Object> map = Maps.newHashMap();
		Long userId = SessionUtil.getUserId(session);
		TBrUserPlan tBrUserPlan = tBrUserPlanService.getUserPlan(userId);
		TBrUserPlan lastUserPlan = tBrUserPlanService.getLastUserPlan(userId);
		
		TBrUserPlanItemQuery tBrUserPlanItemQuery = new TBrUserPlanItemQuery();
		Float element = 0f;
		List<Float> arrayNow = Lists.newArrayList();
		List<Float> arrayOld = Lists.newArrayList();
		//本次测试方案
		tBrUserPlanItemQuery.setUserPlanId(tBrUserPlan.getId());
		List<TBrUserPlanItem> planItem = tBrUserPlanItemService.queryList(tBrUserPlanItemQuery);
		for (TBrUserPlanItem tBrUserPlanItem : planItem) {
			element = tBrUserPlanItem.getGradeCount();
			arrayNow.add(element);
		}
		//上次测试方案
		tBrUserPlanItemQuery.setUserPlanId(lastUserPlan.getId());
		List<TBrUserPlanItem> lastPlanItem = tBrUserPlanItemService.queryList(tBrUserPlanItemQuery);
		for (TBrUserPlanItem tBrUserPlanItem2 : lastPlanItem) {
			element = tBrUserPlanItem2.getGradeCount();
			arrayOld.add(element);
		}
		map.put("now", arrayNow);
		map.put("old", arrayOld);
		return map;
	}
	
	
	
}


