package com.co.example.controller.question;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.constant.Constant;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.label.TBrLabel;
import com.co.example.entity.label.aide.TBrLabelQuery;
import com.co.example.entity.label.aide.TBrLabelVo;
import com.co.example.entity.question.TBrQuestionType;
import com.co.example.entity.question.TBrQuestionTypeLabel;
import com.co.example.entity.question.aide.TBrQuestionTypeLabelQuery;
import com.co.example.entity.question.aide.TBrQuestionTypeQuery;
import com.co.example.service.label.TBrLabelService;
import com.co.example.service.question.TBrQuestionTypeLabelService;
import com.co.example.service.question.TBrQuestionTypeService;
import com.co.example.utils.BaseDataUtil;

@Controller
@RequestMapping("questiontype")
public class QuestionTypeController extends  BaseControllerHandler<TBrQuestionTypeQuery> {

	@Resource
	TBrQuestionTypeService tBrQuestionTypeService;
	
	@Resource
	TBrQuestionTypeLabelService tBrQuestionTypeLabelService;
	
	@Resource
	TBrLabelService tBrLabelService;

	@ResponseBody
	@RequestMapping(value = "/listMore", method = { RequestMethod.GET,RequestMethod.POST })
	public Object listMore(Model model, HttpSession session, TBrQuestionTypeQuery query) {
		//接收父Id
		List<TBrQuestionType> list = tBrQuestionTypeService.queryList(query);
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/label", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> label(Model model,  Long id) {
		Map<String, Object> result = result();
		TBrQuestionTypeLabelQuery query = new TBrQuestionTypeLabelQuery();
		query.setTypeId(id);
		List<TBrQuestionTypeLabel> tlList = tBrQuestionTypeLabelService.queryList(query);
		TBrLabelQuery tBrLabelQuery = new TBrLabelQuery();
		tBrLabelQuery.setDelFlg(Constant.NO);
		tBrLabelQuery.setAppId(Constant.STATUS_ZERO);
		List<TBrLabel> labelList = tBrLabelService.queryList(tBrLabelQuery);
		Long labelId  = 0l;
		Long labelIdTmp  = 0l;
		for (TBrQuestionTypeLabel tl : tlList) {
			for (TBrLabel tBrLabel : labelList) {
				TBrLabelVo vo =  (TBrLabelVo) tBrLabel;
				labelIdTmp = tl.getLabelId();
				labelId = tBrLabel.getId();
				if(labelId.equals(labelIdTmp)){
					vo.setSelected(true);
				}
			}
		}
		result.put("labelList", labelList);
		return result;
	}


	@RequestMapping(value = "/addInitMore", method = { RequestMethod.GET,RequestMethod.POST })
	public String addInit(Model model, HttpSession session,TBrQuestionTypeQuery query) throws Exception{
		model.addAttribute("parentId", query.getId());
		model.addAttribute("typeLevel", query.getTypeLevel()+1);
		return "questiontype/addInit";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/addLabel", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> addLabel(Model model, HttpSession session, TBrQuestionTypeLabelQuery query) {
		Map<String, Object> result = result();
		BaseDataUtil.setDefaultData(query);
		tBrQuestionTypeLabelService.add(query);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/removeLabel", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> removeLabel(Model model, HttpSession session, TBrQuestionTypeLabelQuery query) {
		Map<String, Object> result = result();
		TBrQuestionTypeLabel one = tBrQuestionTypeLabelService.queryOne(query);
		tBrQuestionTypeLabelService.deleteById(one.getId());
		return result;
	}
	
}
