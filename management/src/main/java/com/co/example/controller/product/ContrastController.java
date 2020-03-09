package com.co.example.controller.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.PageReq;
import com.co.example.entity.brand.TBrBrand;
import com.co.example.entity.brand.TBrProductBrand;
import com.co.example.entity.brand.aide.TBrBrandQuery;
import com.co.example.entity.brand.aide.TBrBrandVo;
import com.co.example.entity.brand.aide.TBrProductBrandQuery;
import com.co.example.entity.enterprise.TBrEnterprisePermission;
import com.co.example.entity.enterprise.TBrEnterpriseRegister;
import com.co.example.entity.label.TBrProductLabel;
import com.co.example.entity.label.aide.TBrProductLabelQuery;
import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrEnterpriseQuery;
import com.co.example.entity.product.aide.TBrEnterpriseVo;
import com.co.example.entity.product.aide.TBrIngredientQuery;
import com.co.example.entity.product.aide.TBrIngredientVo;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.entity.product.aide.TBrProductSolr;
import com.co.example.entity.product.aide.TBrProductVo;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.brand.TBrProductBrandService;
import com.co.example.service.enterprise.TBrEnterprisePermissionService;
import com.co.example.service.enterprise.TBrEnterpriseRegisterService;
import com.co.example.service.label.TBrProductLabelService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrIngredientService;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.solr.SolrService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

//商品对比
@Controller
@RequestMapping("contrast")
public class ContrastController {

	@Inject
	TBrProductService tBrProductService;

	@Inject
	TBrEnterpriseService tBrEnterpriseService;

	@Inject
	TBrProductLabelService tBrProductLabelService;

	@Inject
	TBrProductBrandService tBrProductBrandService;

	@Inject
	TBrIngredientService tBrIngredientService;

	@Inject
	TBrBrandService tBrBrandService;

	@Inject
	SolrService solrService;

	@Inject
	TBrEnterprisePermissionService tBrEnterprisePermissionService;
	
	@Inject
	TBrEnterpriseRegisterService tBrEnterpriseRegisterService;

	/**
	 * 旧版使用
	 */
	@Deprecated
	@RequestMapping(value = "detail/{ids}", method = { RequestMethod.GET })
	public String detail(Model model, HttpSession session, @PathVariable String ids) {
		ArrayList<TBrProductVo> list = Lists.newArrayList();
		if (StringUtils.isNotBlank(ids)) {
			String[] idArr = ids.split("_");
			for (String idStr : idArr) {
				if (NumberUtils.isDigits(idStr)) {
					long id = Long.parseLong(idStr);
					if (id != 0) {
						TBrProductQuery tBrProductQuery = new TBrProductQuery();
						tBrProductQuery.setId(id);
						tBrProductQuery.setJoinBrandFlg(true);
						TBrProduct productTmp = tBrProductService.queryOne(tBrProductQuery);
						if (productTmp != null) {
							TBrProductVo productVo = (TBrProductVo) productTmp;
							// 插入生产企业
							TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
							tBrEnterpriseQuery.setProductId(id);
							tBrEnterpriseQuery.setJoinFlg(true);
							List<TBrEnterpriseVo> enterpriseList = tBrEnterpriseService.queryList(tBrEnterpriseQuery);
							productVo.setEnterpriseList(enterpriseList);
							// 插入品牌
							TBrProductBrandQuery tBrProductBrandQuery = new TBrProductBrandQuery();
							tBrProductBrandQuery.setProductId(id);
							TBrProductBrand tBrProductBrand = tBrProductBrandService.queryOne(tBrProductBrandQuery);
							TBrBrand brand = null;
							if (tBrProductBrand != null) {
								brand = tBrBrandService.queryById(tBrProductBrand.getBrandId());
								if (brand != null) {
									productVo.setBrandName(brand.getName());
								}
							}
							list.add(productVo);
						}
					}
				}
			}
		}
		model.addAttribute("list", list);
		return "contrast/detail";
	}

