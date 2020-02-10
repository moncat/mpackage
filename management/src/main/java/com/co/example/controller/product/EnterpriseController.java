package com.co.example.controller.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.assertj.core.util.Sets;
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
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.brand.TBrBrand;
import com.co.example.entity.brand.aide.TBrBrandPo;
import com.co.example.entity.common.KvBean;
import com.co.example.entity.enterprise.TBrEnterpriseBase;
import com.co.example.entity.enterprise.TBrEnterpriseLawsuit;
import com.co.example.entity.enterprise.TBrEnterpriseManager;
import com.co.example.entity.enterprise.TBrEnterprisePermission;
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
import com.co.example.entity.product.aide.TBrProductVo;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.category.TBrCategoryService;
import com.co.example.service.enterprise.TBrEnterpriseBaseService;
import com.co.example.service.enterprise.TBrEnterpriseLawsuitService;
import com.co.example.service.enterprise.TBrEnterpriseManagerService;
import com.co.example.service.enterprise.TBrEnterprisePermissionService;
import com.co.example.service.enterprise.TBrEnterprisePunishService;
import com.co.example.service.enterprise.TBrEnterpriseRegisterService;
import com.co.example.service.enterprise.TBrEnterpriseShareholderService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrProductEnterpriseService;
import com.co.example.service.product.TBrProductService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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
	
	@Resource
	TBrEnterprisePermissionService tBrEnterprisePermissionService;
	
	@Resource
	TBrBrandService tBrBrandService;
	
	@Resource
	TBrCategoryService tBrCategoryService;

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
		// 1 默认全部 2生产企业 3运营企业
		if(enterpriseType !=null){
			if (enterpriseType == 2) {
				query.setIsProduct(Constant.YES);
			} else if (enterpriseType == 3) {
				query.setIsBus(Constant.YES);
			}
		}
		long queryCount = tBrEnterpriseService.queryCount(query);
		query.setJoinRegFlg(true);
		Page<TBrEnterprise> page = tBrEnterpriseService.queryPageList(query, pageReq,queryCount);
		model.addAttribute(QUERY, query);
		model.addAttribute(PAGE, page);
		return "enterprise/list2";
	}

	@RequestMapping(value = "/show2/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String show(Model model, @PathVariable Long id) throws Exception {

		TBrEnterpriseBase base = tBrEnterpriseBaseService.queryEnterpriseBaseServiceByEId(id);

		TBrEnterpriseRegister register = tBrEnterpriseRegisterService.queryVoByEId(id);

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

	@RequestMapping(value = "/index/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String index(Model model, HttpSession session,  @PathVariable Long id) {
		TBrEnterprise one = tBrEnterpriseService.queryById(id);
		TBrEnterprisePermission permission = tBrEnterprisePermissionService.queryVoByEId(id);
		TBrEnterpriseRegister register = tBrEnterpriseRegisterService.queryVoByEId(id);
		TBrEnterpriseBase base = tBrEnterpriseBaseService.queryEnterpriseBaseServiceByEId(id);
		model.addAttribute(ONE, one);
		model.addAttribute("permission", permission);
		model.addAttribute("register", register);
		model.addAttribute("base", base);
		return "enterprise/index";
	}
	
	@ResponseBody
	@RequestMapping(value = "/base/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> base(Model model, @PathVariable Long id) {
		Map<String, Object> result = result();
		List<TBrProductVo> productList= tBrProductService.queryProductVoListByRealEnterpriseId(id);
		HashSet<Long> brandKeySet = Sets.newHashSet();
		HashSet<Long> categoryKeySet = Sets.newHashSet();
		productList.forEach(item ->{
			categoryKeySet.add(item.getCategoryId());
			TBrBrand  one = tBrBrandService.queryByProductId(item.getId());
			if(one !=null){
				brandKeySet.add(one.getId());
			}
		});
		categoryKeySet.remove(null);
		result.put("brandNum", brandKeySet.size());
		result.put("categoryNum", categoryKeySet.size());
		result.put("productNum", productList.size());
		result.put("info", "获取完成！");
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/productList/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> productList(Model model, @PathVariable Long id, PageReq pageReq) {
		Map<String, Object> result = result();
		Page<TBrProductVo> page = tBrProductService.queryProductVoPageByRealEnterpriseId(id,pageReq);
		result.put("page", page);
		return result;
	}
	
	/**
	 * 品类占比 饼图
	 * 产品数 比率
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/categoryPercentage/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> categoryPercentage(Model model, @PathVariable Long id) {
		List<TBrProductVo> productList = tBrProductService.queryProductVoListByRealEnterpriseId(id);
		HashSet<Object> set = Sets.newHashSet();
		HashMap<Long, String> nameMap = Maps.newHashMap();
		HashMap<Long, Integer> numMap = Maps.newHashMap();
		List<KvBean> series = Lists.newArrayList();
		for (TBrProductVo tBrProduct : productList) {
			TBrBrand tBrBrand = tBrBrandService.queryByProductId(tBrProduct.getId());
			tBrProduct.setTBrBrand(tBrBrand);
			Long categoryId = tBrProduct.getCategoryId();
			if (categoryId != null) {
				if (set.contains(categoryId)) {
					Integer integer = numMap.get(categoryId);
					numMap.replace(categoryId, integer + 1);
				} else {
					set.add(categoryId);
					numMap.put(categoryId, 1);
					nameMap.put(categoryId, tBrProduct.getCategoryName());
				}
			}
		}

		List<Map.Entry<Long, Integer>> list = new ArrayList<Map.Entry<Long, Integer>>(numMap.entrySet());
		// 然后通过比较器来实现排序
		Collections.sort(list, new Comparator<Map.Entry<Long, Integer>>() {
			// 降序排序
			public int compare(Entry<Long, Integer> o1, Entry<Long, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		KvBean kvBean = null;
		for (Entry<Long, Integer> map : list) {
			kvBean = new KvBean();
			kvBean.setName(nameMap.get(map.getKey()));
			kvBean.setValue(numMap.get(map.getKey()));
			series.add(kvBean);
		}
		Map<String, Object> result = result();
		result.put("legend", nameMap.values());
		result.put("series", series);
		result.put("productList", productList);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/brandList/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> moreData(Model model, @PathVariable Long id, PageReq pageReq) {
		List<TBrProductVo> productList = tBrProductService.queryProductVoListByRealEnterpriseId(id);
		HashSet<Integer> levelSet = Sets.newHashSet();
		HashSet<Long> brandNumSet = Sets.newHashSet();
		HashMap<Long, Integer> brandNumMap = Maps.newHashMap();
		HashMap<Integer, Integer> levelProductNumMap = Maps.newHashMap();
		HashMap<Long, TBrBrand> brandMap = Maps.newHashMap();
		
		for (TBrProductVo tBrProduct : productList) {
			TBrBrand tBrBrand = tBrBrandService.queryByProductId(tBrProduct.getId());
			if(tBrBrand !=null){
				Byte levelTmp =tBrBrand.getLevel();
				if (levelTmp != null) {
					Integer level = (int)levelTmp;
					if (levelSet.contains(level)) {
						Integer integer = levelProductNumMap.get(level);
						levelProductNumMap.replace((int)level, integer + 1);
					} else {
						levelSet.add((int)level);
						levelProductNumMap.put((int)level, 1);
					}
				}
				//去重品牌，计算每个品牌产品数量
				Long brandId = tBrBrand.getId();
				if (brandId != null) {
					if (brandNumSet.contains(brandId)) {
						Integer integer = brandNumMap.get(brandId);
						brandNumMap.replace(brandId, integer + 1);
					} else {
						brandNumSet.add(brandId);
						brandNumMap.put(brandId, 1);
						brandMap.put(brandId, tBrBrand);
					}
				}
			}
		}
		
		//计算每个等级的品牌数占比,计算该数据时需要先品牌去重
		HashMap<Byte, Integer> levelBrandNumMap = Maps.newHashMap();
		for (TBrBrand brand : brandMap.values()){
			Byte level = brand.getLevel();
			if(levelBrandNumMap.keySet().contains(level)){
				levelBrandNumMap.replace(level, levelBrandNumMap.get(level)+1);
			}else{
				levelBrandNumMap.put(level, 1);
			}
		}
		
		//计算品牌的列表
		ArrayList<TBrBrandPo> poList = Lists.newArrayList();
		for (Long key : brandNumSet) {
			List<TBrProductVo> productListTmp = tBrProductService.queryProductVoListByBrandId(key);
			HashSet<Long> categorykeys = tBrCategoryService.getCategorykeysByProductList(productListTmp);
			TBrBrandPo tBrBrandPo = new TBrBrandPo();
			tBrBrandPo.setId(key);
			tBrBrandPo.setProductNum(productListTmp.size());
			tBrBrandPo.setCurProductNum(brandNumMap.get(key));
			TBrBrand tBrBrand = brandMap.get(key);
			tBrBrandPo.setId(tBrBrand.getId());
			tBrBrandPo.setTBrBrandLevel(tBrBrand.getLevel());
			tBrBrandPo.setTBrBrandName(tBrBrand.getName());
			tBrBrandPo.setCatagoryNum(categorykeys.size());
			poList.add(tBrBrandPo);
		}
		KvBean kvBean = null;
		HashMap<Integer, String> nameMap = Maps.newHashMap();
		nameMap.put(1, "顶级品牌");
		nameMap.put(2, "一线品牌");
		nameMap.put(3, "二线品牌");
		nameMap.put(4, "三线品牌");
		nameMap.put(5, "四线品牌");
		nameMap.put(6, "其他品牌");
		Integer max = 0;
		
		
		List<KvBean> series = Lists.newArrayList();
		//pie 每个等级的品牌数量
		for (Byte key : levelBrandNumMap.keySet()) {
			kvBean = new KvBean();
			kvBean.setName(nameMap.get((int)key));
			kvBean.setValue(levelBrandNumMap.get(key));
			series.add(kvBean);
		}
		Map<String, Object> result = result();
		result.put("legend", nameMap.values());
		result.put("series", series);
		
		List<KvBean> process = Lists.newArrayList();
		//process 每个等级的产品数量
		for (Integer key : levelSet) {
			kvBean = new KvBean();
			kvBean.setName(nameMap.get(key));
			Integer value = levelProductNumMap.get(key);
			kvBean.setValue(value);
			if(value>max){
				max = value;
			}
			process.add(kvBean);
		}
		result.put("process", process);
		result.put("max", max);
				
		
		ArrayList<TBrBrandPo> poListTmp = Lists.newArrayList();
		int dataSize = poList.size();
		int pageSize = pageReq.getPageSize();
		int i = pageReq.getPageNumber()*pageSize;
		int end = i+pageSize;
		if(end > dataSize){
			end  = dataSize;
		}
		for ( ; i <end ; i++) {
			poListTmp.add(poList.get(i));			
		}
		Page<TBrBrandPo> page = new PageImpl<TBrBrandPo>(poListTmp, pageReq, dataSize);
		result.put("page", page);		
		return result;
	}
	


}
