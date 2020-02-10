package com.co.example.controller.brand;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.PageReq;
import com.co.example.constant.HttpStatusCode;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.brand.TBrBrand;
import com.co.example.entity.brand.aide.TBrBrandQuery;
import com.co.example.entity.brand.aide.TBrProductBrandQuery;
import com.co.example.entity.common.KvBean;
import com.co.example.entity.enterprise.TBrEnterprisePermission;
import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.TBrProductEnterprise;
import com.co.example.entity.product.aide.TBrEnterprisePo;
import com.co.example.entity.product.aide.TBrEnterpriseVo;
import com.co.example.entity.product.aide.TBrProductEnterpriseQuery;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.entity.product.aide.TBrProductVo;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.brand.TBrProductBrandService;
import com.co.example.service.category.TBrCategoryService;
import com.co.example.service.enterprise.TBrEnterprisePermissionService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrProductEnterpriseService;
import com.co.example.service.product.TBrProductService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.SneakyThrows;

@Controller
@RequestMapping("brand")
public class BrandController extends BaseControllerHandler<TBrBrandQuery> {

	@Autowired
	TBrBrandService service;

	@Autowired
	TBrProductBrandService tBrProductBrandService;

	@Autowired
	TBrProductService tBrProductService;

	@Autowired
	TBrBrandService tBrBrandService;

	@Autowired
	TBrProductEnterpriseService tBrProductEnterpriseService;

	@Autowired
	TBrEnterpriseService tBrEnterpriseService;
	
	@Autowired
	TBrEnterprisePermissionService tBrEnterprisePermissionService;
	
	@Autowired
	TBrCategoryService tBrCategoryService;

	// 太平洋女性
	public static String BRAND_LIST_PCLADY_URL = "http://cosme.pclady.com.cn/brand_list.html";
	// yoka时尚
	public static String BRAND_LIST_YOKA_URL = "http://brand.yoka.com/cosmetics/brandlist.htm";
	// 女人志
	public static String BRAND_LIST_ONLYLADY_URL = "http://hzp.onlylady.com/brand.html";
	// 腾讯女性
	public static String BRAND_LIST_QQLADY_URL = "http://lady.qq.com/product/pinpai.htm";
	// 39化妆品库
	public static String BRAND_LIST_39_URL = "http://hzpk.39.net/allbrand.html";
	// 凤凰时尚
	public static String BRAND_LIST_IFENG_URL = "http://cosmetics.ifeng.com/brand_all.html";
	// 瑞丽网
	public static String BRAND_LIST_RAYLI_URL = "http://hzp.rayli.com.cn/brand.html";
	// 网易女人
	public static String BRAND_LIST_163LADY_URL = "http://cosmetic.lady.163.com/brand/";

	@ResponseBody
	@RequestMapping("sync")
	public String sync() throws InterruptedException {
		syncData(BRAND_LIST_PCLADY_URL, "gb2312", ".sBrand li");
		syncData(BRAND_LIST_YOKA_URL, "utf-8", "fic_main1 dd");
		syncData(BRAND_LIST_ONLYLADY_URL, "gbk", "sortByLetter .brand");
		syncData(BRAND_LIST_QQLADY_URL, "utf-8", "uChar .c");
		syncData(BRAND_LIST_39_URL, "gb2312", ".brandBox li");
		syncData(BRAND_LIST_IFENG_URL, "utf-8", ".box1055 .blockBg td");
		syncData(BRAND_LIST_RAYLI_URL, "utf-8", ".g_zmpic li");
		syncData(BRAND_LIST_163LADY_URL, "utf-8", ".sect-main.tabs-panel2 li");

		return "200";
	}

	public void syncData(String url, String encode, String tags) throws InterruptedException {
		Document doc = null;
		try {
			doc = Jsoup.parse(new URL(url).openStream(), encode, url);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Elements sBrand = doc.select(tags);
		for (int i = 0; i < sBrand.size(); i++) {
			Element element = sBrand.get(i);
			Thread.sleep(2000);
			service.addBrandFromPclady(element);
			service.addBrandFromYOKA(element);
			service.addBrandFromONLYLADY(element);
			service.addBrandFromQQLADY(element);
			service.addBrandFrom39(element);
			service.addBrandFromIfeng(element);
			service.addBrandFromRAYLI(element);
			service.addBrandFrom163LADY(element);
		}
	}

	@Override
	public Boolean addExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrBrandQuery t, PageReq pageReq, Map<String, Object> result) {
		TBrBrandQuery tBrBrandQuery = new TBrBrandQuery();
		tBrBrandQuery.setName(t.getName());
		long queryCount = service.queryCount(tBrBrandQuery);
		if (queryCount > 0) {
			result.put("desc", "添加失败，已有该品牌.");
			result.put("code", "400");
			return true;
		} else {
			result.put("desc", "添加成功!");
			return false;
		}
	}

