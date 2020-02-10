package com.co.example.controller.manifest;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
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
import com.co.example.common.utils.PageReq;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.manifest.TBrManifest;
import com.co.example.entity.manifest.TBrManifestAuth;
import com.co.example.entity.manifest.TBrManifestBrand;
import com.co.example.entity.manifest.TBrManifestCategory;
import com.co.example.entity.manifest.TBrManifestEnterprise;
import com.co.example.entity.manifest.TBrManifestIngredient;
import com.co.example.entity.manifest.TBrManifestProduct;
import com.co.example.entity.manifest.aide.TBrManifestAuthQuery;
import com.co.example.entity.manifest.aide.TBrManifestBrandQuery;
import com.co.example.entity.manifest.aide.TBrManifestCategoryQuery;
import com.co.example.entity.manifest.aide.TBrManifestDataQuery;
import com.co.example.entity.manifest.aide.TBrManifestEnterpriseQuery;
import com.co.example.entity.manifest.aide.TBrManifestIngredientQuery;
import com.co.example.entity.manifest.aide.TBrManifestProductQuery;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.manifest.TBrManifestAuthService;
import com.co.example.service.manifest.TBrManifestBrandService;
import com.co.example.service.manifest.TBrManifestCategoryService;
import com.co.example.service.manifest.TBrManifestDataService;
import com.co.example.service.manifest.TBrManifestEnterpriseService;
import com.co.example.service.manifest.TBrManifestIngredientService;
import com.co.example.service.manifest.TBrManifestProductService;
import com.co.example.service.manifest.TBrManifestService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrProductService;
import com.co.example.utils.SessionUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("manifestData")
public class ManifestDataController extends BaseControllerHandler<TBrManifestDataQuery> {
	
	@Inject
	TBrManifestDataService tBrManifestDataService;	
	@Inject
	TBrProductService tBrProductService;	
	@Inject
	TBrEnterpriseService tBrEnterpriseService;
	@Inject
	TBrBrandService tBrBrandService;	
	@Inject
	TBrManifestProductService tBrManifestProductService;	
	@Inject
	TBrManifestBrandService tBrManifestBrandService;	
	@Inject
	TBrManifestEnterpriseService tBrManifestEnterpriseService;	
	@Inject
	TBrManifestIngredientService tBrManifestIngredientService;	
	@Inject
	TBrManifestCategoryService tBrManifestCategoryService;
	@Inject
	TBrManifestService tBrManifestService;
	@Inject
	TBrManifestAuthService tBrManifestAuthService;	
	
	/**
	 * v1版 仅仅关联，在v2版启用后，该方法弃用
	 * @param model
	 * @param session
	 * @param mid
	 * @param ids
	 * @return
	 */
	@Deprecated
	@ResponseBody
	@RequestMapping(value = "/conn", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> conn(Model model, HttpSession session ,Long mid, @RequestParam(value = "ids[]") Long[] ids) {
		Long createBy = SessionUtil.getAdminId(session);
		Map<String, Object> result = tBrManifestDataService.connManifest(createBy, mid, ids);
		return result;
	}
	
	/**
	 * 在v2版启用
	 * @param model
	 * @param session
	 * @param mid
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/connV2", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> connV2(Model model, HttpSession session ,Long mid, @RequestParam(value = "ids[]") Long[] ids) {
		Long createBy = SessionUtil.getAdminId(session);
		Map<String, Object> result = tBrManifestDataService.connManifest(createBy, mid, ids);
		//更新主表状态，数据已经初始化，准备加载
		TBrManifest tBrManifest = new TBrManifest();
		tBrManifest.setId(mid);
		tBrManifest.setStatus(Constant.TASK_STATUS_TODO);	
		tBrManifestService.updateByIdSelective(tBrManifest);
		
		//更新授权表状态，需要批量更新， 包含哪些未有数据，便已分享的情况
		TBrManifestAuthQuery tBrManifestAuthQuery = new TBrManifestAuthQuery();
		tBrManifestAuthQuery.setManifestId(mid);
		List<TBrManifestAuth> authList = tBrManifestAuthService.queryList(tBrManifestAuthQuery);
		for (TBrManifestAuth old : authList) {
			TBrManifestAuth tmp = new TBrManifestAuth();
			tmp.setId(old.getId());
			tmp.setStatus(Constant.TASK_STATUS_TODO);
			tBrManifestAuthService.updateByIdSelective(tmp);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/mp/{manifestId}", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> mp(Model model, HttpSession session,PageReq pageReq, @PathVariable Long manifestId) {
		Map<String, Object> result = result();
		TBrManifestProductQuery tBrManifestProductQuery = new TBrManifestProductQuery();
		tBrManifestProductQuery.setManifestId(manifestId);
		Page<TBrManifestProduct>  page = tBrManifestProductService.queryPageList(tBrManifestProductQuery, pageReq);
		result.put("page", page);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/mb/{manifestId}", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> mb(Model model, HttpSession session,PageReq pageReq, @PathVariable Long manifestId) {
		Map<String, Object> result = result();
		TBrManifestBrandQuery tBrManifestBrandQuery = new TBrManifestBrandQuery();
		tBrManifestBrandQuery.setManifestId(manifestId);
		Page<TBrManifestBrand> page = tBrManifestBrandService.queryPageList(tBrManifestBrandQuery, pageReq);
		result.put("page", page);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/me/{manifestId}", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> me(Model model, HttpSession session,PageReq pageReq, @PathVariable Long manifestId) {
		Map<String, Object> result = result();
		TBrManifestEnterpriseQuery tBrManifestEnterpriseQuery = new TBrManifestEnterpriseQuery();
		tBrManifestEnterpriseQuery.setManifestId(manifestId);
		Page<TBrManifestEnterprise> page = tBrManifestEnterpriseService.queryPageList(tBrManifestEnterpriseQuery, pageReq);
		result.put("page", page);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/mc/{manifestId}", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> mc(Model model, HttpSession session,PageReq pageReq, @PathVariable Long manifestId) {
		Map<String, Object> result = result();
		TBrManifestCategoryQuery tBrManifestCategoryQuery = new TBrManifestCategoryQuery();
		tBrManifestCategoryQuery.setManifestId(manifestId);
		Page<TBrManifestCategory> page = tBrManifestCategoryService.queryPageList(tBrManifestCategoryQuery, pageReq);
		result.put("page", page);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/mi/{manifestId}", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> mi(Model model, HttpSession session,PageReq pageReq, @PathVariable Long manifestId) {
		Map<String, Object> result = result();
		TBrManifestIngredientQuery tBrManifestIngredientQuery = new TBrManifestIngredientQuery();
		tBrManifestIngredientQuery.setManifestId(manifestId);
		Page<TBrManifestIngredient> page = tBrManifestIngredientService.queryPageList(tBrManifestIngredientQuery, pageReq);
		result.put("page", page);
		return result;
	}
	
	
	
	
}
