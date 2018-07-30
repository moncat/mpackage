package com.cntend.beauty.controller.question;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.co.example.entity.question.TBrQuestionOption;
import com.co.example.entity.question.TBrQuestionType;
import com.co.example.entity.question.aide.TBrQuestionTypeQuery;
import com.co.example.entity.question.aide.TBrQuestionTypeVo;
import com.co.example.entity.question.aide.TBrQuestionVo;
import com.co.example.entity.user.TUser;
import com.co.example.service.question.TBrQuestionOptionService;
import com.co.example.service.question.TBrQuestionPlanService;
import com.co.example.service.question.TBrQuestionService;
import com.co.example.service.question.TBrQuestionTypeService;
import com.co.example.service.user.TBrUserPlanService;
import com.co.example.utils.ClientUtil;
import com.co.example.utils.SessionUtil;
import com.google.common.collect.Lists;

@Controller
@RequestMapping("question")
public class QuestionController  {

	@Resource
	TBrQuestionService tBrQuestionService;
	@Resource
	TBrQuestionOptionService tBrQuestionOptionService;
	@Resource
	TBrQuestionTypeService tBrQuestionTypeService;
	@Resource
	TBrQuestionPlanService tBrQuestionPlanService;
	@Resource
	TBrUserPlanService tBrUserPlanService;
	
	
	@RequestMapping(value="/init",method = { RequestMethod.GET})
	public String init(Model model,HttpSession session){
		//从前台读取session storage 数据
		return "question/init";
	}
	
	
	//每套测试题
	@RequestMapping(value="/topic",method = { RequestMethod.GET})
	public String topic(Model model,HttpSession session,Byte type){
		List<TBrQuestionVo>  questionList= tBrQuestionService.getTopicByType(type);
		model.addAttribute("list", questionList);
		model.addAttribute("typeText", getTextByType(type));
		return "question/topic";
	}
	
	private Object getTextByType(Byte type) {
		if(type ==1){
			return "干油性测试";
		}
		if(type ==2){
			return "敏感耐受性测试";
		}
		if(type ==3){
			return "色素沉着性测试";
		}
		if(type ==4){
			return "皮肤紧致性测试";
		}
		return null;
	}


	@ResponseBody
	@RequestMapping(value="answer",method = { RequestMethod.POST})
	public TBrQuestionType answer(Model model, HttpSession session,Long pid ,@RequestParam(value = "ids[]") Long[] ids ) {
		Float gradeCount = 0f;
		TBrQuestionOption option = null;
		for (Long optionId : ids) {
			option = tBrQuestionOptionService.queryById(optionId);
			gradeCount+=option.getGrade();
		}
		
		TBrQuestionTypeQuery tBrQuestionTypeQuery = new TBrQuestionTypeQuery();
		tBrQuestionTypeQuery.setParentId(pid);
		tBrQuestionTypeQuery.setTypeLevel(2);
		tBrQuestionTypeQuery.setGradeCount(gradeCount);
		TBrQuestionType one = tBrQuestionTypeService.queryOne(tBrQuestionTypeQuery);
		TBrQuestionTypeVo  vo= (TBrQuestionTypeVo) one;
		vo.setGradeCount(gradeCount);
		return one;
	}
	
	@ResponseBody
	@RequestMapping(value="plan",method = { RequestMethod.POST})
	public Integer plan(Model model, HttpServletRequest request,HttpSession session, String arr) {
		List<TBrQuestionTypeVo> beanList = Lists.newArrayList();
		 @SuppressWarnings("unchecked")
		ArrayList<String> list = JSON.parseObject(arr, ArrayList.class);  
		 for (String str : list) {
			 TBrQuestionTypeVo vo = JSON.parseObject(str, TBrQuestionTypeVo.class);  
			 beanList.add(vo);
		}
		String ip = ClientUtil.getIp(request);
		TUser user = SessionUtil.getUser(session);
		tBrUserPlanService.saveUserPlan(ip,user, beanList);
		return 200;
	}
	
	
	
	
	
}
