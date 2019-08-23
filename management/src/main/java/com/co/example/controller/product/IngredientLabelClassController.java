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
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.PageReq;
import com.co.example.constant.HttpStatusCode;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.label.TBrIngredientLabelClass;
import com.co.example.entity.label.TBrLabelClass;
import com.co.example.entity.label.aide.TBrIngredientLabelClassQuery;
import com.co.example.entity.label.aide.TBrLabelClassQuery;
import com.co.example.service.label.TBrIngredientLabelClassService;

@Controller
@RequestMapping("ingredientLabelClass")
public class IngredientLabelClassController extends BaseControllerHandler<TBrIngredientLabelClassQuery> {

	@Inject
	TBrIngredientLabelClassService ingredientLabelClassService;
	
	
	@Override
	public Boolean addExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrIngredientLabelClassQuery t, PageReq pageReq, Map<String, Object> result) {
		TBrIngredientLabelClass tBrIngredientLabelClass = new TBrIngredientLabelClass();
		tBrIngredientLabelClass.setName(StringUtils.trim(t.getName()));
		long queryCount = ingredientLabelClassService.queryCount(tBrIngredientLabelClass);
		if(queryCount>0){
			result.put("code", HttpStatusCode.CODE_ERROR);
			result.put("info", "已有该分类，添加失败。");
		}else{
			ingredientLabelClassService.add(t);
			result.put("info", "添加成功！");
		}
		return true;
	}


	

}
