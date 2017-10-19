package com.co.example.controller.product;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.spec.TBrSpecValue;
import com.co.example.entity.spec.aide.TBrSpecKeyQuery;
import com.co.example.entity.spec.aide.TBrSpecValueQuery;
import com.co.example.service.spec.TBrSpecValueService;

@Controller
@RequestMapping("spec")
public class SpecController extends BaseControllerHandler<TBrSpecKeyQuery> {

	@Resource
	TBrSpecValueService tBrSpecValueService;
	
	@Override
	public Boolean showExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrSpecKeyQuery t, Long id) {
		TBrSpecValueQuery tBrSpecValueQuery = new TBrSpecValueQuery();
		tBrSpecValueQuery.setKeyId(id);
		List<TBrSpecValue> specValueList = tBrSpecValueService.queryList(tBrSpecValueQuery);
		model.addAttribute("specValueList", specValueList);
		return false;
	}

	
}









