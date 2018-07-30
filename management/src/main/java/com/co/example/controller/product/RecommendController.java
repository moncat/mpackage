package com.co.example.controller.product;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.TBrRecommend;
import com.co.example.entity.product.aide.TBrRecommendConstant;
import com.co.example.entity.product.aide.TBrRecommendQuery;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.product.TBrRecommendService;
import com.co.example.utils.BaseDataUtil;

@Controller
@RequestMapping("recommend") 
public class RecommendController extends  BaseControllerHandler<TBrRecommendQuery> {

	@Resource
	TBrProductService tBrProductService;
	
	@Resource
	TBrRecommendService tBrRecommendService;
	
	@ResponseBody
	@RequestMapping(value = "/ok/{id}", method = { RequestMethod.POST })
	public Map<String, Object> ok( Model model,HttpSession session, @PathVariable Long id ) {
		Map<String, Object> result = result();
		TBrRecommendQuery tBrRecommendQuery = new TBrRecommendQuery();
		tBrRecommendQuery.setCid(id);
		long count = tBrRecommendService.queryCount(tBrRecommendQuery);
		if(count == 0){
			TBrProduct product = tBrProductService.queryById(id);
			TBrRecommend tBrRecommend = new TBrRecommend();
			tBrRecommend.setCid(id);
			tBrRecommend.setName(product.getProductName());
			tBrRecommend.setType(TBrRecommendConstant.TYPE_PRODUCT);
			BaseDataUtil.setDefaultData(tBrRecommend);
			tBrRecommendService.add(tBrRecommend);
		}
		return result;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/cancel/{id}", method = { RequestMethod.POST })
	public Map<String, Object> cancel( Model model,HttpSession session, @PathVariable Long id ) {
		Map<String, Object> result = result();
		TBrRecommend tBrRecommendQuery = new TBrRecommendQuery();
		tBrRecommendQuery.setCid(id);
		tBrRecommendQuery.setType(TBrRecommendConstant.TYPE_PRODUCT);
		tBrRecommendService.delete(tBrRecommendQuery);
		return result;
	}
	
}
