package com.co.example.controller.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.utils.PageReq;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.brand.TBrBrand;
import com.co.example.entity.brand.TBrProductBrand;
import com.co.example.entity.brand.aide.TBrProductBrandQuery;
import com.co.example.entity.comment.TBrProductCommentStatistics;
import com.co.example.entity.comment.aide.Comment;
import com.co.example.entity.comment.aide.TBrProductCommentStatisticsQuery;
import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.TBrProductImage;
import com.co.example.entity.product.TBrProductSpec;
import com.co.example.entity.product.aide.ProductConstant;
import com.co.example.entity.product.aide.TBrEnterpriseQuery;
import com.co.example.entity.product.aide.TBrIngredientQuery;
import com.co.example.entity.product.aide.TBrProductImageQuery;
import com.co.example.entity.product.aide.TBrProductImageVo;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.entity.product.aide.TBrProductSpecQuery;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.brand.TBrProductBrandService;
import com.co.example.service.comment.TBrProductCommentStatisticsService;
import com.co.example.service.product.TBrAimService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrIngredientService;
import com.co.example.service.product.TBrProductImageService;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.product.TBrProductSpecService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@Controller
@RequestMapping("product")
public class ProductController extends BaseControllerHandler<TBrProductQuery> {

	@Inject
	TBrIngredientService tBrIngredientService;
	
	@Inject
	TBrProductService tBrProductService;
	
	@Inject
	TBrAimService tBrAimService;
	
	@Inject
	TBrProductImageService tBrProductImageService;
	
	@Inject
	TBrEnterpriseService tBrEnterpriseService;
	
	@Inject
	TBrProductBrandService tBrProductBrandService;
	
	@Inject
	TBrBrandService tBrBrandService;
	
	@Inject
	TBrProductSpecService tBrProductSpecService;
	
