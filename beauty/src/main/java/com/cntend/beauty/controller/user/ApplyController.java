package com.cntend.beauty.controller.user;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.utils.PageReq;
import com.co.example.constant.HttpStatusCode;
import com.co.example.entity.activity.TBrActivity;
import com.co.example.entity.user.TBrUserAddress;
import com.co.example.entity.user.TBrUserApply;
import com.co.example.entity.user.TUser;
import com.co.example.entity.user.aide.TBrUserAddressQuery;
import com.co.example.entity.user.aide.TBrUserApplyQuery;
import com.co.example.service.activity.TBrActivityService;
import com.co.example.service.user.TBrUserAddressService;
import com.co.example.service.user.TBrUserApplyService;
import com.co.example.service.user.TBrUserPlanItemService;
import com.co.example.service.user.TBrUserPlanService;
import com.co.example.service.user.TUserService;
import com.co.example.utils.BaseDataUtil;
import com.co.example.utils.SessionUtil;
import com.google.common.collect.Maps;

import lombok.SneakyThrows;

@Controller
@RequestMapping("apply")
public class ApplyController {
	
	
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
	
	
	// 试用申请二级页面 list

	// 试用活动填写页面
	@SneakyThrows(Exception.class)
	@RequestMapping(value = "/init/{id}", method = { RequestMethod.GET })
	public String applyInit(Model model, HttpSession session, @PathVariable Long id) throws Exception {
		TBrActivity activity = tBrActivityService.queryById(id);
		TUser user = SessionUtil.getUser(session);
		Long userId = user.getId();
		TUser tUser = tUserService.queryById(userId);
		TBrUserAddressQuery tBrUserAddressQuery = new TBrUserAddressQuery();
		tBrUserAddressQuery.setUid(userId);
		List<TBrUserAddress> addressList = tBrUserAddressService.queryList(tBrUserAddressQuery);
		
		model.addAttribute("activity", activity);
		model.addAttribute("user", tUser);
		model.addAttribute("addressList", addressList);
		
//		TBrUserPlan userPlan = tBrUserPlanService.getUserPlan(userId);
//		if(userPlan !=null){
//			TBrUserPlanItemQuery tBrUserPlanItemQuery = new TBrUserPlanItemQuery();
//			tBrUserPlanItemQuery.setUserPlanId(userPlan.getId());
//			List<TBrUserPlanItem> planItems = tBrUserPlanItemService.queryList(tBrUserPlanItemQuery);
//			model.addAttribute("userPlan", userPlan);
//			model.addAttribute("planItems", planItems);
//		}


		return "apply/init";
	}

	// apply ajax 用户申请
	@ResponseBody
	@RequestMapping(value = "/act", method = { RequestMethod.POST })
	public HashMap<String, Object> act(Model model, HttpSession session,TUser tUser,Long addressId,Long activityId) {
		TBrUserApplyQuery tBrUserApplyQuery = new TBrUserApplyQuery();
		Long userId = SessionUtil.getUserId(session);
		tBrUserApplyQuery.setCreateBy(userId);
		tBrUserApplyQuery.setActivityId(activityId);
		long queryCount = tBrUserApplyService.queryCount(tBrUserApplyQuery);
		HashMap<String, Object> map = Maps.newHashMap();
		if(queryCount==0){
			//保存用户基本信息
			tUser.setId(userId);
			tUserService.updateByIdSelective(tUser);
			//设置默认收货地址
			tBrUserAddressService.setDefaultAddress(userId, addressId);
			
			TBrUserApply tBrUserApply = new TBrUserApply();
			tBrUserApply.setActivityId(activityId);
			tBrUserApply.setCreateBy(userId);
			BaseDataUtil.setDefaultData(tBrUserApply);
			tBrUserApplyService.add(tBrUserApply);
			map.put("code", HttpStatusCode.CODE_SUCCESS);
		}else{
			map.put("code", HttpStatusCode.CODE_ERROR);
			
		}
		return map;
	}

	
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model, HttpSession session, PageReq pageReq) {
		return "apply/list";
	}
	
	@ResponseBody
	@RequestMapping(value = "/listAjax", method = { RequestMethod.POST })
	public HashMap<String, Object> listAjax(Model model, HttpSession session, PageReq pageReq,Byte type) {
		HashMap<String, Object> map = Maps.newHashMap();
		TBrUserApplyQuery tBrUserApplyQuery = new TBrUserApplyQuery();
		Long userId = SessionUtil.getUserId(session);
		tBrUserApplyQuery.setIsActive(type);
		tBrUserApplyQuery.setCreateBy(userId);
		tBrUserApplyQuery.setJoinActivity(true);
		Page<TBrUserApply> page = tBrUserApplyService.queryPageList(tBrUserApplyQuery, pageReq);
		map.put("code", HttpStatusCode.CODE_SUCCESS);
		map.put("page", page);
		return map;
	}
	
	
}