	@Override
	public Boolean editExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrBrandQuery t, PageReq pageReq, Map<String, Object> result) {
		TBrBrandQuery tBrBrandQuery = new TBrBrandQuery();
		tBrBrandQuery.setName(t.getName());
		tBrBrandQuery.setIdNotEqual(t.getId());
		long queryCount = service.queryCount(tBrBrandQuery);
		if (queryCount > 0) {
			result.put("code", "400");
			result.put("desc", "更新失败，已有该品牌.");
			return true;
		} else {
			result.put("desc", "更新成功!");
			return false;
		}
	}

	/**
	 * 批量添加关联（产品与品牌）
	 */
	@SneakyThrows(Exception.class)
	@ResponseBody
	@RequestMapping(value = "/conn", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> conn(Model model, HttpSession session, Long id) throws Exception {
		Map<String, Object> result = result();
		TBrBrand one = service.queryById(id);
		int count = service.addConnect2Product(one);
		result.put("count", count);
		return result;
	}

	/**
	 * 解除关联（单个产品和品牌）
	 */
	@SneakyThrows(Exception.class)
	@ResponseBody
	@RequestMapping(value = "/relieve", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> relieve(Model model, HttpSession session, Long id, Long pid) throws Exception {
		Map<String, Object> result = result();
		TBrProductBrandQuery tBrProductBrandQuery = new TBrProductBrandQuery();
		tBrProductBrandQuery.setProductId(pid);
		tBrProductBrandQuery.setBrandId(id);
		tBrProductBrandService.delete(tBrProductBrandQuery);
		result.put("desc", "已解除关联。");
		return result;
	}

	@RequestMapping(value = "/showMore/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String showMore(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, @PathVariable Long id) {
		TBrBrand one = service.queryById(id);
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setJoinBrandFlg(true);
		tBrProductQuery.setBrandId(one.getId());
		pageReq.setPageSize(10);
		Page<TBrProduct> page = tBrProductService.queryPageList(tBrProductQuery, pageReq);
		model.addAttribute(PAGE, page);
		model.addAttribute(ONE, one);
		return "brand/show";
	}

	/****** 以下为新增2019年8月12日 ********/
	@RequestMapping(value = "/list2", method = { RequestMethod.GET, RequestMethod.POST })
	public String list2(Model model, PageReq pageReq, TBrBrandQuery query) throws Exception {
		query.setDelFlg(Constant.NO);
		Page<TBrBrand> page = service.queryPageList(query, pageReq);
		model.addAttribute(QUERY, query);
		model.addAttribute(PAGE, page);
		return "brand/list2";
	}

	/***** 弹窗展示 *******/
	@RequestMapping(value = "/showMore2/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String showMore2(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, @PathVariable Long id) {
		TBrBrand one = service.queryById(id);
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setJoinBrandFlg(true);
		tBrProductQuery.setBrandId(one.getId());
		pageReq.setPageSize(10);
		Page<TBrProduct> page = tBrProductService.queryPageList(tBrProductQuery, pageReq);
		model.addAttribute(PAGE, page);
		model.addAttribute(ONE, one);
		return "brand/show2";
	}

	@ResponseBody
	@RequestMapping(value = "/updateStatus", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> updateStatus(Model model, Long id, Boolean flg) throws Exception {
		Map<String, Object> result = result();
		TBrBrand tBrBrand = new TBrBrand();
		tBrBrand.setId(id);
		if (flg) {
			tBrBrand.setIsChoice(Constant.YES);
		} else {
			tBrBrand.setIsChoice(Constant.NO);
		}
		service.updateByIdSelective(tBrBrand);
		return result;
	}

	// 设置品牌等级
	@ResponseBody
	@RequestMapping(value = "/setLevel", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> setLevel(Model model, int level, @RequestParam(value = "bids[]") Long[] bids) {
		Map<String, Object> result = result();
		try {
			for (int j = 0; j < bids.length; j++) {
				TBrBrand tBrBrand = new TBrBrand();
				tBrBrand.setId(bids[j]);
				tBrBrand.setLevel((byte) level);
				tBrBrandService.updateByIdSelective(tBrBrand);
			}
		} catch (Exception e) {
			result.put("info", "关联失败！");
		}
		result.put("info", "关联完成！");
		return result;
	}

	// *******************2019年9月18日**************************/
	@RequestMapping(value = "/index/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String index(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, @PathVariable Long id) {
		TBrBrand one = service.queryById(id);
		model.addAttribute(ONE, one);
		return "brand/index";
	}

	@ResponseBody
	@RequestMapping(value = "/base/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> base(Model model, @PathVariable Long id) {
		Map<String, Object> result = result();
		Long applyNum = 0l, cancleApplyNum = 0l;
		Integer enterpriseNum = 0;
		Integer categoryNum = 0;
		try {
			TBrProductQuery tBrProductQuery = new TBrProductQuery();
			tBrProductQuery.setBrandId(id);
			tBrProductQuery.setJoinBrandFlg(true);
			List<TBrProductVo> productList = tBrProductService.queryList(tBrProductQuery);
			tBrProductQuery.setMoreData1("1");
			// 产品取消备案数
			cancleApplyNum = tBrProductService.queryCount(tBrProductQuery);
			// 产品备案数
			applyNum = productList.size() - cancleApplyNum;

			HashSet<Object> set1 = Sets.newHashSet();
			HashSet<Object> set2 = Sets.newHashSet();
			TBrProductEnterpriseQuery tBrProductEnterpriseQuery = null;
			for (TBrProduct tBrProduct : productList) {
				Long categoryId = tBrProduct.getCategoryId();
				if (categoryId != null) {
					set1.add(categoryId);
				}
				tBrProductEnterpriseQuery = new TBrProductEnterpriseQuery();
				tBrProductEnterpriseQuery.setProductId(tBrProduct.getId());
				tBrProductEnterpriseQuery.setJoinProductFlg(true);
				List<TBrProductEnterprise> peList = tBrProductEnterpriseService.queryList(tBrProductEnterpriseQuery);
				for (TBrProductEnterprise tBrProductEnterprise : peList) {
					set2.add(tBrProductEnterprise.getEnterpriseId());
				}
			}
			// 品类
			categoryNum = set1.size();
			// 供应商
			enterpriseNum = set2.size();

		} catch (Exception e) {
			result.put("code", HttpStatusCode.CODE_ERROR);
			result.put("info", "获取失败！");
		}
		result.put("applyNum", applyNum);
		result.put("cancleApplyNum", cancleApplyNum);
		result.put("enterpriseNum", enterpriseNum);
		result.put("categoryNum", categoryNum);
		result.put("info", "获取完成！");
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
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setBrandId(id);
		tBrProductQuery.setJoinBrandFlg(true);
		List<TBrProduct> productList = tBrProductService.queryList(tBrProductQuery);
		HashSet<Object> set = Sets.newHashSet();
		HashMap<Long, String> nameMap = Maps.newHashMap();
		HashMap<Long, Integer> numMap = Maps.newHashMap();
		List<KvBean> series = Lists.newArrayList();
		for (TBrProduct tBrProduct : productList) {
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
		return result;
	}

	/**
	 * 品牌包含企业个数 饼图
	 * 全国地图，各省企业数量
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/enterprisePercentage/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> enterprisePercentage(Model model, @PathVariable Long id) {
		List<TBrProductVo> productList = tBrProductService.queryProductVoListByBrandId(id);
		HashMap<Long, String> nameMap = Maps.newHashMap();
		HashMap<Long, Integer> numMap = Maps.newHashMap();
		HashMap<Long, String> cityMap = Maps.newHashMap();
		HashSet<Long> set = Sets.newHashSet();
		for (TBrProduct tBrProduct : productList) {
			List<TBrEnterpriseVo> queryList = tBrEnterpriseService.queryEnterpriseListByProductId(tBrProduct.getId());
			for (TBrEnterpriseVo tBrEnterprise : queryList) {
				Long eId = tBrEnterprise.getId();
				if (eId != null) {
					if (set.contains(eId)) {
						Integer integer = numMap.get(eId);
						numMap.replace(eId, integer + 1);
					} else {
						set.add(eId);
						numMap.put(eId, 1);
						nameMap.put(eId, tBrEnterprise.getEnterpriseName());
						cityMap.put(eId, tBrEnterprise.getProvince());
					}
				}
			}
		}
		List<KvBean> series = Lists.newArrayList();
		KvBean kvBean = null;
		for (Long key : set) {
			kvBean = new KvBean();
			kvBean.setName(nameMap.get(key));
			kvBean.setValue(numMap.get(key));
			series.add(kvBean);
		}
		List<KvBean> city = Lists.newArrayList();
		HashSet<String> citySet = Sets.newHashSet();
		HashMap<String,Integer> cityNumMap = Maps.newHashMap();
		for (Long key : cityMap.keySet()) {
			String province = cityMap.get(key);
			if (citySet.contains(province)) {
				Integer integer = cityNumMap.get(province);
				cityNumMap.replace(province, integer + 1);
			}else{
				citySet.add(province);
				cityNumMap.put(province, 1);
			}
		}
		for (String key : cityNumMap.keySet()) {
			kvBean = new KvBean();
			kvBean.setName(key);
			kvBean.setValue(cityNumMap.get(key));
			city.add(kvBean);
		}
		Map<String, Object> result = result();
		result.put("legend", nameMap.values());
		result.put("series", series);
		result.put("city", city);
		return result;
	}

	
	// 产品列表
	@ResponseBody
	@RequestMapping(value = "/productList/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> productList(Model model, @PathVariable Long id, PageReq pageReq) {
		Map<String, Object> result = result();
		Page<TBrProductVo> page = tBrProductService.queryProductVoPageByBrandId(id,pageReq);
		result.put("page", page);
		return result;
	}
	
	
	// 企业列表
	@ResponseBody
	@RequestMapping(value = "/enterpriseList/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> enterpriseList(Model model, @PathVariable Long id, PageReq pageReq) {
		List<TBrProductVo> productList = tBrProductService.queryProductVoListByBrandId(id);
		HashMap<Long, TBrEnterprise> eMap = Maps.newHashMap();
		HashMap<Long, Integer> numMap = Maps.newHashMap();
		HashSet<Long> set = Sets.newHashSet();
		for (TBrProductVo tBrProduct : productList) {
			List<TBrEnterpriseVo> queryList = tBrEnterpriseService.queryEnterpriseListByProductId(tBrProduct.getId());
			for (TBrEnterpriseVo tBrEnterprise : queryList) {
				Long eId = tBrEnterprise.getId();
				if (eId != null) {
					if (set.contains(eId)) {
						Integer integer = numMap.get(eId);
						numMap.replace(eId, integer + 1);
					}else{
						set.add(eId);
						numMap.put(eId, 1);
						eMap.put(eId, tBrEnterprise);
					}
				}
			}
			tBrProduct.setEnterpriseList(queryList);
		}
		TBrEnterprisePermission tBrEnterprisePermission = null;
		ArrayList<TBrEnterprisePo> poList = Lists.newArrayList();
		for (Long key : set) {
			List<TBrProductVo> productListTmp = tBrProductService.queryProductVoListByRealEnterpriseId(key);
			HashSet<Long> categorykeys = tBrCategoryService.getCategorykeysByProductList(productListTmp);
			TBrEnterprisePo tBrEnterprisePo = new TBrEnterprisePo();
			tBrEnterprisePo.setId(key);
			tBrEnterprisePo.setProductNum(productListTmp.size());
			tBrEnterprisePo.setCurProductNum(numMap.get(key));
			tBrEnterprisePo.setEnterprise(eMap.get(key));
			tBrEnterprisePo.setCatagoryNum(categorykeys.size());
			tBrEnterprisePermission = new TBrEnterprisePermission();
			tBrEnterprisePermission.setEid(key);
			TBrEnterprisePermission queryOne = tBrEnterprisePermissionService.queryOne(tBrEnterprisePermission);
			if(queryOne !=null){
				tBrEnterprisePo.setPermission(queryOne);
			}
			poList.add(tBrEnterprisePo);
		}
		Collections.sort(poList, new Comparator<TBrEnterprisePo>(){
            public int compare(TBrEnterprisePo o1, TBrEnterprisePo o2) {
                return o2.getProductNum().compareTo(o1.getProductNum());
            }});
		Map<String, Object> result = result();
		ArrayList<TBrEnterprisePo> poListTmp = Lists.newArrayList();
		int dataSize = poList.size();
		int pageSize = pageReq.getPageSize();
		int i = pageReq.getPageNumber()*pageSize;
		int end = i+pageSize;
		if(end > dataSize){
			end  = dataSize;
		}
//		int remainderFlg =  dataSize%pageSize;
//		int morePage = 0;
//		if(remainderFlg>0){
//			morePage = 1;
//		}
//		0   0_14
//		1   15-29
//		int pages = dataSize/pageSize+morePage;
		for ( ; i <end ; i++) {
			poListTmp.add(poList.get(i));			
		}
		
		Page<TBrEnterprisePo> page = new PageImpl<TBrEnterprisePo>(poListTmp, pageReq, dataSize);
		result.put("page", page);
		return result;
	}
	
}
