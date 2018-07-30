package com.cntend.beauty.controller.user;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.PageReq;
import com.co.example.controller.BaseRestControllerHandler;
import com.co.example.entity.message.aide.TBrMessageQuery;
import com.co.example.service.message.TBrMessageService;
import com.co.example.utils.SessionUtil;

@Controller
@RequestMapping("message")
public class MessageController extends BaseRestControllerHandler<TBrMessageQuery>  {
	
	@Inject
	TBrMessageService tBrMessageService;
	
	@RequestMapping(value = "/init")
	public String init() {
		return "message/init";
	}
	
	
	@Override
	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, TBrMessageQuery query) {
		query.setReceiveBy(SessionUtil.getUserId(session));
		return super.listExt(model, session, request, response, pageReq, query);
	}



	@ResponseBody
	@RequestMapping(value = "/read")
	public Map<String, Object> read(TBrMessageQuery query) {
		query.setIsRead(Constant.YES);
		tBrMessageService.updateByIdSelective(query);
		Map<String, Object> result = result();
		return result;
	}
	
}
