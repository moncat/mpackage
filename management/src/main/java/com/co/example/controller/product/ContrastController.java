package com.co.example.controller.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.entity.brand.TBrBrand;
import com.co.example.entity.brand.TBrProductBrand;
import com.co.example.entity.brand.aide.TBrBrandQuery;
import com.co.example.entity.brand.aide.TBrBrandVo;
import com.co.example.entity.brand.aide.TBrProductBrandQuery;
import com.co.example.entity.enterprise.TBrEnterprisePermission;
import com.co.example.entity.label.TBrProductLabel;
import com.co.example.entity.label.aide.TBrProductLabelQuery;
import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrEnterpriseQuery;
import com.co.example.entity.product.aide.TBrEnterpriseVo;
import com.co.example.entity.product.aide.TBrIngredientQuery;
import com.co.example.entity.product.aide.TBrIngredientVo;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.entity.product.aide.TBrProductVo;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.brand.TBrProductBrandService;
import com.co.example.service.enterprise.TBrEnterprisePermissionService;
import com.co.example.service.label.TBrProductLabelService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrIngredientService;
import com.co.example.service.product.TBrProductService;
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
	TBrEnterprisePermissionService tBrEnterprisePermissionService;

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
	@RequestMapping(value = "label/{id1}/{id2}", method = {  RequestMethod.GET,RequestMethod.POST })
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
		TBrProduct statisticsInfo = tBrProductService.getStatisticsInfo(tBrProductService.queryOne(tBrProductQuery), ingredientList);
		map.put("ingredientList", ingredientList);
		map.put("statisticsInfo", statisticsInfo);
		map.put("productScore", productScore);
		return map;
	}

	
	@RequestMapping(value = "choice", method = { RequestMethod.GET, RequestMethod.POST })
	public String choice(Model model) throws Exception {
		return "contrast/choice";
	}
	
	
	
	@RequestMapping(value = "detailE/{ids}", method = { RequestMethod.GET })
	public String detailE(Model model, HttpSession session, @PathVariable String ids) {
		ArrayList<TBrEnterpriseVo> list = Lists.newArrayList();
		if (StringUtils.isNotBlank(ids)) {
			String[] idArr = ids.split("_");
			for (String idStr : idArr) {
				if (NumberUtils.isDigits(idStr)) {
					long id = Long.parseLong(idStr);
					if (id != 0) {
						TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
						tBrEnterpriseQuery.setId(id);
						TBrEnterpriseVo eTmp = tBrEnterpriseService.queryOne(tBrEnterpriseQuery);
						TBrEnterprisePermission permission = tBrEnterprisePermissionService.queryVoByEId(id);
						if (eTmp != null) {
							//许可信息
							eTmp.setPermission(permission);
							//产品数
							List<TBrProductVo> plist = tBrProductService.queryProductVoListByRealEnterpriseId(id);
							eTmp.setPnum(plist.size());
							//品牌数
							HashSet<Long> bSet = Sets.newHashSet();
							for (TBrProductVo tBrProductVo : plist) {
								bSet.add(tBrProductVo.getId());
							}
							eTmp.setBnum(bSet.size());
							list.add(eTmp);
						}
					}
				}
			}
		}
		model.addAttribute("list", list);
		return "contrast/detailE";
	}
	
	@RequestMapping(value = "detailB/{ids}", method = { RequestMethod.GET })
	public String detailB(Model model, HttpSession session, @PathVariable String ids) {
		ArrayList<TBrBrandVo> list = Lists.newArrayList();
		if (StringUtils.isNotBlank(ids)) {
			String[] idArr = ids.split("_");
			for (String idStr : idArr) {
				if (NumberUtils.isDigits(idStr)) {
					long id = Long.parseLong(idStr);
					if (id != 0) {
						TBrBrandQuery tBrBrandQuery = new TBrBrandQuery();
						tBrBrandQuery.setId(id);
						TBrBrandVo bTmp = tBrBrandService.queryOne(tBrBrandQuery);
						if (bTmp != null) {
							list.add(bTmp);
						}
					}
				}
			}
		}
		model.addAttribute("list", list);
		return "contrast/detailB";
	}
	
	
	@RequestMapping(value = "detailI/{ids}", method = { RequestMethod.GET })
	public String detailI(Model model, HttpSession session, @PathVariable String ids) {
		ArrayList<TBrIngredientVo> list = Lists.newArrayList();
		if (StringUtils.isNotBlank(ids)) {
			String[] idArr = ids.split("_");
			for (String idStr : idArr) {
				if (NumberUtils.isDigits(idStr)) {
					long id = Long.parseLong(idStr);
					if (id != 0) {
						TBrIngredientQuery ingredientQuery = new TBrIngredientQuery();
						ingredientQuery.setId(id);
						TBrIngredientVo iTmp = tBrIngredientService.queryOne(ingredientQuery);
						if (iTmp != null) {
							tBrIngredientService.getAims(iTmp);
							List<TBrProductVo> plist = tBrProductService.queryProductVoListByIngredientId(id);
	//						HashSet<Long> bSet = Sets.newHashSet();
							for (TBrProductVo tBrProductVo : plist) {
								
								
	//							list.add(bTmp);
								
							}
							//产品数
							//品牌数
							//企业数
							
						}
					}
				}
			}
		}
		model.addAttribute("list", list);
		return "contrast/detailI";
	}
	
	
	
	
}
