package com.co.example.controller.product;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.PageReq;
import com.co.example.constant.HttpStatusCode;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.label.TBrIngredientLabel;
import com.co.example.entity.label.TBrIngredientLabelClass;
import com.co.example.entity.label.aide.TBrIngredientLabelClassQuery;
import com.co.example.entity.label.aide.TBrIngredientLabelQuery;
import com.co.example.service.label.TBrIngredientLabelClassService;
import com.co.example.service.label.TBrIngredientLabelService;

@Controller
@RequestMapping("ingredientLabel")
public class IngredientLabelController extends BaseControllerHandler<TBrIngredientLabelQuery> {

	@Inject
	TBrIngredientLabelClassService ingredientLabelClassService;

	@Inject
	TBrIngredientLabelService ingredientLabelService;

	@Override
	public void addInitExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		TBrIngredientLabelClassQuery query = new TBrIngredientLabelClassQuery();
		query.setDelFlg(Constant.NO);
		List<TBrIngredientLabelClass> labelClassList = ingredientLabelClassService.queryList(query);
		model.addAttribute("labelClassList", labelClassList);
		
	}

	@Override
	public void editInitExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrIngredientLabelQuery t, Long id) {
		
	}
	
	
	

	@Override
 	public String editInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrIngredientLabelQuery t, @PathVariable Long id, Long pageNumber) throws Exception {
		TBrIngredientLabelClassQuery query = new TBrIngredientLabelClassQuery();
		query.setDelFlg(Constant.NO);
		List<TBrIngredientLabelClass> labelClassList = ingredientLabelClassService.queryList(query);
		TBrIngredientLabel one = ingredientLabelService.queryById(id);
		model.addAttribute("labelClassList", labelClassList);
		model.addAttribute(ONE, one);
		model.addAttribute("pageNumber", pageNumber);
		return  "ingredientLabel/editInit";
		
	 
	}

	@Override
	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, TBrIngredientLabelQuery query) {
		query.setJoinClassFlg(true);
		return false;
	}

	@Override
	public Boolean addExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrIngredientLabelQuery t, PageReq pageReq, Map<String, Object> result) {
		TBrIngredientLabel tBrIngredientLabel = new TBrIngredientLabel();
		tBrIngredientLabel.setName(StringUtils.trim(t.getName()));
		long queryCount = ingredientLabelService.queryCount(tBrIngredientLabel);
		if (queryCount > 0) {
			result.put("code", HttpStatusCode.CODE_ERROR);
			result.put("info", "已有该标签，添加失败。");
		} else {
			ingredientLabelService.add(t);
			result.put("info", "添加成功！");
		}
		return true;
	}

	@ResponseBody
	@RequestMapping(value = "/updateStatus", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String,Object> updateStatus(Model model, Long id,Boolean flg)throws Exception {
		Map<String, Object> result = result();
		TBrIngredientLabel one = new TBrIngredientLabel();
		one.setId(id);
		if(flg){
			one.setIsChoice(Constant.YES);
		}else{
			one.setIsChoice(Constant.NO);		
		}
		ingredientLabelService.updateByIdSelective(one);		
		return result;
	}
	 
}
