package com.cntend.beauty.controller.activity;

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
import com.co.example.service.activity.TBrActivityService;
import com.co.example.service.user.TBrUserAddressService;
import com.co.example.service.user.TBrUserApplyService;
import com.co.example.service.user.TBrUserPlanItemService;
import com.co.example.service.user.TBrUserPlanService;
import com.co.example.service.user.TUserService;

import lombok.SneakyThrows;

@Controller
@RequestMapping("normal")
public class normalController {

	@Autowired
	TBrActivityService tBrActivityService;

	@Autowired
	TBrUserApplyService tBrUserApplyService;

	@Autowired
	TBrUserAddressService tBrUserAddressService;

	@Autowired
	TUserService tUserService;

	@Autowired
	TBrUserPlanService tBrUserPlanService;

	@Autowired
	TBrUserPlanItemService tBrUserPlanItemService;

	//其他活动
	@SneakyThrows(Exception.class)
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model, HttpSession session, PageReq pageReq, TBrActivityQuery query) throws Exception {
		pageReq.setSort(new Sort(Direction.DESC, "t.item_order").and(new Sort(Direction.DESC, "t.create_time")));
		query.setIsActive(Constant.YES);
		query.setDelFlg(Constant.NO);
		//2其他 活动
		query.setType(2);
		Page<TBrActivity> page = tBrActivityService.queryPageList(query, pageReq);
		model.addAttribute("page", page);
		return "normal/list";
	}

	// 活动详情
	@SneakyThrows(Exception.class)
	@RequestMapping(value = "/detail/{id}", method = { RequestMethod.GET })
	public String detail(Model model, HttpSession session, @PathVariable Long id) throws Exception {
		TBrActivity activity = tBrActivityService.queryById(id);
		model.addAttribute("activity", activity);
		return "normal/detail";
	}

	
	
}
