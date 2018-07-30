package com.cntend.beauty.controller.index;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.entity.user.TBrUserPlan;
import com.co.example.service.user.TBrUserPlanService;
import com.co.example.utils.SessionUtil;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@Resource
	TBrUserPlanService tBrUserPlanService;
	//首页
	@RequestMapping(value = "/")
	public String index(Model model,HttpSession session) throws Exception{
	
		//获得用户计划
//				Long userId = SessionUtil.getUserId(session);
//				TBrUserPlan tBrUserPlan = tBrUserPlanService.getUserPlan(userId);
		
		
		return "index/index";
	}
	

	
}
