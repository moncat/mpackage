package com.co.example.controller.product;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.DateUtil;
import com.co.example.common.utils.PageReq;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.label.TBrIngredientLabel;
import com.co.example.entity.label.TBrIngredientLabelJoin;
import com.co.example.entity.label.aide.TBrIngredientLabelQuery;
import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrIngredientQuery;
import com.co.example.entity.product.aide.TBrIngredientVo;
import com.co.example.entity.product.aide.TBrProductIngredientQuery;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.entity.product.aide.TBrProductVo;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.label.TBrIngredientLabelJoinService;
import com.co.example.service.label.TBrIngredientLabelService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrIngredientService;
import com.co.example.service.product.TBrProductIngredientService;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.solr.SolrService;
import com.google.common.collect.Lists;

//成分
@Controller
@RequestMapping("ingredient")
public class IngredientController extends BaseControllerHandler<TBrIngredientQuery> {

	@Inject
	TBrProductService tBrProductService;

	@Inject
	TBrIngredientService tBrIngredientService;

	@Inject
	TBrIngredientLabelService tBrIngredientLabelService;

	@Inject
	TBrIngredientLabelJoinService tBrIngredientLabelJoinService;
	
	@Inject
	TBrProductIngredientService tBrProductIngredientService;
	
	@Inject
	TBrEnterpriseService tBrEnterpriseService;
	
	@Inject
	TBrBrandService tBrBrandService;
	
	@Inject
	SolrService solrService;

