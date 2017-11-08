package com.co.example.controller.product;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.co.example.entity.product.TBrProductEnterprise;
import com.co.example.entity.product.aide.TBrEnterpriseQuery;
import com.co.example.entity.product.aide.TBrProductEnterpriseQuery;
import com.co.example.service.enterprise.TBrEnterpriseBaseService;
import com.co.example.service.enterprise.TBrEnterpriseLawsuitService;
import com.co.example.service.enterprise.TBrEnterpriseManagerService;
import com.co.example.service.enterprise.TBrEnterprisePunishService;
import com.co.example.service.enterprise.TBrEnterpriseRegisterService;
import com.co.example.service.enterprise.TBrEnterpriseShareholderService;
import com.co.example.service.product.TBrProductEnterpriseService;

@Controller
@RequestMapping("enterprise")
public class EnterpriseController extends BaseControllerHandler<TBrEnterpriseQuery> {

	
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
		List<TBrEnterpriseShareholder> shareholders = tBrEnterpriseShareholderService.queryList(tBrEnterpriseShareholderQuery);
		
		
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
	
		//企业包含的产品 
		@ResponseBody
		@RequestMapping(value = "/product", method = { RequestMethod.GET,RequestMethod.POST })
		public Map<String,Object> product(Model model,HttpSession session,Long id)throws Exception {
			Map<String, Object> result = result();
			TBrProductEnterpriseQuery tBrProductEnterpriseQuery = new TBrProductEnterpriseQuery();
			tBrProductEnterpriseQuery.setEnterpriseId(id);
			tBrProductEnterpriseQuery.setJoinProductFlg(true);
			List<TBrProductEnterprise> productList = tBrProductEnterpriseService.queryList(tBrProductEnterpriseQuery);
			result.put("productList", productList);
			return result;
		}
	
	
}
