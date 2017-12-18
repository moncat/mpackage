package com.co.example.controller.message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.common.utils.PageReq;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.message.TBrMessage;
import com.co.example.entity.message.aide.TBrMessageQuery;
import com.co.example.service.message.TBrMessageService;

@Controller
@RequestMapping("message")
public class MessageController extends  BaseControllerHandler<TBrMessageQuery> {

	@Autowired
	TBrMessageService tBrMessageService;

	@Override
	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, TBrMessageQuery query) {
		query.setJoinUserFlg(true);
		Page<TBrMessage> page = tBrMessageService.queryPageList(query, pageReq);
		model.addAttribute(QUERY, query);
		model.addAttribute(PAGE, page);
		return true;
	}
	
}
