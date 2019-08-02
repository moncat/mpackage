package com.co.example.controller.taobao;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.utils.DateFormatUtil;
import com.co.example.service.taobao.TBrTaobaoGoodsService;
import com.co.example.service.taobao.TBrTaobaoSortService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("taobao")
public class TaobaoController {
	
	@Resource
	TBrTaobaoGoodsService tBrTaobaoGoodsService;
	
	
	@Resource
	TBrTaobaoSortService tBrTaobaoSortService;
	

	@ResponseBody
	@RequestMapping(value = "/grapTaobaoSort", method = { RequestMethod.GET, RequestMethod.POST })
	public String grapTaobaoSort() throws InterruptedException {
		tBrTaobaoGoodsService.grapTaobaoSort();
		log.info("grapTaobaoSort运行完毕");
		return DateFormatUtil.getDateTimeNumber();
	}
	
	@ResponseBody
	@RequestMapping(value = "/grapTaobaoGoods", method = { RequestMethod.GET, RequestMethod.POST })
	public String grapTaobaoMei() throws InterruptedException {
		tBrTaobaoGoodsService.grapTaobaoGoods();
		log.info("grapTaobaoGoods运行完毕");
		return DateFormatUtil.getDateTimeNumber();
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/grapTmallSort", method = { RequestMethod.GET, RequestMethod.POST })
	public String grapTmallSort() throws InterruptedException {
		tBrTaobaoGoodsService.grapTmallSort();
		log.info("grapTmallSort运行完毕");
		return DateFormatUtil.getDateTimeNumber();
	}
	
	@ResponseBody
	@RequestMapping(value = "/grapTmallGoods", method = { RequestMethod.GET, RequestMethod.POST })
	public String grapTmallGoods() throws InterruptedException {
		tBrTaobaoGoodsService.grapTmallGoods();
		log.info("grapTmallGoods运行完毕");
		return DateFormatUtil.getDateTimeNumber();
	}
	
	@ResponseBody
	@RequestMapping(value = "/grapTmallGoodsSells", method = { RequestMethod.GET, RequestMethod.POST })
	public String grapTmallGoodsSells() throws InterruptedException {
		tBrTaobaoGoodsService.grapTmallGoodsSells();
		log.info("grapTmallGoodsSells运行完毕");
		return DateFormatUtil.getDateTimeNumber();
	}
}
