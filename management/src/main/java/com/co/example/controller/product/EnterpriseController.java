package com.co.example.controller.product;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.PageReq;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.enterprise.TBrEnterpriseBase;
import com.co.example.entity.enterprise.TBrEnterpriseLawsuit;
import com.co.example.entity.enterprise.TBrEnterpriseManager;
import com.co.example.entity.enterprise.TBrEnterprisePunish;
import com.co.example.entity.enterprise.TBrEnterpriseRegister;
import com.co.example.entity.enterprise.TBrEnterpriseShareholder;
import com.co.example.entity.enterprise.aide.TBrEnterpriseBaseQuery;
import com.co.example.entity.enterprise.aide.TBrEnterpriseLawsuitQuery;
import com.co.example.entity.enterprise.aide.TBrEnterpriseManagerQuery;
import com.co.example.entity.enterprise.aide.TBrEnterprisePunishQuery;
import com.co.example.entity.enterprise.aide.TBrEnterpriseRegisterQuery;
import com.co.example.entity.enterprise.aide.TBrEnterpriseShareholderQuery;
import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.TBrProductEnterprise;
import com.co.example.entity.product.aide.TBrEnterpriseQuery;
import com.co.example.entity.product.aide.TBrProductEnterpriseQuery;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.enterprise.TBrEnterpriseBaseService;
import com.co.example.service.enterprise.TBrEnterpriseLawsuitService;
import com.co.example.service.enterprise.TBrEnterpriseManagerService;
import com.co.example.service.enterprise.TBrEnterprisePunishService;
import com.co.example.service.enterprise.TBrEnterpriseRegisterService;
import com.co.example.service.enterprise.TBrEnterpriseShareholderService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrProductEnterpriseService;
import com.co.example.service.product.TBrProductService;

@Controller
@RequestMapping("enterprise")
public class EnterpriseController extends BaseControllerHandler<TBrEnterpriseQuery> {

	@Resource
	TBrEnterpriseService tBrEnterpriseService;

	@Resource
	TBrEnterpriseBaseService tBrEnterpriseBaseService;

	@Resource
	TBrEnterpriseRegisterService tBrEnterpriseRegisterService;

	@Resource
	TBrEnterpriseManagerService tBrEnterpriseManagerService;

	@Resource
	TBrEnterpriseShareholderService tBrEnterpriseShareholderService;

	@Resource
	TBrEnterpriseLawsuitService tBrEnterpriseLawsuitService;

	@Resource
	TBrEnterprisePunishService tBrEnterprisePunishService;

	@Resource
	TBrProductEnterpriseService tBrProductEnterpriseService;

	@Resource
	TBrProductService tBrProductService;

