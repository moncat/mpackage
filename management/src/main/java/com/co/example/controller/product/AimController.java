package com.co.example.controller.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.co.example.common.utils.PageReq;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.product.TBrAim;
import com.co.example.entity.product.TBrIngredientAim;
import com.co.example.entity.product.aide.TBrAimQuery;
import com.co.example.entity.product.aide.TBrIngredientAimQuery;
import com.co.example.service.product.TBrAimService;
import com.co.example.service.product.TBrIngredientAimService;

@Controller
@RequestMapping("aim")
public class AimController extends BaseControllerHandler<TBrAimQuery> {
	
	@Autowired
	TBrAimService tBrAimService;

	@Autowired
	TBrIngredientAimService tBrIngredientAimService;

	@RequestMapping(value = "/showMore/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String showMore(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, @PathVariable Long id) {
		TBrAim one = tBrAimService.queryById(id);
		TBrIngredientAimQuery tBrIngredientAimQuery = new TBrIngredientAimQuery();
		tBrIngredientAimQuery.setJoinIngredientFlg(true);
		tBrIngredientAimQuery.setAimId(id);
		pageReq.setPageSize(10);
		Page<TBrIngredientAim> page = tBrIngredientAimService.queryPageList(tBrIngredientAimQuery, pageReq);
		model.addAttribute(PAGE, page);
		model.addAttribute(ONE, one);
		return "aim/show";
	}

}
