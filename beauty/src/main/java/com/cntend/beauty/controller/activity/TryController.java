package com.cntend.beauty.controller.activity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.PageReq;
import com.co.example.entity.activity.TBrActivity;
import com.co.example.entity.activity.aide.TBrActivityQuery;
import com.co.example.entity.user.TBrUserApply;
import com.co.example.entity.user.aide.TBrUserApplyQuery;
import com.co.example.service.activity.TBrActivityService;
import com.co.example.service.user.TBrUserApplyService;
import com.co.example.utils.SessionUtil;
import com.google.common.collect.Maps;

import lombok.SneakyThrows;

@Controller
@RequestMapping("try")
public class TryController {

	@Autowired
	TBrActivityService tBrActivityService;

	@Autowired
	TBrUserApplyService tBrUserApplyService;


	// 试用活动  查看列表 必须登录，根据业务需求，是否改变
	@SneakyThrows(Exception.class)
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model, HttpSession session, PageReq pageReq, TBrActivityQuery query) throws Exception {
		pageReq.setSort(new Sort(Direction.DESC, "t.item_order").and(new Sort(Direction.DESC, "t.create_time")));
		query.setIsActive(Constant.YES);
		query.setDelFlg(Constant.NO);
		//1试用活动
		query.setType(1);
		Page<TBrActivity> page = tBrActivityService.queryPageList(query, pageReq);
		model.addAttribute("page", page);
		
		Long userId = SessionUtil.getUserId(session);
		TBrUserApplyQuery tBrUserApplyQuery = new TBrUserApplyQuery();
		tBrUserApplyQuery.setCreateBy(userId);
		List<TBrUserApply> applyList = tBrUserApplyService.queryList(tBrUserApplyQuery);
		HashMap<Long, TBrUserApply> map = Maps.newHashMap();
		for (TBrUserApply tBrUserApply : applyList) {
			map.put(tBrUserApply.getActivityId(), tBrUserApply);
		}
		model.addAttribute("map", map);
		
		
		return "try/list";
	}

	// 活动详情
	// 试用活动
	@SneakyThrows(Exception.class)
	@RequestMapping(value = "/detail/{id}", method = { RequestMethod.GET })
	public String detail(Model model, HttpSession session, @PathVariable Long id) throws Exception {
		TBrActivity activity = tBrActivityService.queryById(id);
		model.addAttribute("activity", activity);
		Long userId = SessionUtil.getUserId(session);
		Date endTime = activity.getEndTime();
		Date nowTime = new Date();
		// 4活动已结束 5活动可申请 6活动未开始 v 1申请中 2申请成功，填写报告v 0申请失败 3试用完成
		Byte flg = 4;
		
		Date startTime = activity.getStartTime();
		if(startTime.after(nowTime)){
			//活动还没开始
			flg = 6;
			model.addAttribute("flg", flg);
			return "try/detail";
		}
		if (endTime.after(nowTime)) {
			TBrUserApplyQuery tBrUserApplyQuery = new TBrUserApplyQuery();
			tBrUserApplyQuery.setActivityId(id);
			tBrUserApplyQuery.setCreateBy(userId);
			TBrUserApply apply = tBrUserApplyService.queryOne(tBrUserApplyQuery);
			if (apply == null) {
				flg = 5;
			} else {
				flg = apply.getIsActive();
			}
		}

		model.addAttribute("flg", flg);
		return "try/detail";
	}
	
}