	@Inject
	TBrProductCommentStatisticsService tBrProductCommentStatisticsService;
	
	
	
	
	@Override
	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, TBrProductQuery query) {
		pageReq.setPageSize(15);
		return super.listExt(model, session, request, response, pageReq, query);
	}


	@Override
	public Boolean showExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrProductQuery t, Long id) {
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setId(id);
		TBrProduct one = tBrProductService.queryOne(tBrProductQuery);
		TBrIngredientQuery tBrIngredientQuery = new TBrIngredientQuery();
		tBrIngredientQuery.setProductId(id);
		tBrIngredientQuery.setJoinFlg(true);
		List<TBrIngredient> ingredientList = tBrIngredientService.queryList(tBrIngredientQuery);
		//装饰成分信息
		tBrIngredientService.decorateColour(ingredientList);
		Byte oldImage = 12;
		Byte jdImage = 3;
		Byte tmallImage = 4;
		//图片信息
		TBrProductImageQuery tBrProductImageQuery = new TBrProductImageQuery();
		tBrProductImageQuery.setProductId(id);
		tBrProductImageQuery.setSource(oldImage);
		List<TBrProductImage> productImageList = tBrProductImageService.queryList(tBrProductImageQuery);
		if(ProductConstant.PRODUCT_SOURCE_CFDA.equals(one.getSource())){
			productImageList.forEach(item->{
				String cfdaImageId = item.getCfdaImageId();
				String cfdaSsid = item.getCfdaSsid();
				TBrProductImageVo itemVo = (TBrProductImageVo)item;
				itemVo.setDownloadUrl(ProductConstant.CFDA_PRODUCT_IMAGE_DOWNLOAD.replace("@1", cfdaImageId).replace("@2", cfdaSsid));
				itemVo.setImageUrl(ProductConstant.CFDA_PRODUCT_IMAGE_SHOW.replace("@1",cfdaImageId));
			});
		}
		
		tBrProductImageQuery.setSource(jdImage);
		List<TBrProductImage> jdImageList = tBrProductImageService.queryList(tBrProductImageQuery);
		
		tBrProductImageQuery.setSource(tmallImage);
		List<TBrProductImage> tmallImageList = tBrProductImageService.queryList(tBrProductImageQuery);
		
		
		one = tBrProductService.getStatisticsInfo(one, ingredientList);
		
		//企业名称，到实际生产企业表查询，如果企业为实际生产企业，则加上链接
		String enterpriseName = one.getEnterpriseName();
		
		TBrEnterpriseQuery tBrEnterpriseQuery1 = new TBrEnterpriseQuery();
		tBrEnterpriseQuery1.setEnterpriseName(enterpriseName);
		List<TBrEnterprise> enterpriseList1 = tBrEnterpriseService.queryList(tBrEnterpriseQuery1);
		if(CollectionUtils.isNotEmpty(enterpriseList1)){
			TBrEnterprise oneEnterprise = enterpriseList1.get(0);
			model.addAttribute("oneEnterprise", oneEnterprise);
		}
		
		
		//实际生产企业
		TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
		tBrEnterpriseQuery.setProductId(id);
		tBrEnterpriseQuery.setJoinFlg(true);
		List<TBrEnterprise> enterpriseList = tBrEnterpriseService.queryList(tBrEnterpriseQuery);
		
		TBrProductBrandQuery tBrProductBrandQuery = new TBrProductBrandQuery();
		tBrProductBrandQuery.setProductId(id);
		TBrProductBrand tBrProductBrand = tBrProductBrandService.queryOne(tBrProductBrandQuery);
		TBrBrand brand = null;
		if(tBrProductBrand !=null){
			brand = tBrBrandService.queryById(tBrProductBrand.getBrandId());
		}
		
		
		model.addAttribute(ONE, one);
		model.addAttribute("ingredientList", ingredientList);
		model.addAttribute("productImageList", productImageList);
		model.addAttribute("jdImageList", jdImageList);
		model.addAttribute("tmallImageList", tmallImageList);
		model.addAttribute("enterpriseList", enterpriseList);
		model.addAttribute("brand", brand);
		return true;
	}
	

	//JD Tmall 产品规格
	@ResponseBody
	@RequestMapping(value = "/spec", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String,Object> spec(Model model,HttpSession session,Long id)throws Exception {
		Map<String, Object> result = result();
		TBrProductSpecQuery tBrProductSpecQuery = new TBrProductSpecQuery();
		tBrProductSpecQuery.setPid(id);
		List<TBrProductSpec> productSpecList = tBrProductSpecService.queryList(tBrProductSpecQuery);
		List<Map<String,String>> specList = Lists.newArrayList();
		HashMap<String, String> kvMap  = null;
		String specKeyName = null;
		String specValueNames = "";
		HashSet<String> keyNameSet = Sets.newHashSet();
		for (TBrProductSpec tBrProductSpec : productSpecList) {
			specKeyName = tBrProductSpec.getSpecKeyName();
			keyNameSet.add(specKeyName);
		}
		for (String keyName : keyNameSet) {
			for (TBrProductSpec tBrProductSpec : productSpecList) {
				specKeyName = tBrProductSpec.getSpecKeyName();
				if(StringUtils.equals(keyName, specKeyName)){
					specValueNames = specValueNames+tBrProductSpec.getSpecValueName()+" ";
				}
			}
			kvMap = Maps.newHashMap();
			kvMap.put("keyName", keyName);
			kvMap.put("specValueNames", specValueNames);
			specValueNames = "";
			specList.add(kvMap);
		}
		
		
		result.put("specList", specList);
		return result;
	}
	
	//评论,评论统计 
	@ResponseBody
	@RequestMapping(value = "/comment", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String,Object> comment(Model model,HttpSession session,Long id)throws Exception {
		
		//评论统计信息
		Map<String, Object> result = result();
		TBrProductCommentStatisticsQuery tBrProductCommentStatisticsQuery = new TBrProductCommentStatisticsQuery();
		tBrProductCommentStatisticsQuery.setPid(id);
		TBrProductCommentStatistics cs = tBrProductCommentStatisticsService.queryOne(tBrProductCommentStatisticsQuery);
		//评论统计信息
		List<Comment> commentList = tBrProductSpecService.getComment(id);
		result.put("cs", cs);
		result.put("commentList", commentList);
		return result;
	}
	
	
	
}









