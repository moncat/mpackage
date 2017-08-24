package com.co.example.controller.product;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.base.controller.BaseControllerHandler;
import com.co.example.common.utils.PageReq;
import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.TBrProductImage;
import com.co.example.entity.product.aide.ProductConstant;
import com.co.example.entity.product.aide.TBrEnterpriseQuery;
import com.co.example.entity.product.aide.TBrIngredientQuery;
import com.co.example.entity.product.aide.TBrProductImageQuery;
import com.co.example.entity.product.aide.TBrProductImageVo;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.product.TBrAimService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrIngredientService;
import com.co.example.service.product.TBrProductImageService;
import com.co.example.service.product.TBrProductService;

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
	
	
	@Override
	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, TBrProductQuery query) {
		pageReq.setPageSize(15);
		return super.listExt(model, session, request, response, pageReq, query);
	}


	@Override
	public Boolean showExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrProductQuery t, Long id) {
		TBrProduct one = tBrProductService.queryById(id);
		TBrIngredientQuery tBrIngredientQuery = new TBrIngredientQuery();
		tBrIngredientQuery.setProductId(id);
		tBrIngredientQuery.setJoinFlg(true);
		List<TBrIngredient> ingredientList = tBrIngredientService.queryList(tBrIngredientQuery);
		//装饰成分信息
		tBrIngredientService.decorateColour(ingredientList);
		
		//图片信息
		TBrProductImageQuery tBrProductImageQuery = new TBrProductImageQuery();
		tBrProductImageQuery.setProductId(id);
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
		
		//实际生产企业
		TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
		tBrEnterpriseQuery.setProductId(id);
		tBrEnterpriseQuery.setJoinFlg(true);
		List<TBrEnterprise> enterpriseList = tBrEnterpriseService.queryList(tBrEnterpriseQuery);
		
		model.addAttribute(ONE, one);
		model.addAttribute("ingredientList", ingredientList);
		model.addAttribute("productImageList", productImageList);
		model.addAttribute("enterpriseList", enterpriseList);
		return true;
	}
	
}









