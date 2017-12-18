package com.cntend.beauty.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.controller.BaseRestControllerHandler;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.user.TBrUserProductContrast;
import com.co.example.entity.user.aide.TBrUserProductContrastQuery;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.user.TBrUserProductContrastService;
import com.co.example.utils.SessionUtil;
import com.google.common.collect.Lists;

@Controller
@RequestMapping("contrast")
public class ContrastController extends BaseRestControllerHandler<TBrUserProductContrastQuery> {

	@Inject
	TBrUserProductContrastService service;
	
	@Inject
	TBrProductService tBrProductService;

	@RequestMapping(value="data")
	public List<TBrProduct> data(Model model, HttpSession session){
		Long userId = SessionUtil.getUserId(session);
		TBrUserProductContrastQuery query = new TBrUserProductContrastQuery();
		query.setCreateBy(userId);
		List<TBrUserProductContrast> list = service.queryList(query);
		ArrayList<TBrProduct> productList = Lists.newArrayList();
		for (TBrUserProductContrast tBrUserProductContrast : list) {
			Long pid = tBrUserProductContrast.getPid();
			TBrProduct product = tBrProductService.showOneProduct4Mobile(pid);
			productList.add(product);
		}
		return  productList;
	}
	
}








