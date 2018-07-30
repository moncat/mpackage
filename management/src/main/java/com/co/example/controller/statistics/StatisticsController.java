package com.co.example.controller.statistics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.utils.PageReq;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.product.aide.TBrAimQuery;
import com.co.example.entity.statistics.TBrStatistics;
import com.co.example.entity.statistics.aide.TBrStatisticsQuery;
import com.co.example.entity.user.TBrUserStatisticsMonth;
import com.co.example.entity.user.aide.TBrUserStatisticsConstant;
import com.co.example.entity.user.aide.TBrUserStatisticsMonthQuery;
import com.co.example.entity.user.aide.TBrUserStatisticsQuery;
import com.co.example.service.statistics.TBrStatisticsService;
import com.co.example.service.user.TBrUserStatisticsMonthService;
import com.co.example.service.user.TBrUserStatisticsService;
import com.co.example.utils.SessionUtil;
import com.google.common.collect.Lists;

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
	TBrStatisticsService tBrStatisticsService;
	
	@Resource
	TBrUserStatisticsService uss;
	
	@Resource
	TBrUserStatisticsMonthService usms;   
	
	@Resource
    TBrUserStatisticsService tBrUserStatisticsService;
	
	/**
	 * 产品基础数据统计
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/show", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> show() {
		Map<String, Object> result = result();
		PageReq pageReq = new PageReq();
		pageReq.setPageSize(1);
		pageReq.setPage(0);
		pageReq.setSort(new Sort(Direction.DESC,"t.create_time"));
		Page<TBrStatistics> list = tBrStatisticsService.queryPageList(new TBrStatisticsQuery(), pageReq);
		result.put("statistics", list.getContent().get(0));
		return result;
	}
	
	
	/**
	 * 用户数据统计   总用户数，肤质测试数，用户咨询量
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> user() {
		Map<String, Object> result = result();
		//总数
		TBrUserStatisticsQuery query = new TBrUserStatisticsQuery();
		query.setType(TBrUserStatisticsConstant.REGISTER);
		long registerCount = uss.queryCount(query);
		query.setType(TBrUserStatisticsConstant.EXAM);
		long examCount = uss.queryCount(query);
		query.setType(TBrUserStatisticsConstant.CONSULT);
		long consultCount = uss.queryCount(query);
		//今天
		query.setType(TBrUserStatisticsConstant.REGISTER);
		query.setSmallTime(getDateByCalendar());
		long registerCountToday = uss.queryCount(query);
		query.setType(TBrUserStatisticsConstant.EXAM);
		long examCountToday = uss.queryCount(query);
		query.setType(TBrUserStatisticsConstant.CONSULT);
		long consultCountToday = uss.queryCount(query);
		
		result.put("registerCount",registerCount);
		result.put("examCount",examCount);
		result.put("consultCount",consultCount);
		result.put("registerCountToday",registerCountToday);
		result.put("examCountToday",examCountToday);
		result.put("consultCountToday",consultCountToday);
		
		return result;
	}
	
	//按月统计
	@ResponseBody
	@RequestMapping(value = "/month", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> month() {
		Map<String, Object> result = result();
		result.put("categories",getMonthLabel(TBrUserStatisticsConstant.REGISTER));
		result.put("register",getMonthData(TBrUserStatisticsConstant.REGISTER));
		result.put("exam",getMonthData(TBrUserStatisticsConstant.EXAM));
		result.put("consult",getMonthData(TBrUserStatisticsConstant.CONSULT));
		return result;
	}
	
	private ArrayList<String> getMonthLabel(Integer type) {
		ArrayList<String> categories = Lists.newArrayList();	
		TBrUserStatisticsMonthQuery tBrUserStatisticsMonthQuery = new TBrUserStatisticsMonthQuery();
		tBrUserStatisticsMonthQuery.setType(type);
		List<TBrUserStatisticsMonth> registerList = usms.queryList(tBrUserStatisticsMonthQuery);
		for (TBrUserStatisticsMonth tBrUserStatisticsMonth : registerList) {
			categories.add(tBrUserStatisticsMonth.getMonth());
		}		
		return categories;
	}
	
	private ArrayList<Integer> getMonthData(Integer type) {
		ArrayList<Integer> trueData = Lists.newArrayList();		
		TBrUserStatisticsMonthQuery tBrUserStatisticsMonthQuery = new TBrUserStatisticsMonthQuery();
		tBrUserStatisticsMonthQuery.setType(type);
		List<TBrUserStatisticsMonth> registerList = usms.queryList(tBrUserStatisticsMonthQuery);
		for (TBrUserStatisticsMonth tBrUserStatisticsMonth : registerList) {
			trueData.add(tBrUserStatisticsMonth.getCount());
		}		
		return trueData;
	}
	
	Date getDateByCalendar(){
		  Calendar calendar = Calendar.getInstance();
          calendar.setTime(new Date());
          calendar.set(Calendar.HOUR_OF_DAY, 0);
          calendar.set(Calendar.MINUTE, 0);
          calendar.set(Calendar.SECOND, 0);
          Date date = calendar.getTime();
          return date;
	}
	//产品相关数据统计定时器
	@Scheduled(cron="00 00 01 * * ? ")  //每天01:00分执行一次
	public void addData() { 
		log.info("***数据统计定时器开始***");
		long startMs = System.currentTimeMillis();
		tBrStatisticsService.addData();
		long endMs = System.currentTimeMillis();
		Long interval=endMs-startMs;
		Long second = interval/1000;
		log.info("***数据统计定时器结束***用时(秒)***"+second);
	}
	
	//用户月数据统计定时器
	@Scheduled(cron="1 0 0 1 * ? ")  //每月1日00:00:01执行一次
	public void addUserData() { 
		log.info("***addUserData数据统计定时器开始***");
		long startMs = System.currentTimeMillis();
		tBrStatisticsService.addUserData();
		long endMs = System.currentTimeMillis();
		Long interval=endMs-startMs;
		Long second = interval/1000;
		log.info("***addUserData数据统计定时器结束***用时(秒)***"+second);
	}
	
	
	
	
}
