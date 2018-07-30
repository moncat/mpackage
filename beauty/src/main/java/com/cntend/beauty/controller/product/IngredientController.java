package com.cntend.beauty.controller.product;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.aide.TBrIngredientQuery;
import com.co.example.entity.product.aide.TBrIngredientVo;
import com.co.example.service.product.TBrIngredientService;

@Controller
@RequestMapping("ingredient")
public class IngredientController extends BaseControllerHandler<TBrIngredientQuery> {
	

	@Inject
	TBrIngredientService tBrIngredientService;
	
	//成分列表界面   暂时无展示全部成分的需求
//	@Override
//	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
//			PageReq pageReq, TBrIngredientQuery query) {
//		pageReq.setPageSize(10);
//		Page<TBrIngredient> page = tBrIngredientService.queryPageList(query, pageReq);
//		//装饰成分信息
//		tBrIngredientService.decorateColour(page.getContent());
//		model.addAttribute(QUERY, query);
//		model.addAttribute(PAGE, page);
//		return true;
//	}


	//成分分析  安全  较安全 危险  百分比在前台算？
	@ResponseBody
	@RequestMapping(value="ingredientAnalyze")
	public Map<String,Integer> ingredientAnalyze(Long productId){
		return  tBrIngredientService.ingredientAnalyze(productId);
	}
	
	@ResponseBody
	@RequestMapping(value="safeAnalyze")
	public List<Map<String, Object>> safeAnalyze(Long productId){
		return  tBrIngredientService.safeAnalyze(productId);
	}
	
	@ResponseBody
	@RequestMapping(value="effectAnalyze")
	public List<Map<String, Object>> effectAnalyze(Long productId){
		return  tBrIngredientService.effectAnalyze(productId);
	}
	
	@RequestMapping(value = "/detail", method = { RequestMethod.GET, RequestMethod.POST })
	public String detail(Model model, HttpSession session,Long productId) {
		TBrIngredientQuery query = new TBrIngredientQuery();
		query.setJoinFlg(true);
		query.setProductId(productId);
		List<TBrIngredient> list = tBrIngredientService.queryList(query);
		for (TBrIngredient tBrIngredient : list) {
			TBrIngredientVo vo = (TBrIngredientVo) tBrIngredient;
			tBrIngredientService.getAims(vo);
		}
		tBrIngredientService.decorateColour(list);
		model.addAttribute(LIST, list);
		return "ingredient/detail";
	}
	
	//成分数量
	@ResponseBody
	@RequestMapping(value = "/count", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> count(Model model, HttpSession session,Long productId) {
		TBrIngredientQuery query = new TBrIngredientQuery();
		query.setJoinFlg(true);
		query.setProductId(productId);
		long count = tBrIngredientService.queryCount(query);
		Map<String, Object> result = result();
		result.put("count", count);
		return result;
	}
	
	
}









