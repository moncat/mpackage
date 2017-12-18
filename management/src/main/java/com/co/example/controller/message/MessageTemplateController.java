package com.co.example.controller.message;

import java.util.List;

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
import com.co.example.entity.message.TBrMessageTemplate;
import com.co.example.entity.message.TBrMessageType;
import com.co.example.service.message.TBrMessageTemplateService;
import com.co.example.service.message.TBrMessageTypeService;

@Controller
@RequestMapping("messagetmp")
public class MessageTemplateController extends  BaseControllerHandler<TBrMessageTemplate> {

	@Autowired
	TBrMessageTemplateService tBrMessageTemplateService;
	
	@Autowired
	TBrMessageTypeService tBrMessageTypeService;

	@Override
	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, TBrMessageTemplate query) {
		Page<TBrMessageTemplate> page = tBrMessageTemplateService.queryPageList(query, pageReq);
		model.addAttribute(QUERY, query);
		model.addAttribute(PAGE, page);
		return true;
	}

	@Override
	public void addInitExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		List<TBrMessageType> types = tBrMessageTypeService.queryList();
		model.addAttribute("types", types);
		super.addInitExt(model, session, request, response);
	}

	@Override
	public void editInitExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrMessageTemplate t, Long id) {
		List<TBrMessageType> types = tBrMessageTypeService.queryList();
		model.addAttribute("types", types);
		super.editInitExt(model, session, request, response, t, id);
	}


	
}
