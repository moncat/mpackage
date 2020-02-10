package com.co.example.controller.category;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.PageReq;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.category.TBrCategory;
import com.co.example.entity.category.aide.TBrCategoryQuery;
import com.co.example.entity.category.aide.TBrProductCategoryQuery;
import com.co.example.service.category.TBrCategoryService;
import com.co.example.service.category.TBrProductCategoryService;
import com.github.moncat.common.service.BaseService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("category")
public class CategoryController extends BaseControllerHandler<TBrCategoryQuery> {

	@Inject
	TBrCategoryService tBrCategoryService;
	
	@Inject
	TBrProductCategoryService tBrProductCategoryService;

	@Override
	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, TBrCategoryQuery query) {
		pageReq.setSort(new Sort(Direction.DESC, "t.create_time"));
		return super.listExt(model, session, request, response, pageReq, query);
	}

	@Override
	public void addInitExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		TBrCategoryQuery tBrCategoryQuery = new TBrCategoryQuery();
		tBrCategoryQuery.setDelFlg(Constant.NO);
		tBrCategoryQuery.setIsRoot(Constant.YES);
		List<TBrCategory> queryList = tBrCategoryService.queryList(tBrCategoryQuery);
		model.addAttribute("list", queryList);
	}

	@RequestMapping(value = "/editInit1/{id}/{pageNumber}", method = { RequestMethod.GET,RequestMethod.POST })
	public String editInit1( Model model,@PathVariable Long id, @PathVariable Long pageNumber)throws Exception {
		TBrCategory one = tBrCategoryService.queryById(id);
		model.addAttribute(ONE, one);
		TBrCategoryQuery tBrCategoryQuery = new TBrCategoryQuery();
		tBrCategoryQuery.setDelFlg(Constant.NO);
		tBrCategoryQuery.setIsRoot(Constant.YES);
		List<TBrCategory> queryList = tBrCategoryService.queryList(tBrCategoryQuery);
		model.addAttribute("list", queryList);
		model.addAttribute("pageNumber", pageNumber);
		return "/category/editInit";
	}
	

	@Override
	public Boolean addExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrCategoryQuery t, PageReq pageReq, Map<String, Object> result) {
		Long parentId = t.getParentId();
		if (parentId == null || parentId == 0) {
			t.setParentName("根目录");
			t.setIsRoot(Constant.YES);
			t.setLevel(1);
		} else {
			TBrCategoryQuery tBrCategoryQuery = new TBrCategoryQuery();
			tBrCategoryQuery.setId(parentId);
			TBrCategory queryOne = tBrCategoryService.queryOne(tBrCategoryQuery);
			t.setParentName(queryOne.getName());
			t.setIsRoot(Constant.NO);
			t.setLevel(2);
		}
		return false;
	}

	@Override
	public Boolean editExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrCategoryQuery t, PageReq pageReq, Map<String, Object> result) {
		Long parentId = t.getParentId();
		TBrCategory p = tBrCategoryService.queryById(parentId);
		t.setParentName(p.getName());
		return super.editExt(model, session, request, response, t, pageReq, result);
	}

	@ResponseBody
	@RequestMapping(value = "/updateStatus", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> updateStatus(Model model, Long id, Boolean flg) throws Exception {
		Map<String, Object> result = result();
		TBrCategory one = new TBrCategory();
		one.setId(id);
		if (flg) {
			one.setIsChoice(Constant.YES);
		} else {
			one.setIsChoice(Constant.NO);
		}
		tBrCategoryService.updateByIdSelective(one);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/count/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> count(Model model, @PathVariable Long id) {
		Long num = 0l;
		TBrProductCategoryQuery tBrProductCategoryQuery = new TBrProductCategoryQuery();
		tBrProductCategoryQuery.setCategoryId(id);
		long queryCount = tBrProductCategoryService.queryCount(tBrProductCategoryQuery);
		num += queryCount;
		Map<String, Object> result = result();
		result.put("num", num);
		return result;
	}
}