	@Override
	public Boolean showExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrEnterpriseQuery t, Long id) {

		TBrEnterpriseBaseQuery tBrEnterpriseBaseQuery = new TBrEnterpriseBaseQuery();
		tBrEnterpriseBaseQuery.setEid(id);
		TBrEnterpriseBase base = tBrEnterpriseBaseService.queryOne(tBrEnterpriseBaseQuery);

		TBrEnterpriseRegisterQuery tBrEnterpriseRegisterQuery = new TBrEnterpriseRegisterQuery();
		tBrEnterpriseRegisterQuery.setEid(id);
		TBrEnterpriseRegister register = tBrEnterpriseRegisterService.queryOne(tBrEnterpriseRegisterQuery);

		TBrEnterpriseManagerQuery tBrEnterpriseManagerQuery = new TBrEnterpriseManagerQuery();
		tBrEnterpriseManagerQuery.setEid(id);
		List<TBrEnterpriseManager> managers = tBrEnterpriseManagerService.queryList(tBrEnterpriseManagerQuery);

		TBrEnterpriseShareholderQuery tBrEnterpriseShareholderQuery = new TBrEnterpriseShareholderQuery();
		tBrEnterpriseShareholderQuery.setEid(id);
		List<TBrEnterpriseShareholder> shareholders = tBrEnterpriseShareholderService
				.queryList(tBrEnterpriseShareholderQuery);

		TBrEnterpriseLawsuitQuery tBrEnterpriseLawsuitQuery = new TBrEnterpriseLawsuitQuery();
		tBrEnterpriseLawsuitQuery.setEid(id);
		List<TBrEnterpriseLawsuit> lawsuits = tBrEnterpriseLawsuitService.queryList(tBrEnterpriseLawsuitQuery);

		TBrEnterprisePunishQuery tBrEnterprisePunishQuery = new TBrEnterprisePunishQuery();
		tBrEnterprisePunishQuery.setEid(id);
		List<TBrEnterprisePunish> punishs = tBrEnterprisePunishService.queryList(tBrEnterprisePunishQuery);

		model.addAttribute("base", base);
		model.addAttribute("register", register);
		model.addAttribute("managers", managers);
		model.addAttribute("shareholders", shareholders);
		model.addAttribute("lawsuits", lawsuits);
		model.addAttribute("punishs", punishs);
		return super.showExt(model, session, request, response, t, id);
	}

	// 企业包含的产品
	@ResponseBody
	@RequestMapping(value = "/product", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> product(Model model, HttpSession session, Long id) throws Exception {
		Map<String, Object> result = result();
		TBrProductEnterpriseQuery tBrProductEnterpriseQuery = new TBrProductEnterpriseQuery();
		tBrProductEnterpriseQuery.setEnterpriseId(id);
		tBrProductEnterpriseQuery.setJoinProductFlg(true);
		List<TBrProductEnterprise> productList = tBrProductEnterpriseService.queryList(tBrProductEnterpriseQuery);
		result.put("productList", productList);
		return result;
	}

	@RequestMapping(value = "/list2", method = { RequestMethod.GET, RequestMethod.POST })
	public String list2(Model model, PageReq pageReq, TBrEnterpriseQuery query) throws Exception {
		query.setDelFlg(Constant.NO);
		Integer enterpriseType = query.getEnterpriseType();
		query.setJoinRegFlg(true);
		// 1 默认全部 2生产企业 3运营企业
		if(enterpriseType !=null){
			if (enterpriseType == 2) {
				query.setIsProduct(Constant.YES);
			} else if (enterpriseType == 3) {
				query.setIsBus(Constant.YES);
			}
		}
		Page<TBrEnterprise> page = tBrEnterpriseService.queryPageList(query, pageReq);
		model.addAttribute(QUERY, query);
		model.addAttribute(PAGE, page);
		return "enterprise/list2";
	}

	@RequestMapping(value = "/show2/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String show(Model model, @PathVariable Long id) throws Exception {

		TBrEnterpriseBaseQuery tBrEnterpriseBaseQuery = new TBrEnterpriseBaseQuery();
		tBrEnterpriseBaseQuery.setEid(id);
		TBrEnterpriseBase base = tBrEnterpriseBaseService.queryOne(tBrEnterpriseBaseQuery);

		TBrEnterpriseRegisterQuery tBrEnterpriseRegisterQuery = new TBrEnterpriseRegisterQuery();
		tBrEnterpriseRegisterQuery.setEid(id);
		TBrEnterpriseRegister register = tBrEnterpriseRegisterService.queryOne(tBrEnterpriseRegisterQuery);

		TBrEnterpriseManagerQuery tBrEnterpriseManagerQuery = new TBrEnterpriseManagerQuery();
		tBrEnterpriseManagerQuery.setEid(id);
		List<TBrEnterpriseManager> managers = tBrEnterpriseManagerService.queryList(tBrEnterpriseManagerQuery);

		TBrEnterpriseShareholderQuery tBrEnterpriseShareholderQuery = new TBrEnterpriseShareholderQuery();
		tBrEnterpriseShareholderQuery.setEid(id);
		List<TBrEnterpriseShareholder> shareholders = tBrEnterpriseShareholderService
				.queryList(tBrEnterpriseShareholderQuery);

		TBrEnterpriseLawsuitQuery tBrEnterpriseLawsuitQuery = new TBrEnterpriseLawsuitQuery();
		tBrEnterpriseLawsuitQuery.setEid(id);
		List<TBrEnterpriseLawsuit> lawsuits = tBrEnterpriseLawsuitService.queryList(tBrEnterpriseLawsuitQuery);

		TBrEnterprisePunishQuery tBrEnterprisePunishQuery = new TBrEnterprisePunishQuery();
		tBrEnterprisePunishQuery.setEid(id);
		List<TBrEnterprisePunish> punishs = tBrEnterprisePunishService.queryList(tBrEnterprisePunishQuery);

		model.addAttribute("base", base);
		model.addAttribute("register", register);
		model.addAttribute("managers", managers);
		model.addAttribute("shareholders", shareholders);
		model.addAttribute("lawsuits", lawsuits);
		model.addAttribute("punishs", punishs);
		TBrEnterprise tBrEnterprise = tBrEnterpriseService.queryById(id);
		model.addAttribute(ONE, tBrEnterprise);
		return "enterprise/show2";
	}

	@ResponseBody
	@RequestMapping(value = "/updateStatus", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> updateStatus(Model model, Long id, Boolean flg) throws Exception {
		Map<String, Object> result = result();
		TBrEnterprise tBrEnterprise = new TBrEnterprise();
		tBrEnterprise.setId(id);
		if (flg) {
			tBrEnterprise.setIsChoice(Constant.YES);
		} else {
			tBrEnterprise.setIsChoice(Constant.NO);
		}
		tBrEnterpriseService.updateByIdSelective(tBrEnterprise);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/count/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> count(Model model, @PathVariable Long id) {
		Long num = 0l;
		TBrEnterprise one = tBrEnterpriseService.queryById(id);
		Byte isProduct = one.getIsProduct();
		Byte isBus = one.getIsBus();
		if (isProduct == Constant.YES) {
			TBrProductEnterpriseQuery tBrProductEnterpriseQuery = new TBrProductEnterpriseQuery();
			tBrProductEnterpriseQuery.setEnterpriseId(id);
			long queryCount = tBrProductEnterpriseService.queryCount(tBrProductEnterpriseQuery);
			num += queryCount;
		} else if (isBus == Constant.YES) {
			TBrProductQuery tBrProductQuery = new TBrProductQuery();
			tBrProductQuery.setEnterpriseId(id);
			long queryCount = tBrProductService.queryCount(tBrProductQuery);
			num += queryCount;
		}
		Map<String, Object> result = result();
		result.put("num", num);
		return result;
	}

}
