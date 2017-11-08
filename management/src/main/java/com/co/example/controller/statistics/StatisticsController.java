package com.co.example.controller.statistics;

import java.util.Map;

import javax.annotation.Resource;

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
import com.co.example.service.statistics.TBrStatisticsService;

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
	
	
	
}