	@Override
	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, TBrIngredientQuery query) {
		pageReq.setPageSize(10);
		Page<TBrIngredient> page = tBrIngredientService.queryPageList(query, pageReq);
		// 装饰成分信息
		tBrIngredientService.decorateColour(page.getContent());
		model.addAttribute(QUERY, query);
		model.addAttribute(PAGE, page);
		return true;
	}

	@RequestMapping(value = "/showMore/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String showMore(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, @PathVariable Long id) {
		// 获得one
		TBrIngredientQuery tBrIngredientQuery = new TBrIngredientQuery();
		tBrIngredientQuery.setId(id);
		TBrIngredient one = tBrIngredientService.queryOne(tBrIngredientQuery);
		TBrIngredientVo vo = (TBrIngredientVo) one;
		// 获得aim
		tBrIngredientService.getAims(vo);
		// 获得产品
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setIngredientId(id);
		tBrProductQuery.setJoinFlg(true);
		pageReq.setPageSize(10);
		Page<TBrProduct> page = tBrProductService.queryPageList(tBrProductQuery, pageReq);
		model.addAttribute(PAGE, page);
		model.addAttribute(ONE, one);
		return "/ingredient/show";
	}

	/********* 2019年8月13日新增 **************/

	@RequestMapping(value = "/showMore2/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String showMore2(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, @PathVariable Long id) {
		// 获得one
		TBrIngredientQuery tBrIngredientQuery = new TBrIngredientQuery();
		tBrIngredientQuery.setId(id);
		TBrIngredient one = tBrIngredientService.queryOne(tBrIngredientQuery);
		TBrIngredientVo vo = (TBrIngredientVo) one;
		// 获得aim
		tBrIngredientService.getAims(vo);
		// 获得产品
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setIngredientId(id);
		tBrProductQuery.setJoinFlg(true);
		pageReq.setPageSize(10);
		Page<TBrProduct> page = tBrProductService.queryPageList(tBrProductQuery, pageReq);
		
		List<TBrIngredientLabel> labelList = tBrIngredientService.getLabelListById(id);
		model.addAttribute(PAGE, page);
		model.addAttribute(ONE, one);
		model.addAttribute("labelList", labelList);
		return "/ingredient/show2";
	}

	@RequestMapping(value = "/list2", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model, HttpSession session, PageReq pageReq, TBrIngredientQuery query) throws Exception {
		query.setDelFlg(Constant.NO);
		Page<TBrIngredient> page = tBrIngredientService.queryPageList(query, pageReq);
		// 装饰成分信息
		tBrIngredientService.decorateColour(page.getContent());
		model.addAttribute(QUERY, query);
		model.addAttribute(PAGE, page);
		TBrIngredientLabelQuery tBrIngredientLabelQuery = new TBrIngredientLabelQuery();
		tBrIngredientLabelQuery.setDelFlg(Constant.NO);
		List<TBrIngredientLabel> labelList = tBrIngredientLabelService.queryList(tBrIngredientLabelQuery);
		model.addAttribute("labelList", labelList);
		return "ingredient/list2";
	}

	@ResponseBody
	@RequestMapping(value = "/updateStatus", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> updateStatus(Model model, Long id, Boolean flg) throws Exception {
		Map<String, Object> result = result();
		TBrIngredient one = new TBrIngredient();
		one.setId(id);
		if (flg) {
			one.setIsChoice(Constant.YES);
		} else {
			one.setIsChoice(Constant.NO);
		}
		tBrIngredientService.updateByIdSelective(one);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/count/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> count(Model model, @PathVariable Long id) {
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setJoinFlg(true);
		tBrProductQuery.setIngredientId(id);
		long num = tBrProductService.queryCount(tBrProductQuery);
		Map<String, Object> result = result();
		result.put("num", num);
		return result;
	}

	@RequestMapping(value = "/labels", method = { RequestMethod.GET, RequestMethod.POST })
	public String labels(Model model) throws Exception {
		return "ingredient/labels";
	}

	@ResponseBody
	@RequestMapping(value = "/labelList", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> labelList(Model model, String key) throws Exception {
		Map<String, Object> result = result();
		TBrIngredientLabelQuery tBrIngredientLabelQuery = new TBrIngredientLabelQuery();
		tBrIngredientLabelQuery.setNameLike(key);
		tBrIngredientLabelQuery.setDelFlg(Constant.NO);
		List<TBrIngredientLabel> labelList = tBrIngredientLabelService.queryList(tBrIngredientLabelQuery);
		result.put("labelList", labelList);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/setLabels", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> setLabels(Model model, String iids, @RequestParam(value = "lids[]") Long[] lids) {
		Map<String, Object> result = result();

		try {
			String[] ingredientIds = iids.split(",");
			for (int i = 0; i < ingredientIds.length; i++) {
				Long ingredientId = Long.parseLong(ingredientIds[i]);
				for (int j = 0; j < lids.length; j++) {
					Long labelId = lids[j];
					TBrIngredientLabelJoin tBrIngredientLabelJoin = new TBrIngredientLabelJoin();
					tBrIngredientLabelJoin.setIngredientId(ingredientId);
					tBrIngredientLabelJoin.setLabelId(labelId);
					long queryCount = tBrIngredientLabelJoinService.queryCount(tBrIngredientLabelJoin);
					if (queryCount == 0) {
						tBrIngredientLabelJoin.setCreateTime(new Date());
						tBrIngredientLabelJoin.setDelFlg(Constant.NO);
						tBrIngredientLabelJoinService.add(tBrIngredientLabelJoin);
					}
				}
			}
		} catch (Exception e) {
			result.put("info", "关联失败！");
		}
		result.put("info", "关联完成！");
		return result;
	}

	@RequestMapping(value = "/index/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String index(Model model, HttpSession session, @PathVariable Long id,   PageReq pageReq, TBrProductQuery query) {
		// 获得one
		TBrIngredientQuery tBrIngredientQuery = new TBrIngredientQuery();
		tBrIngredientQuery.setId(id);
		TBrIngredient one = tBrIngredientService.queryOne(tBrIngredientQuery);
		TBrIngredientVo vo = (TBrIngredientVo) one;
		// 获得aim
		tBrIngredientService.getAims(vo);
		// 获得产品
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setIngredientId(id);
		tBrProductQuery.setJoinFlg(true);
		Page<TBrProductVo> page = tBrProductService.queryPageList(tBrProductQuery, pageReq);
		for (TBrProductVo tBrProductVo : page) {
			tBrProductVo.setEnterpriseList(tBrEnterpriseService.queryEnterpriseListByProductId(tBrProductVo.getId()));
			tBrProductVo.setTBrBrand(tBrBrandService.queryByProductId(tBrProductVo.getId()));
		}
		model.addAttribute(PAGE, page);
		model.addAttribute(QUERY, query);
		model.addAttribute(ONE, one);
		return "ingredient/index";
	}

	@ResponseBody
	@RequestMapping(value = "/month/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> month(Model model,  @PathVariable Long id) {
		TBrProductIngredientQuery tBrProductIngredientQuery = null;
		Map<String, Object> result = result();
		List<String> monthLabels = DateUtil.getMonths();
		List<Date> monthsLimits = DateUtil.getDateMonthsOnYear();
		List<Long> series = Lists.newArrayList();
		List<Long> series2 = Lists.newArrayList();
		Long tmp = 0l;
		for (int i = 0; i < monthsLimits.size()-1; i++) {
			Date timeLmit1 = monthsLimits.get(i);
			Date timeLmit2 = monthsLimits.get(i+1);
			tBrProductIngredientQuery = new TBrProductIngredientQuery();
			tBrProductIngredientQuery.setIngredientId(id);
			tBrProductIngredientQuery.setTimeLmit1(timeLmit1);
			tBrProductIngredientQuery.setTimeLmit2(timeLmit2);
			long count = tBrProductIngredientService.queryCount(tBrProductIngredientQuery);
			tmp = tmp+count;
			series.add(count);
			series2.add(tmp);
		}
		result.put("legend", monthLabels);
		result.put("series", series);
		result.put("series2", series2);
		return result;
	}
	
}


//query.setDelFlg(Constant.NO);
//int pageSize = 15;
//Map<String, Object> map = solrService.querySolr(query, 15 * pageReq.getPageNumber(), pageSize);
//pageReq.setPageSize(pageSize);
//@SuppressWarnings("unchecked")
//int count = Integer.parseInt(map.get("count").toString());
//Page<TBrProductSolr> page = new PageImpl<TBrProductSolr>((List<TBrProductSolr>) map.get("list"), pageReq,
//		count);
//model.addAttribute("count", count);
//model.addAttribute(QUERY, query);
//model.addAttribute(PAGE, page);