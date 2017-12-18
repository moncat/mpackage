package com.co.example.controller.information;

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
import com.co.example.entity.information.TBrInformationComment;
import com.co.example.entity.information.aide.TBrInformationCommentQuery;
import com.co.example.service.information.TBrInformationCommentService;

@Controller
@RequestMapping("comment")
public class CommentController extends  BaseControllerHandler<TBrInformationCommentQuery> {

	@Autowired
	TBrInformationCommentService service;
	
	@Override
	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, TBrInformationCommentQuery query) {
		query.setJoinFlg(true);
		Page<TBrInformationComment> queryPageList = service.queryPageList(query, pageReq);
		model.addAttribute(QUERY, query);
		model.addAttribute(PAGE, queryPageList);
		return true;
	}
	
}
