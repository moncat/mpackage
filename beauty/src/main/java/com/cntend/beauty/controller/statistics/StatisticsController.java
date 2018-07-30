package com.cntend.beauty.controller.statistics;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.product.aide.TBrAimQuery;
import com.co.example.entity.user.aide.TBrUserStatisticsConstant;
import com.co.example.service.user.TBrUserStatisticsService;
import com.co.example.utils.SessionUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 统计信息
 * @author zyl
 *
 */
@Slf4j
@Controller
@RequestMapping("statistics")
public class StatisticsController extends BaseControllerHandler<TBrAimQuery>{
	
	@Resource
    TBrUserStatisticsService tBrUserStatisticsService;
	
	//用户已登录，并点击了咨询功能   //TODO 放到前台
	@ResponseBody
	@RequestMapping(value = "/consult", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> consult(HttpSession session) {
		Map<String, Object> result = result();
		//统计用户点击咨询次数
		boolean isLogin = SessionUtil.getIsLogin(session);
		if(isLogin){
			userId = SessionUtil.getUserId(session);
			log.info(userId+" 点击了咨询。");
			tBrUserStatisticsService.addUserStatistics(userId, TBrUserStatisticsConstant.CONSULT);
		}
		return result;
	}

	
	
	
}
