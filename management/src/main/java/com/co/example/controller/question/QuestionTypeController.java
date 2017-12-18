package com.co.example.controller.question;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.question.TBrQuestionType;
import com.co.example.entity.question.aide.TBrQuestionTypeQuery;
import com.co.example.service.question.TBrQuestionTypeService;

@Controller
@RequestMapping("questiontype")
public class QuestionTypeController extends  BaseControllerHandler<TBrQuestionTypeQuery> {

	@Resource
	TBrQuestionTypeService tBrQuestionTypeService;

	@ResponseBody
	@RequestMapping(value = "/listMore", method = { RequestMethod.GET,RequestMethod.POST })
	public Object listMore(Model model, HttpSession session, TBrQuestionTypeQuery query) {
		//接收父Id
		List<TBrQuestionType> list = tBrQuestionTypeService.queryList(query);
		return list;
	}
	
	@RequestMapping(value = "/addInitMore", method = { RequestMethod.GET,RequestMethod.POST })
	public String addInit(Model model, HttpSession session,TBrQuestionTypeQuery query) throws Exception{
		model.addAttribute("parentId", query.getId());
		model.addAttribute("typeLevel", query.getTypeLevel()+1);
		return "questiontype/addInit";
	}
	
}