	// 标签 计算 1
	@ResponseBody
	@RequestMapping(value = "label/{id1}/{id2}", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<Long, Object> label(Model model, HttpSession session, @PathVariable Long id1,
			@PathVariable Long id2) {
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

	/**
	 * 异步查询成分，成分分析，成分得分
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "ingredientAjax", method = { RequestMethod.POST })
	public HashMap<String, Object> ingredientAjax(Model model, Long id) {
		HashMap<String, Object> map = Maps.newHashMap();
		List<TBrIngredient> ingredientList = tBrIngredientService.queryTBrIngredientList(id);
		Float productScore = tBrIngredientService.getProductScore(ingredientList);
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setId(id);
		TBrProduct statisticsInfo = tBrProductService.getStatisticsInfo(tBrProductService.queryOne(tBrProductQuery),
				ingredientList);
		map.put("ingredientList", ingredientList);
		map.put("statisticsInfo", statisticsInfo);
		map.put("productScore", productScore);
		return map;
	}

	@RequestMapping(value = "choice", method = { RequestMethod.GET, RequestMethod.POST })
	public String choice(Model model) throws Exception {
		return "contrast/choice";
	}

	// 页面展示
	@RequestMapping(value = "detailP", method = { RequestMethod.GET, RequestMethod.POST })
	public String detailP(Model model) throws Exception {
		return "contrast/detailP";
	}

	@RequestMapping(value = "detailB", method = { RequestMethod.GET, RequestMethod.POST })
	public String detailB(Model model) throws Exception {
		return "contrast/detailB";
	}

	@RequestMapping(value = "detailE", method = { RequestMethod.GET, RequestMethod.POST })
	public String detailE(Model model) throws Exception {
		return "contrast/detailE";
	}

	@RequestMapping(value = "detailI", method = { RequestMethod.GET, RequestMethod.POST })
	public String detailI(Model model) throws Exception {
		return "contrast/detailI";
	}

	// 数据异步添加
	@ResponseBody
	@RequestMapping(value = "/poption", method = { RequestMethod.GET, RequestMethod.POST })
	public List<TBrProductSolr> poption(Model model, String key) throws Exception {
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setNormal(key);
		tBrProductQuery.setNormalType(1);
		Map<String, Object> map = solrService.querySolr2(tBrProductQuery, 0, 10);
		@SuppressWarnings("unchecked")
		List<TBrProductSolr> list = (List<TBrProductSolr>) map.get("list");
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/boption", method = { RequestMethod.GET, RequestMethod.POST })
	public List<TBrBrand> boption(Model model, String key) throws Exception {
		TBrBrandQuery query = new TBrBrandQuery();
		query.setNameLike(key);
		query.setDelFlg(Constant.NO);
		PageReq pageReq = new PageReq();
		pageReq.setPage(1);
		pageReq.setPageSize(10);
		Page<TBrBrand> page = tBrBrandService.queryPageList(query, pageReq);
		List<TBrBrand> list = page.getContent();
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/eoption", method = { RequestMethod.GET, RequestMethod.POST })
	public List<TBrEnterprise> eoption(Model model, String key) throws Exception {
		TBrEnterpriseQuery query = new TBrEnterpriseQuery();
		query.setEnterpriseNameLike(key);
		query.setDelFlg(Constant.NO);
		PageReq pageReq = new PageReq();
		pageReq.setPage(1);
		pageReq.setPageSize(10);
		Page<TBrEnterprise> page = tBrEnterpriseService.queryPageList(query, pageReq);
		List<TBrEnterprise> list = page.getContent();
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/ioption", method = { RequestMethod.GET, RequestMethod.POST })
	public List<TBrIngredient> ioption(Model model, String key) throws Exception {
		TBrIngredientQuery query = new TBrIngredientQuery();
		query.setNameLike(key);
		query.setDelFlg(Constant.NO);
		PageReq pageReq = new PageReq();
		pageReq.setPage(1);
		pageReq.setPageSize(10);
		Page<TBrIngredient> page = tBrIngredientService.queryPageList(query, pageReq);
		List<TBrIngredient> list = page.getContent();
		return list;
	}

	// 数据异步展示
	@ResponseBody
	@RequestMapping(value = "oneP", method = { RequestMethod.POST })
	public HashMap<String, Object> oneP(Model model, HttpSession session, long id) {
		HashMap<String, Object> map = Maps.newHashMap();
		map.put("code", 400);
		if (id != 0) {
			TBrProductQuery tBrProductQuery = new TBrProductQuery();
			tBrProductQuery.setId(id);
			tBrProductQuery.setJoinBrandFlg(true);
			TBrProductVo productVo = tBrProductService.queryOne(tBrProductQuery);
			if (productVo != null) {
				// 插入生产企业
				TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
				tBrEnterpriseQuery.setProductId(id);
				tBrEnterpriseQuery.setJoinFlg(true);
				List<TBrEnterpriseVo> enterpriseList = tBrEnterpriseService.queryList(tBrEnterpriseQuery);
				productVo.setEnterpriseList(enterpriseList);
				// 插入品牌
				TBrProductBrandQuery tBrProductBrandQuery = new TBrProductBrandQuery();
				tBrProductBrandQuery.setProductId(id);
				TBrProductBrand tBrProductBrand = tBrProductBrandService.queryOne(tBrProductBrandQuery);
				TBrBrand brand = null;
				if (tBrProductBrand != null) {
					brand = tBrBrandService.queryById(tBrProductBrand.getBrandId());
					if (brand != null) {
						productVo.setBrandName(brand.getName());
					}
				}
				List<TBrIngredient> ingredientList = tBrIngredientService.queryTBrIngredientList(id);
				TBrProduct pvoWithstatisticsInfo = tBrProductService.getStatisticsInfo(productVo, ingredientList);
				map.put("pvo", pvoWithstatisticsInfo);
				map.put("iList", ingredientList);
				map.put("code", 200);
			}
		}
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "oneE", method = { RequestMethod.POST })
	public HashMap<String, Object> detailE(Model model, HttpSession session, long id) {
		HashMap<String, Object> map = Maps.newHashMap();
		map.put("code", 400);
		if (id != 0) {
			TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
			tBrEnterpriseQuery.setId(id);
			TBrEnterpriseVo eTmp = tBrEnterpriseService.queryOne(tBrEnterpriseQuery);
			TBrEnterprisePermission permission = tBrEnterprisePermissionService.queryVoByEId(id);
			TBrEnterpriseRegister register = tBrEnterpriseRegisterService.queryVoByEId(id);
			
			HashSet<Long> cSet = Sets.newHashSet();
			HashMap<Long, Integer> cMap = Maps.newHashMap();
			HashMap<Long, String> cNameMap = Maps.newHashMap();
			if (eTmp != null) {
				// 产品数
				List<TBrProductVo> plist = tBrProductService.queryProductVoListByRealEnterpriseId(id);
				eTmp.setPnum(plist.size());
				// 品牌数
				HashSet<Long> bSet = Sets.newHashSet();
				HashSet<Long> iSet = Sets.newHashSet();
				for (TBrProductVo tBrProductVo : plist) {
					bSet.add(tBrProductVo.getProductBrandId());
					List<TBrIngredient> ingredientList = tBrIngredientService.queryTBrIngredientList(tBrProductVo.getId());
					for (TBrIngredient tBrIngredient : ingredientList) {
						iSet.add(tBrIngredient.getId());
					}
					setCatagory(cSet, cMap, cNameMap, tBrProductVo);
				}
				eTmp.setBnum(bSet.size());
				eTmp.setInum(iSet.size());
			}
			map.put("cValues", cMap.values());
			map.put("cNames", cNameMap.values());
			map.put("permission", permission);
			map.put("register", register);
			map.put("evo", eTmp);
			map.put("code", 200);
		}
		return map;
	}

	private void setCatagory(HashSet<Long> cSet, HashMap<Long, Integer> cMap, HashMap<Long, String> cNameMap,
			TBrProductVo tBrProductVo) {
		Long categoryId = tBrProductVo.getCategoryId();
		String categoryName = tBrProductVo.getCategoryName();
		if (categoryId != null && categoryName != null) {
			if(cSet.contains(categoryId)){
				cMap.replace(categoryId, cMap.get(categoryId) + 1);
			}else{
				cSet.add(categoryId);							
				cMap.put(categoryId, 1);
				cNameMap.put(categoryId, categoryName);
			}
		}
	}

	@ResponseBody
	@RequestMapping(value = "oneB", method = { RequestMethod.POST })
	public HashMap<String, Object> oneB(Model model, HttpSession session, long id) {
		HashMap<String, Object> map = Maps.newHashMap();
		map.put("code", 400);
		HashSet<Long> cSet = Sets.newHashSet();
		HashMap<Long, Integer> cMap = Maps.newHashMap();
		HashMap<Long, String> cNameMap = Maps.newHashMap();
		if (id != 0) {
			HashSet<TBrEnterpriseVo> eSet = Sets.newHashSet();
			HashSet<Long> iSet = Sets.newHashSet();
			TBrBrandQuery tBrBrandQuery = new TBrBrandQuery();
			tBrBrandQuery.setId(id);
			TBrBrandVo bTmp = tBrBrandService.queryOne(tBrBrandQuery);
			TBrProductQuery tBrProductQuery = new TBrProductQuery();
			tBrProductQuery.setProductBrandId(id);
			tBrProductQuery.setDelFlg(Constant.NO);
			List<TBrProductVo> pList = tBrProductService.queryList(tBrProductQuery);
			for (TBrProductVo tBrProduct : pList) {
				List<TBrIngredient> ingredientList = tBrIngredientService.queryTBrIngredientList(tBrProduct.getId());
				for (TBrIngredient tBrIngredient : ingredientList) {
					iSet.add(tBrIngredient.getId());
				}
				List<TBrEnterpriseVo> elist = tBrEnterpriseService.queryEnterpriseListByProductId(tBrProduct.getId());
				for (TBrEnterpriseVo tBrEnterpriseVo : elist) {
					eSet.add(tBrEnterpriseVo);
				}
				setCatagory(cSet, cMap, cNameMap, tBrProduct);
			}
			bTmp.setPnum(pList.size());
			bTmp.setEenum(eSet.size());
			bTmp.setInum(iSet.size());
			map.put("cValues", cMap.values());
			map.put("cNames", cNameMap.values());
			map.put("elist", eSet);
			map.put("bvo", bTmp);
			map.put("code", 200);
		}
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "oneI", method = { RequestMethod.POST })
	public HashMap<String, Object> oneI(Model model, HttpSession session, long id) {
		HashMap<String, Object> map = Maps.newHashMap();
		map.put("code", 400);
		HashSet<Long> cSet = Sets.newHashSet();
		HashMap<Long, Integer> cMap = Maps.newHashMap();
		HashMap<Long, String> cNameMap = Maps.newHashMap();
		if (id != 0) {
			TBrIngredientQuery ingredientQuery = new TBrIngredientQuery();
			ingredientQuery.setId(id);
			TBrIngredientVo iTmp = tBrIngredientService.queryOne(ingredientQuery);
			if (iTmp != null) {
				tBrIngredientService.getAims(iTmp);
				List<TBrProductVo> plist = tBrProductService.queryProductVoListByIngredientId(id);
				HashSet<Long> bSet = Sets.newHashSet();
				HashSet<Long> eSet = Sets.newHashSet();
				// 产品数
				iTmp.setPnum(plist.size());
				tBrIngredientService.getAims(iTmp);
				for (TBrProductVo tBrProductVo : plist) {
					bSet.add(tBrProductVo.getProductBrandId());
					List<TBrEnterpriseVo> elist = tBrEnterpriseService.queryEnterpriseListByProductId(id);
					for (TBrEnterpriseVo tBrEnterpriseVo : elist) {
						eSet.add(tBrEnterpriseVo.getId());
					}
					setCatagory(cSet, cMap, cNameMap, tBrProductVo);
				}
				// 品牌数
				bSet.remove(null);
				iTmp.setBnum(bSet.size());
				// 企业数
				iTmp.setEenum(eSet.size());
				map.put("cValues", cMap.values());
				map.put("cNames", cNameMap.values());
				map.put("ivo", iTmp);
				map.put("code", 200);
			}
		}
		return map;
	}

}
