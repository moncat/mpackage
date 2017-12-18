package com.co.example.controller.question;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.common.utils.PageReq;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.question.TBrQuestion;
import com.co.example.entity.question.TBrQuestionOption;
import com.co.example.entity.question.aide.QuestionConstant;
import com.co.example.entity.question.aide.TBrQuestionOptionQuery;
import com.co.example.entity.question.aide.TBrQuestionQuery;
import com.co.example.service.question.TBrQuestionOptionService;
import com.co.example.service.question.TBrQuestionService;

@Controller
@RequestMapping("question")
public class QuestionController extends  BaseControllerHandler<TBrQuestionQuery> {

	@Resource
	TBrQuestionService tBrQuestionService;
	@Resource
	TBrQuestionOptionService tBrQuestionOptionService;
	
	
	@Override
	public Boolean addExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrQuestionQuery t, PageReq pageReq, Map<String, Object> result) {
			tBrQuestionService.addData(t);
			return true;
	}

	
	@Override
	public Boolean editExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrQuestionQuery t, PageReq pageReq, Map<String, Object> result) {
		tBrQuestionService.editData(t);
		return true;
	}


	@Override
	public String editInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrQuestionQuery t,@PathVariable Long id,@PathVariable Long pageNumber) throws Exception {
		TBrQuestion question = tBrQuestionService.queryById(id);
		t.setId(question.getId());
		t.setType(question.getType());
		t.setName(question.getName());
		t.setDetail(question.getDetail());
		t.setRemark(question.getRemark());
		TBrQuestionOptionQuery tBrQuestionOptionQuery = new TBrQuestionOptionQuery();
		tBrQuestionOptionQuery.setQuestionId(id);
		List<TBrQuestionOption> optionList = tBrQuestionOptionService.queryList(tBrQuestionOptionQuery);
		int size = optionList.size();
		TBrQuestionOption tBrQuestionOption = null;
		String name = null;
		Float grade = 0f;
		for (int i = 0; i < size; i++) {
			tBrQuestionOption = optionList.get(i);
			name = tBrQuestionOption.getName();
			grade = tBrQuestionOption.getGrade();
			if(Math.abs(grade - QuestionConstant.OPTION_1_GRADE)<=0){
				t.setOptionA(name);
			}
			if(Math.abs(grade - QuestionConstant.OPTION_2_GRADE)<=0){
				t.setOptionB(name);
			}
			if(Math.abs(grade - QuestionConstant.OPTION_3_GRADE)<=0){
				t.setOptionC(name);
			}
			if(Math.abs(grade - QuestionConstant.OPTION_4_GRADE)<=0){
				t.setOptionD(name);
			}
			if(Math.abs(grade - QuestionConstant.OPTION_2P5_GRADE)<=0){
				t.setOptionE(name);
			}
			if(Math.abs(grade - QuestionConstant.OPTION_5_GRADE)<=0){
				t.setOptionF(name);
			}
		}
		
		model.addAttribute(ONE, t);
		model.addAttribute("pageNumber", pageNumber);
		return "question/editInit";
	}



	
	
	
	
}
