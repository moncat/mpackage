package com.co.example.controller.mall;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.co.example.common.constant.Constant;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.mall.TBrMall;
import com.co.example.entity.mall.aide.TBrMallQuery;
import com.co.example.service.mall.TBrMallService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("mall")
public class MallController extends BaseControllerHandler<TBrMallQuery> {
	
	@Inject
	TBrMallService tBrMallService;
	
	@RequestMapping(value = "/addMore/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String addMore(Model model, HttpSession session, @PathVariable Long id) { 
	TBrMallQuery tBrMallQuery = new TBrMallQuery();
	tBrMallQuery.setProductId(id);
	tBrMallQuery.setDelFlg(Constant.NO);
	List<TBrMall> mallList = tBrMallService.queryList(tBrMallQuery);
	model.addAttribute("mallList", mallList);
	return "mall/addMore";
}
	
}
