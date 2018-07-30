package com.cntend.beauty.controller.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.utils.PageReq;
import com.co.example.controller.BaseRestControllerHandler;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.entity.user.aide.TBrUserCollectQuery;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.user.TBrUserCollectService;
import com.co.example.utils.SessionUtil;
import com.google.common.collect.Lists;

@Controller
@RequestMapping("collect")
public class CollectController extends BaseRestControllerHandler<TBrUserCollectQuery> {

	@Autowired
	TBrUserCollectService  tBrUserCollectService;
	
	@Autowired
	TBrProductService  tBrProductService;
	
	
	//商品异步收藏
	@Override
	public Boolean addExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrUserCollectQuery t, PageReq pageReq, Map<String, Object> result) {
		t.setCreateBy(SessionUtil.getUserId(session));
		TBrUserCollectQuery tBrUserCollectQuery = new TBrUserCollectQuery();
		tBrUserCollectQuery.setCid(t.getCid());
		tBrUserCollectQuery.setCreateBy(userId);
		long queryCount = tBrUserCollectService.queryCount(tBrUserCollectQuery);
		if(queryCount>0){
			return true;
		}
		return super.addExt(model, session, request, response, t, pageReq, result);
	}

	
	//我的收藏二级页面
	@RequestMapping(value = "/init", method = { RequestMethod.GET, RequestMethod.POST })
	public String init() {
		return "collect/init";
	}
	
	@ResponseBody
	@RequestMapping(value = "/listData", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String,Object> listData( HttpSession session,PageReq pageReq) {
		Map<String, Object> result = result();
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setJoinCollectFlg(true);
		tBrProductQuery.setUserId(SessionUtil.getUserId(session));
		Page<TBrProduct> page = tBrProductService.queryPageList(tBrProductQuery, pageReq);
		List<TBrProduct> newContent = Lists.newArrayList();
		for (TBrProduct tBrProduct : page.getContent()) {
			tBrProduct = tBrProductService.queryById(tBrProduct.getId());
			newContent.add(tBrProduct);
		}
		Page<TBrProduct> pageNew = new PageImpl<TBrProduct>(newContent, pageReq,newContent.size());
		result.put(PAGE, pageNew);
		return result;
	}

	
	
}
