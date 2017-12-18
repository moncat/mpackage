package com.co.example.controller.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.common.utils.PageReq;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.user.TBrUserCollect;
import com.co.example.entity.user.aide.TBrUserCollectQuery;
import com.co.example.service.user.TBrUserCollectService;

@Controller
@RequestMapping("collect")
public class CollectController extends BaseControllerHandler<TBrUserCollectQuery> {

	@Resource
	TBrUserCollectService tBrUserCollectService;
	
	@Override
	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, TBrUserCollectQuery query) {
		query.setJoinFlg(true);
		Page<TBrUserCollect> page = tBrUserCollectService.queryPageList(query, pageReq);
		model.addAttribute(QUERY, query);
		model.addAttribute(PAGE, page);
		return true;
	}

	
}

