package com.cntend.beauty.controller.recommend;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.utils.PageReq;
import com.co.example.constant.HttpStatusCode;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.product.TBrRecommendService;

@Controller
@RequestMapping("recommend") 
public class RecommendController  {

	@Resource
	TBrProductService tBrProductService;
	
	@Resource
	TBrRecommendService tBrRecommendService;	
	
	@ResponseBody
	@RequestMapping(value = "list", method = { RequestMethod.POST })
	public Map<String, Object> list( Model model,HttpSession session,PageReq pageReq) {
		
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setJoinRecommendFlg(true);
		tBrProductQuery.setRecommendIsNotNullFlg(true);
		Page<TBrProduct> page = tBrProductService.queryPageList(tBrProductQuery, pageReq);
		Map<String, Object> result =  new HashMap<String, Object>();
		result.put("code", HttpStatusCode.CODE_SUCCESS);
		
//		TBrRecommend tBrRecommendQuery = new TBrRecommendQuery();
//		tBrRecommendQuery.setType(TBrRecommendConstant.TYPE_PRODUCT);
//		Page<TBrRecommend> page = tBrRecommendService.queryPageList(tBrRecommendQuery, pageReq);
//		List<TBrRecommend> content = page.getContent();
//		ArrayList<TBrProduct> productList = Lists.newArrayList();
//		for (TBrRecommend tBrRecommend : content) {
//			productList.add(tBrProductService.queryById(tBrRecommend.getCid()));
//		}
		
		result.put("page", page);
		return result;
	}
	
}
