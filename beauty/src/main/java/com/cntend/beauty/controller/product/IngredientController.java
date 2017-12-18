package com.cntend.beauty.controller.product;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.utils.PageReq;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.aide.TBrIngredientQuery;
import com.co.example.service.product.TBrIngredientService;

@Controller
@RequestMapping("ingredient")
public class IngredientController extends BaseControllerHandler<TBrIngredientQuery> {
	

	@Inject
	TBrIngredientService tBrIngredientService;
	
	//成分列表界面
	@Override
	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, TBrIngredientQuery query) {
		pageReq.setPageSize(10);
		Page<TBrIngredient> page = tBrIngredientService.queryPageList(query, pageReq);
		//装饰成分信息
		tBrIngredientService.decorateColour(page.getContent());
		model.addAttribute(QUERY, query);
		model.addAttribute(PAGE, page);
		return true;
	}


	//成分分析  安全  较安全 危险
	@ResponseBody
	@RequestMapping(value="ingredientAnalyze")
	public Map<String,Integer> ingredientAnalyze(Long productId){
		return  tBrIngredientService.ingredientAnalyze(productId);
	}
	
	@ResponseBody
	@RequestMapping(value="safeAnalyze")
	public Map<String,Integer> safeAnalyze(Long productId){
		return  tBrIngredientService.safeAnalyze(productId);
	}
	
	@ResponseBody
	@RequestMapping(value="effectAnalyze")
	public Map<String,Integer> effectAnalyze(Long productId){
		return  tBrIngredientService.effectAnalyze(productId);
	}
	
	
	
	
	
}









