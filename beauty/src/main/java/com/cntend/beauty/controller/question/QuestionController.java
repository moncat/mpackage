package com.cntend.beauty.controller.question;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.question.TBrQuestionPlan;
import com.co.example.entity.question.TBrQuestionType;
import com.co.example.entity.question.aide.TBrQuestionPlanQuery;
import com.co.example.entity.question.aide.TBrQuestionQuery;
import com.co.example.entity.question.aide.TBrQuestionTypeQuery;
import com.co.example.service.question.TBrQuestionOptionService;
import com.co.example.service.question.TBrQuestionPlanService;
import com.co.example.service.question.TBrQuestionService;
import com.co.example.service.question.TBrQuestionTypeService;

@Controller
@RequestMapping("question")
public class QuestionController extends  BaseControllerHandler<TBrQuestionQuery> {

	@Resource
	TBrQuestionService tBrQuestionService;
	@Resource
	TBrQuestionOptionService tBrQuestionOptionService;
	@Resource
	TBrQuestionTypeService tBrQuestionTypeService;
	@Resource
	TBrQuestionPlanService tBrQuestionPlanService;
	
	
	@ResponseBody
	@RequestMapping(value="answer")
	public TBrQuestionType answer(Model model, HttpSession session, String flag ,Float gradeCount) {
		TBrQuestionTypeQuery tBrQuestionTypeQuery = new TBrQuestionTypeQuery();
		tBrQuestionTypeQuery.setFlag(flag);
		tBrQuestionTypeQuery.setTypeLevel(3);
		tBrQuestionTypeQuery.setGradeCount(gradeCount);
		TBrQuestionType one = tBrQuestionTypeService.queryOne(tBrQuestionTypeQuery);
		return one;
	}
	
	@ResponseBody
	@RequestMapping(value="plan")
	public TBrQuestionPlan plan(Model model, HttpSession session, String flags) {
		TBrQuestionPlanQuery TBrQuestionPlanQuery = new TBrQuestionPlanQuery();
		TBrQuestionPlanQuery.setFlags(flags);
		TBrQuestionPlan one = tBrQuestionPlanService.queryOne(TBrQuestionPlanQuery);
		return one;
	}
	
	
	
	
	
}
