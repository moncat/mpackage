package com.cntend.beauty.controller.user;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.constant.HttpStatusCode;
import com.co.example.entity.label.TBrProductLabel;
import com.co.example.entity.label.aide.TBrProductLabelQuery;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.user.TBrUserProductContrast;
import com.co.example.entity.user.aide.TBrUserProductContrastPriceDto;
import com.co.example.entity.user.aide.TBrUserProductContrastQuery;
import com.co.example.entity.user.aide.UserSession;
import com.co.example.service.label.TBrProductLabelService;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.user.TBrUserProductContrastService;
import com.co.example.utils.BaseDataUtil;
import com.co.example.utils.SessionUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;


//商品对比
@Controller
@RequestMapping("contrast")
public class ContrastController {

	@Inject
	TBrUserProductContrastService service;
	
	@Inject
	TBrProductService tBrProductService;
	
	@Inject
	TBrProductLabelService tBrProductLabelService;

	
	private static final  String  CONTRAST_LIST ="contrastList";
	
	/**
	 * 用户已经选择的对比商品  
	 * 功能类似 商品购物车
	 * 新增当前商品，并跳转到列表页面
	 * @param model
	 * @param session
	 * @param productId 在商品详情页，用户已经选择的
	 * @return
	 */
	@RequestMapping(value="list" ,method = { RequestMethod.GET})
	public String list(Model model, HttpSession session, Long productId){
		UserSession userSession = SessionUtil.getUserSession(session);
		ArrayList<TBrProduct> productList = Lists.newArrayList();
		Boolean isLogin = false;
		if(userSession !=null){
			isLogin = userSession.isLogin();
		}
		//登录
		if(isLogin){
			//从session中获得的商品id
			@SuppressWarnings("unchecked")
			Set<Long> pidSet = (Set<Long>) session.getAttribute(CONTRAST_LIST);
			if(pidSet == null){
				pidSet = Sets.newHashSet();
			}
			if(productId!=null){
				pidSet.add(productId);
			}
			//从数据库里获得的商品id
			Long userId = userSession.getUser().getId();
			TBrUserProductContrastQuery query = new TBrUserProductContrastQuery();
			query.setCreateBy(userId);
			List<TBrUserProductContrast> list = service.queryList(query);
			for (TBrUserProductContrast tBrUserProductContrast : list) {
				Long pid = tBrUserProductContrast.getPid();
				//去掉和数据库重复的部分
				if(pidSet.contains(pid)){
					pidSet.remove(pid);
				}
				TBrProduct product = tBrProductService.queryById(pid);
				productList.add(product);
			}
			TBrUserProductContrast tBrUserProductContrast = null;
			//将这些数据也保存到数据库,并合并最新的列表
			for (Long pidTmp : pidSet) {
				tBrUserProductContrast = new TBrUserProductContrast();
				tBrUserProductContrast.setPid(pidTmp);
				tBrUserProductContrast.setCreateBy(userId);
				BaseDataUtil.setDefaultData(tBrUserProductContrast);
				service.add(tBrUserProductContrast);
				TBrProduct product = tBrProductService.queryById(pidTmp);
				productList.add(product);
			}
			session.removeAttribute(CONTRAST_LIST);
		}else{
			//未登录
			@SuppressWarnings("unchecked")
			Set<Long> pidSet = (Set<Long>) session.getAttribute(CONTRAST_LIST);
			if(pidSet == null ){
				pidSet = Sets.newHashSet();
			}
			if(productId!=null){
				pidSet.add(productId);
			}
			session.setAttribute(CONTRAST_LIST, pidSet);
			for (Long pid : pidSet) {
				TBrProduct product = tBrProductService.queryById(pid);
				productList.add(product);
			}
		}
		model.addAttribute("list", productList);
		return  "contrast/list";
	}
	
	@ResponseBody
	@RequestMapping(value="delete" ,method = { RequestMethod.POST})
	public Map<String ,Object> delete(Model model, HttpSession session,@RequestParam(value="productIds[]") Long[] productIds){
		Map<String, Object> map = Maps.newHashMap();
		map.put("code", HttpStatusCode.CODE_SUCCESS);
		UserSession userSession = SessionUtil.getUserSession(session);
		Boolean isLogin = false;
		if(userSession !=null){
			isLogin = userSession.isLogin();
		}
		if(isLogin){
			for (Long pid : productIds) {
				TBrUserProductContrast tBrUserProductContrast = new TBrUserProductContrast();
				tBrUserProductContrast.setPid(pid);
				service.delete(tBrUserProductContrast);
			}
		}else{
			@SuppressWarnings("unchecked")
			Set<Long> pidSet = (Set<Long>) session.getAttribute(CONTRAST_LIST);
			for (Long pid : productIds) {
				pidSet.remove(pid);
			}
			session.setAttribute(CONTRAST_LIST, pidSet);
		}
		return map;
	}
	
	@RequestMapping(value="detail/{id1}/{id2}" ,method = { RequestMethod.GET})
	public String detail(Model model, HttpSession session,@PathVariable Long id1,@PathVariable Long id2){
		
		TBrProduct product1 = tBrProductService.queryById(id1);
		TBrProduct product2 = tBrProductService.queryById(id2);
		BigDecimal price1 = tBrProductService.getCheapPrice(id1);
		BigDecimal price2 = tBrProductService.getCheapPrice(id2);
		model.addAttribute("product1", product1);
		model.addAttribute("product2", product2);
		model.addAttribute("price1", price1);
		model.addAttribute("price2", price2);
		return "contrast/detail";
	}
	
	//ajax
	//制造商 已有     1
	//销量（天猫）    已有        1
	// 评分比较       没有评分，只有评价个数
	
	
	
   // 价格低价计算，暂不使用该方法
	@ResponseBody
	@RequestMapping(value="price/{id1}/{id2}" ,method = { RequestMethod.POST})
	public HashMap<String, Object> price(Model model, HttpSession session,@PathVariable Long id1,@PathVariable Long id2){
		HashMap<String, Object> map = Maps.newHashMap();
		Map<Long, TBrUserProductContrastPriceDto> contrastPrice = service.contrastPrice(id1, id2);
		map.put("price", contrastPrice);
		return map;
	}
	
	//	标签    计算 1
	@ResponseBody
	@RequestMapping(value="label/{id1}/{id2}" ,method = { RequestMethod.POST})
	public HashMap<Long, Object> label(Model model, HttpSession session,@PathVariable Long id1,@PathVariable Long id2){
		HashMap<Long, Object> map = Maps.newHashMap();
		TBrProductLabelQuery tBrProductLabelQuery = new TBrProductLabelQuery();
		tBrProductLabelQuery.setJoinLabelFlg(true);
		tBrProductLabelQuery.setProductId(id1);
		List<TBrProductLabel> list1 = tBrProductLabelService.queryList(tBrProductLabelQuery);
		tBrProductLabelQuery.setProductId(id2);
		List<TBrProductLabel> list2 = tBrProductLabelService.queryList(tBrProductLabelQuery);
		map.put(id1, list1);
		map.put(id2, list2);
		return map;
	}
	
}








