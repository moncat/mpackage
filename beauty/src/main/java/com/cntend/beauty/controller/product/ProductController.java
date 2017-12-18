package com.cntend.beauty.controller.product;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.controller.BaseRestControllerHandler;
import com.co.example.entity.comment.aide.Comment;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.brand.TBrProductBrandService;
import com.co.example.service.comment.TBrProductCommentStatisticsService;
import com.co.example.service.product.TBrAimService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrIngredientService;
import com.co.example.service.product.TBrProductImageService;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.product.TBrProductSpecService;

@Controller
@RequestMapping("product")
public class ProductController extends BaseRestControllerHandler<TBrProductQuery> {

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
	
	
	
//	@Override
//	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
//			PageReq pageReq, TBrProductQuery query) {
//		
//		
//		Integer ecType = query.getEcType();
//		if(ecType == 1){
//			query.setUseTmallUrlNotNullFlg(true);
//		}else if(ecType == 2){
//			query.setUseJdUrlNotNullFlg(true);
//		}
//		pageReq.setPageSize(15);
//		return false;
//	}
//

	//基础产品信息
	@Override
	public Boolean showExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrProductQuery t, Long id) {
		TBrProduct one=  tBrProductService.showOneProduct4Mobile(id);
		model.addAttribute(ONE, one);
		return true;
	}
	
	//JD评论
	@ResponseBody
	@RequestMapping(value = "/jdComment", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String,Object> jdComment(Model model,HttpSession session,Long id)throws Exception {
		Map<String, Object> result = result();
		List<Comment> commentList = tBrProductSpecService.getJdComments(id);
		result.put("commentList", commentList);
		return result;
	}
	
	//Tmall评论
	@ResponseBody
	@RequestMapping(value = "/tmallComment", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String,Object> tmallComment(Model model,HttpSession session,Long id)throws Exception {
		Map<String, Object> result = result();
		List<Comment> commentList = tBrProductSpecService.getTmallComments(id);
		result.put("commentList", commentList);
		return result;
	}
	

}


