package com.co.example.controller.product;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.co.example.controller.BaseControllerHandler;
import com.co.example.common.utils.PageReq;
import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrIngredientQuery;
import com.co.example.entity.product.aide.TBrIngredientVo;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.product.TBrIngredientService;
import com.co.example.service.product.TBrProductService;

@Controller
@RequestMapping("ingredient")
public class IngredientController extends BaseControllerHandler<TBrIngredientQuery> {
	
	@Inject
	TBrProductService tBrProductService;

	@Inject
	TBrIngredientService tBrIngredientService;
	
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

	@RequestMapping(value = "/showMore/{id}", method = { RequestMethod.GET,RequestMethod.POST })
	public String showMore(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq,@PathVariable Long id) {
		//获得one
		TBrIngredientQuery tBrIngredientQuery = new TBrIngredientQuery();
		tBrIngredientQuery.setId(id);
		TBrIngredient one = tBrIngredientService.queryOne(tBrIngredientQuery);
		TBrIngredientVo vo = (TBrIngredientVo)one;
		//获得aim
		tBrIngredientService.getAims(vo);
		//获得产品
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setIngredientId(id);
		tBrProductQuery.setJoinFlg(true);
		pageReq.setPageSize(10);
		Page<TBrProduct> page = tBrProductService.queryPageList(tBrProductQuery, pageReq);
		model.addAttribute(PAGE, page);
		model.addAttribute(ONE, one);
		return "/ingredient/show";
	}
	
	
	
}









