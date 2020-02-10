package com.co.example.service.manifest.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.co.example.common.constant.Constant;
import com.co.example.constant.ManifestConstant;
import com.co.example.dao.manifest.TBrManifestDao;
import com.co.example.entity.brand.TBrBrand;
import com.co.example.entity.brand.aide.TBrBrandVo;
import com.co.example.entity.category.TBrCategory;
import com.co.example.entity.common.KvBean;
import com.co.example.entity.enterprise.TBrEnterprisePermission;
import com.co.example.entity.enterprise.aide.TBrEnterprisePermissionQuery;
import com.co.example.entity.label.TBrIngredientLabel;
import com.co.example.entity.label.TBrIngredientLabelJoin;
import com.co.example.entity.manifest.TBrManifest;
import com.co.example.entity.manifest.TBrManifestAuth;
import com.co.example.entity.manifest.TBrManifestBrand;
import com.co.example.entity.manifest.TBrManifestCategory;
import com.co.example.entity.manifest.TBrManifestData;
import com.co.example.entity.manifest.TBrManifestEnterprise;
import com.co.example.entity.manifest.TBrManifestIngredient;
import com.co.example.entity.manifest.TBrManifestProduct;
import com.co.example.entity.manifest.TBrManifestResult;
import com.co.example.entity.manifest.aide.EnterpriseTopPo;
import com.co.example.entity.manifest.aide.TBrManifestAuthQuery;
import com.co.example.entity.manifest.aide.TBrManifestQuery;
import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrEnterpriseVo;
import com.co.example.entity.product.aide.TBrIngredientVo;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.entity.product.aide.TBrProductVo;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.brand.TBrProductBrandService;
import com.co.example.service.category.TBrCategoryService;
import com.co.example.service.category.TBrProductCategoryService;
import com.co.example.service.enterprise.TBrEnterpriseBaseService;
import com.co.example.service.enterprise.TBrEnterprisePermissionService;
import com.co.example.service.enterprise.TBrEnterpriseRegisterService;
import com.co.example.service.label.TBrIngredientLabelJoinService;
import com.co.example.service.label.TBrIngredientLabelService;
import com.co.example.service.label.TBrLabelService;
import com.co.example.service.label.TBrProductLabelService;
import com.co.example.service.mall.TBrMallMonthService;
import com.co.example.service.manifest.TBrManifestAuthService;
import com.co.example.service.manifest.TBrManifestBrandService;
import com.co.example.service.manifest.TBrManifestCategoryService;
import com.co.example.service.manifest.TBrManifestDataService;
import com.co.example.service.manifest.TBrManifestEnterpriseService;
import com.co.example.service.manifest.TBrManifestIngredientService;
import com.co.example.service.manifest.TBrManifestProductService;
import com.co.example.service.manifest.TBrManifestResultService;
import com.co.example.service.manifest.TBrManifestService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrIngredientService;
import com.co.example.service.product.TBrProductEnterpriseService;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.solr.SolrService;
import com.co.example.utils.BaseDataUtil;
import com.co.example.utils.JsonUtil;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.zxing.common.detector.MathUtils;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.model.formatter.BasicTypeFormatter.FloatFormatter;

@Slf4j
@Service
public class TBrManifestServiceImpl extends BaseServiceImpl<TBrManifest, Long> implements TBrManifestService {
	@Resource
	private TBrManifestDao tBrManifestDao;

	@Inject
	TBrManifestAuthService tBrManifestAuthService;

	@Inject
	TBrManifestDataService tBrManifestDataService;

	@Inject
	TBrManifestResultService tBrManifestResultService;

	@Inject
	TBrIngredientService tBrIngredientService;

	@Inject
	TBrProductService tBrProductService;

	@Inject
	TBrEnterpriseService tBrEnterpriseService;

	@Inject
	TBrProductBrandService tBrProductBrandService;

	@Inject
	TBrBrandService tBrBrandService;

	@Inject
	TBrProductLabelService tBrProductLabelService;

	@Inject
	TBrLabelService tBrLabelService;

	@Inject
	SolrService solrService;

	@Inject
	TBrProductEnterpriseService tBrProductEnterpriseService;

	@Inject
	TBrEnterpriseRegisterService tBrEnterpriseRegisterService;

	@Inject
	TBrEnterpriseBaseService tBrEnterpriseBaseService;

	@Inject
	TBrCategoryService tBrCategoryService;

	@Inject
	TBrProductCategoryService tBrProductCategoryService;

	@Inject
	TBrMallMonthService tBrMallMonthService;
	
	@Inject
	TBrIngredientLabelJoinService tBrIngredientLabelJoinService;
	
	@Inject
	TBrIngredientLabelService ingredientLabelService;
	
	@Inject
	TBrManifestProductService tBrManifestProductService;
	
	@Inject
	TBrManifestBrandService tBrManifestBrandService;
		
	@Inject
	TBrManifestCategoryService tBrManifestCategoryService;
	
	@Inject
	TBrManifestEnterpriseService tBrManifestEnterpriseService;
	
	@Inject
	TBrManifestIngredientService tBrManifestIngredientService;
	
	@Inject
	TBrEnterprisePermissionService tBrEnterprisePermissionService;

	@Override
	protected BaseDao<TBrManifest, Long> getBaseDao() {
		return tBrManifestDao;
	}

	@Override
	public Boolean addAndAuth(TBrManifest t, Long adminId) {
		t.setCreateBy(adminId);
		t.setDelFlg(Constant.NO);
		t.setStatus(Constant.TASK_STATUS_WAITTING);
		add(t);
		TBrManifestAuth tBrManifestAuth = new TBrManifestAuth();
		tBrManifestAuth.setManifestId(t.getId());
		tBrManifestAuth.setType(t.getType());
		tBrManifestAuth.setUsingBy(adminId);
		tBrManifestAuth.setCreateBy(adminId);
		tBrManifestAuth.setStatus(t.getStatus());
		BaseDataUtil.setDefaultData(tBrManifestAuth);
		tBrManifestAuthService.add(tBrManifestAuth);
		return true;
	}

	Long mId = 0l;
	List<TBrManifestData> dataList = null;

	@Override
	public void queryManifest(TBrManifest tBrManifest) {
		log.info("###-开始分析数据");
		Byte type = tBrManifest.getType();
		mId = tBrManifest.getId();
		log.info("###-数据id："+mId+"类型："+type+"  1产品2品牌3企业4成分");
		TBrManifestData tBrManifestData = new TBrManifestData();
		tBrManifestData.setManifestId(mId);
		dataList = tBrManifestDataService.queryList(tBrManifestData);
		if (type == ManifestConstant.TYPE_PRODUCT) {
			queryProduct();
		} else if (type == ManifestConstant.TYPE_BRAND) {
			queryBrand();
		} else if (type == ManifestConstant.TYPE_ENTERPRISE) {
			queryEnterprise();
		} else if (type == ManifestConstant.TYPE_INGREDIENT) {
			queryIngredient();
		}

	}

	private void getIngredient(HashSet<Long> ingredientIds, HashMap<Long, Integer> labelNumMap,
			HashMap<Long, String> labelNameMap) {
		HashSet<Long> labelIds = Sets.newHashSet();
		log.info("###get ingredient label map by ingredients");
		ingredientIds.remove(null);
		int isize = ingredientIds.size();
		for (Long iid : ingredientIds) {
			log.info("###getIngredient - isize="+isize--);
			getILabel(labelNumMap, labelNameMap, labelIds, iid);
			//p-i-pbeci
			//b-i-bpeci
			try {
				saveManifestIngredient(iid);
			} catch (Exception e) {
				log.info("###saveManifestIngredient exception pb-i");
				e.printStackTrace();
			}
		}
		tBrManifestIngredientService.addInBatch(ilist);
	}

	private void getILabel(HashMap<Long, Integer> labelNumMap, HashMap<Long, String> labelNameMap,
			HashSet<Long> labelIds, Long iid) {
		List<TBrIngredientLabelJoin> ils = tBrIngredientLabelJoinService.queryList(new TBrIngredientLabelJoin(iid));
		for (TBrIngredientLabelJoin tBrIngredientLabelJoin : ils) {
			Long labelId = tBrIngredientLabelJoin.getLabelId();
			if (labelId != null) {
				if (labelIds.contains(labelId)) {
					Integer integer = labelNumMap.get(labelId);
					labelNumMap.replace(labelId, integer + 1);
				} else {
					labelIds.add(labelId);
					labelNumMap.put(labelId, 1);
					TBrIngredientLabel label = ingredientLabelService.queryById(labelId);
					labelNameMap.put(labelId, label.getName());
				}
			}
		}
	}

	private void getByEnterprises(Collection<TBrEnterpriseVo> values, HashMap<String, Object> pie,
			ArrayList<EnterpriseTopPo> top, ArrayList<KvBean> city,Byte type) {
		log.info("###get enterprise pie top city by enterprises");
		HashSet<Long> cIds = Sets.newHashSet();
		HashSet<Long> bIds = Sets.newHashSet();
		HashSet<Long> pIds = Sets.newHashSet();
		HashMap<Long, String> cNameMap = Maps.newHashMap();
		HashMap<Long, Integer> cNumMap = Maps.newHashMap();
		HashMap<Long, String> cityMap = Maps.newHashMap();
		ArrayList<EnterpriseTopPo> topTmp = Lists.newArrayList();
		int eSize = values.size();
		for (TBrEnterpriseVo tBrEnterprise : values) {
			log.info("###getByEnterprises - size"+ eSize--);
			if (tBrEnterprise != null) {
				HashSet<Long> cIds4one = Sets.newHashSet();
				HashSet<Long> bIds4one = Sets.newHashSet();
				List<TBrProductVo> pvoList = tBrProductService.queryProductVoListByRealEnterpriseId(tBrEnterprise.getId());
				for (TBrProductVo product : pvoList) {
					pIds.add(product.getId());
					TBrBrand brand = tBrBrandService.queryByProductId(product.getId());
					if(brand !=null){
						bIds.add(brand.getId());
					}
					//列表每个企业的品类数和品牌数。
					cIds4one.add(product.getCategoryId());
					if(brand !=null){
						bIds4one.add(brand.getId());
					}
					getCategoryNameAndNum(cIds, cNameMap, cNumMap, null, null, null, product);
				}
				cityMap.put(tBrEnterprise.getId(), tBrEnterprise.getProvince());
				topTmp.add(getETop(tBrEnterprise));
				//p-e-pbeci
				//b-e-bpeci
				//e-e-ebpc
				//i-e-ibecp
				tBrEnterprise.setCnum(cIds4one.size());
				tBrEnterprise.setPnum(pvoList.size());
				tBrEnterprise.setBnum(bIds4one.size());
				TBrEnterprisePermissionQuery tBrEnterprisePermissionQuery = new TBrEnterprisePermissionQuery();
				tBrEnterprisePermissionQuery.setEid(tBrEnterprise.getId());
				TBrEnterprisePermission tBrEnterprisePermission = tBrEnterprisePermissionService.queryOne(tBrEnterprisePermissionQuery);
				if(tBrEnterprisePermission!=null){
					tBrEnterprise.setEndDate(tBrEnterprisePermission.getEndDate());
					tBrEnterprise.setStartDate(tBrEnterprisePermission.getStartDate());
				}
				saveManifestEnterprise(tBrEnterprise);
			}
			
			
		}
		tBrManifestEnterpriseService.addInBatch(elist);
		
		getPie(cNameMap, cNumMap,pie);
		// city
		setChina(city, cityMap);
		
		topTmp.sort((a, b) -> b.getEnterpriseWeight().compareTo(a.getEnterpriseWeight()));
		int size = topTmp.size()>10?10:topTmp.size();
		for (int i=0 ; i< size ; i++) {
			top.add(topTmp.get(i));
		}
		
		initListX30(values.size(),bIds.size(), cIds.size(), pIds.size(), type);
	}

	// 设置中国地图 ，各个供应商的分布地区
	private void setChina(ArrayList<KvBean> city, HashMap<Long, String> cityMap) {
		log.info("###-setChina");
		KvBean kvBean;
		HashSet<String> citySet = Sets.newHashSet();
		HashMap<String, Integer> cityNumMap = Maps.newHashMap();
		for (Long key : cityMap.keySet()) {
			String province = cityMap.get(key);
			if (citySet.contains(province)) {
				Integer integer = cityNumMap.get(province);
				cityNumMap.replace(province, integer + 1);
			} else {
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
	}

	void overManifest(Long mId, Byte type, Integer keyId, String jsondata) {
		log.info("###save manifest data. mid="+mId+"--keyId="+keyId+"--jsondata="+jsondata);
		// 保存到结果表
		TBrManifestResult tBrManifestResult = new TBrManifestResult();
		tBrManifestResult.setKeyId(keyId);
		tBrManifestResult.setManifestId(mId);
		tBrManifestResult.setType(type);
		tBrManifestResult.setJsondata(jsondata);
		BaseDataUtil.setDefaultData(tBrManifestResult);
		tBrManifestResultService.add(tBrManifestResult);
	}

	void overManifestStatus(Long mId) {
		// 更新主表状态
		log.info("###update manifest status");
		TBrManifestQuery tBrManifestQuery = new TBrManifestQuery();
		tBrManifestQuery.setId(mId);
		tBrManifestQuery.setStatus(Constant.TASK_STATUS_DONE);
		updateByIdSelective(tBrManifestQuery);
		// 更新授权表状态
		TBrManifestAuthQuery tBrManifestAuthQuery = new TBrManifestAuthQuery();
		tBrManifestAuthQuery.setManifestId(mId);
		List<TBrManifestAuth> queryList = tBrManifestAuthService.queryList(tBrManifestAuthQuery);
		for (TBrManifestAuth tBrManifestAuth : queryList) {
			tBrManifestAuthQuery = new TBrManifestAuthQuery();
			tBrManifestAuthQuery.setId(tBrManifestAuth.getId());
			tBrManifestAuthQuery.setStatus(Constant.TASK_STATUS_DONE);
			tBrManifestAuthService.updateByIdSelective(tBrManifestAuthQuery);
		}
		
		
	}

	// 根据品牌查询产品，再查询品类
	private void getByBrands(Collection<TBrBrandVo> brandList, HashMap<String, Object> pie,ArrayList<EnterpriseTopPo> top) {
		log.info("###get enterprise pie top  by brands");
		HashSet<Long> categoryIds = Sets.newHashSet();
		HashMap<Long, String> categoryNameMap = Maps.newHashMap();
		HashMap<Long, Integer> categoryNumMap = Maps.newHashMap();
		ArrayList<EnterpriseTopPo> topTmp = Lists.newArrayList();
		// 关注产品--关注品牌--企业（去重）
		HashMap<Long, TBrEnterprise> es = Maps.newHashMap();
		// 关注产品查询出的关联品牌列表
		for (TBrBrandVo tBrBrand : brandList) {
			if (tBrBrand != null && tBrBrand.getId() !=null) {
				// 关联品牌查询出的产品
				HashSet<Long> eids4one = Sets.newHashSet();
				HashSet<Long> cids4one = Sets.newHashSet();
				List<TBrProductVo> productList = tBrProductService.queryProductVoListByBrandId(tBrBrand.getId());
				for (TBrProductVo product : productList) {
					// 对每个这些产品再查询品类
					getCategoryNameAndNum(categoryIds, categoryNameMap, categoryNumMap, null, null, null, product);
					cids4one.add(product.getCategoryId());
					// 对关联品牌的查询产品，再查询企业，企业有重复，所以去重。
					List<TBrEnterpriseVo> eList = tBrEnterpriseService.queryEnterpriseListByProductId(product.getId());
					for (TBrEnterpriseVo tBrEnterprise : eList) {
						if(tBrEnterprise !=null){
							es.put(tBrEnterprise.getId(), tBrEnterprise);
							eids4one.add(tBrEnterprise.getId());
						}
					}
				}
				//p-b-pbeci
				//e-b-ebpc
				//i-b-ibecp
				tBrBrand.setEenum(eids4one.size());
				tBrBrand.setCnum(cids4one.size());
				tBrBrand.setPnum(productList.size());
				saveManifestBrand(tBrBrand);
			}
		}
		
		tBrManifestBrandService.addInBatch(blist);
		// 去重的企业再查询产品，产品再查询品牌，品牌再去重
		// 后期可以建立企业品牌关联表，但要注意带来的影响 1全量产品数据和品牌对比，2抓取产品时的增量数据 3手动给产品设置品牌的数据变动 4
		// 手动给品牌设置等级
		Collection<TBrEnterprise> eValues = es.values();
		int eSize = eValues.size();
		//666
		log.info("get enterprise pie top  by brands---top");
		for (TBrEnterprise distinctEnterprise : eValues) {
			log.info("etop---"+eSize--);
			topTmp.add(getETop(distinctEnterprise));
		}
		// **********************************
		log.info("get enterprise pie top  by brands---pie");
		getPie(categoryNameMap, categoryNumMap,pie);
		// **********************************
		topTmp.sort((a, b) -> b.getEnterpriseWeight().compareTo(a.getEnterpriseWeight()));
		int size = topTmp.size()>10?10:topTmp.size();
		for (int i=0 ; i< size ; i++) {
			top.add(topTmp.get(i));
		}
		
	}
	
	
	private void getByProductIds(HashSet<Long> productIds) {
		for (Long pid : productIds) {
			TBrProductVo product = tBrProductService.queryOne(new TBrProduct(pid));
			if (product != null) {
				setESByP(product);
				TBrBrandVo brandVo = tBrBrandService.queryByProductId(product.getId());
				if (brandVo != null) {
					product.setProductBrandName(brandVo.getName());
				}
				saveManifestProduct(product);
			}
		}
		tBrManifestProductService.addInBatch(plist);
	}

	private EnterpriseTopPo getETop( TBrEnterprise distinctEnterprise) {
		EnterpriseTopPo enterpriseTopPo = new EnterpriseTopPo();
		HashSet<Long> bSet = Sets.newHashSet();
		HashMap<Long,TBrBrand> bMap = Maps.newHashMap();
		HashMap<Long, Integer> bNumMap = Maps.newHashMap();
		HashSet<Long> cSet = Sets.newHashSet();
		Long deId = distinctEnterprise.getId();
		String enterpriseName = distinctEnterprise.getEnterpriseName();
		List<TBrProductVo> pvoList = tBrProductService.queryProductVoListByRealEnterpriseId(deId);
		for (TBrProductVo tBrProductVo : pvoList) {
			if(tBrProductVo !=null){
				cSet.add(tBrProductVo.getCategoryId());
				TBrBrand eb = tBrBrandService.queryByProductId(tBrProductVo.getId());
				if(eb !=null){
					Long ebId = eb.getId();
					if (ebId != null) {
						if (bSet.contains(ebId)) {
							bNumMap.replace(ebId, bNumMap.get(ebId) + 1);
						}else{
							bMap.put(ebId, eb);
							bNumMap.put(ebId, 1);
						}
					}
				}
			}
		}
		cSet.remove(null);
		bSet.remove(null);		
		int bl1 = 0, bl2 = 0, bl3 = 0, bl4 = 0, bl5 = 0, bl6 = 0;
		int bl1PCount = 0, bl2PCount = 0, bl3PCount = 0, bl4PCount = 0, bl5PCount = 0, bl6PCount = 0;
		for (TBrBrand oneBrand : bMap.values()) {
			Byte level = oneBrand.getLevel();
			if(level !=null){
				Integer count = bNumMap.get(oneBrand.getId());
				if (level == 1) {
					bl1++;
					bl1PCount += count;
				}
				if (level == 2) {
					bl2++;
					bl2PCount += count;
				}
				if (level == 3) {
					bl3++;
					bl3PCount += count;
				}
				if (level == 4) {
					bl4++;
					bl4PCount += count;
				}
				if (level == 5) {
					bl5++;
					bl5PCount += count;
				}
				if (level == 6) {
					bl6++;
					bl6PCount += count;
				}
			}
		}
		float weight = (getWeight(1, bl1, bl1PCount) + getWeight(2, bl2, bl2PCount) + getWeight(3, bl3, bl3PCount)
				+ getWeight(4, bl4, bl4PCount) + getWeight(5, bl5, bl5PCount) + getWeight(6, bl6, bl6PCount))/100;
		String format = new java.text.DecimalFormat("0.00").format(weight);
		float weightTmp = Float.valueOf(format);
		enterpriseTopPo.setId(deId);
		enterpriseTopPo.setEnterpriseName(enterpriseName);
		enterpriseTopPo.setBrandNum(bSet.size());
		enterpriseTopPo.setCatagoryNum(cSet.size());
		enterpriseTopPo.setProductNum(pvoList.size());
		enterpriseTopPo.setEnterpriseWeight(weightTmp);
		return enterpriseTopPo;
	}
	
	public static void main(String[] args) {
		float weight = 344.346f;
		String format = new java.text.DecimalFormat("0.00").format(weight);
		float weightTmp = Float.valueOf(format);
		System.out.println(weightTmp);
		System.out.println(111);
	}

	private void getCategoryNameAndNum(HashSet<Long> categoryIds, HashMap<Long, String> categoryNameMap,
			HashMap<Long, Integer> categoryNumMap, HashMap<Long, Integer> categorysaleMap1,
			HashMap<Long, Integer> categorysaleMap2, HashMap<Long, Integer> categorysaleMap3, TBrProductVo product) {
		// 计算销量 本月，上月，半年
		if (categorysaleMap1 != null){
			List<Integer> MonthSales = tBrMallMonthService.getMonthSalesByProductId(product.getId());
			product.setThisMonthSales(MonthSales.get(11));
			product.setLastMonthSales(MonthSales.get(10));
			product.setHalfYearSales(MonthSales.get(11) + MonthSales.get(10) + MonthSales.get(9) + MonthSales.get(8)
			+ MonthSales.get(7) + MonthSales.get(6));
		}
		
		Long categoryId = product.getCategoryId();
		if (categoryId != null) {
			if (categoryIds.contains(categoryId)) {
				Integer integer = categoryNumMap.get(categoryId);
				categoryNumMap.replace(categoryId, integer + 1);
				if (categorysaleMap1 != null) {
					// 销量再根据品类汇总
					categorysaleMap1.replace(categoryId,categorysaleMap1.get(categoryId) + product.getThisMonthSales());
					categorysaleMap2.replace(categoryId,categorysaleMap2.get(categoryId) + product.getLastMonthSales());
					categorysaleMap3.replace(categoryId,categorysaleMap3.get(categoryId) + product.getHalfYearSales());
				}
			} else {
				categoryIds.add(categoryId);
				
				categoryNumMap.put(categoryId, 1);
				categoryNameMap.put(categoryId, product.getCategoryName());
				if (categorysaleMap1 != null) {
					categorysaleMap1.put(categoryId, product.getThisMonthSales());
					categorysaleMap2.put(categoryId, product.getLastMonthSales());
					categorysaleMap3.put(categoryId, product.getHalfYearSales());
				}
			}
		}
		
	}

	private void getPie(HashMap<Long, String> itemNameMap, HashMap<Long, Integer> itemNumMap,HashMap<String, Object> pie) {
		if(pie ==null){
			pie = Maps.newHashMap();
		}
		List<KvBean> series = Lists.newArrayList();
		List<Map.Entry<Long, Integer>> list = new ArrayList<Map.Entry<Long, Integer>>(itemNumMap.entrySet());
		// 然后通过比较器来实现排序
		Collections.sort(list, new Comparator<Map.Entry<Long, Integer>>() {
			// 降序排序
			public int compare(Entry<Long, Integer> o1, Entry<Long, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		for (Entry<Long, Integer> map : list) {
			kvBean = new KvBean();
			kvBean.setName(itemNameMap.get(map.getKey()));
			kvBean.setValue(itemNumMap.get(map.getKey()));
			series.add(kvBean);
		}
		pie.put("legend", itemNameMap.values());
		pie.put("series", series);
//		return pie;
	}

	private float getWeight(Integer level, float LevelNum, float pNum) {
		if (level == 1) {
			return LevelNum * 10f + pNum * 0.6f;
		} else if (level == 2) {
			return LevelNum * 8f + pNum * 0.5f;
		} else if (level == 3) {
			return LevelNum * 5f + pNum * 0.4f;
		} else if (level == 4) {
			return LevelNum * 2f + pNum * 0.3f;
		} else if (level == 5) {
			return LevelNum * 1f + pNum * 0.2f;
		} else if (level == 6) {
			return LevelNum * 0.5f + pNum * 0.1f;
		}
		return 0;
	}

	// 数据获取
	KvBean kvBean = null;
	HashSet<Long> brandIds = null;
	HashSet<Long> categoryIds = null;
	HashSet<Long> enterpriseIds = null;
	HashSet<Long> ingredientIds = null;
	HashSet<Long> productIds = null;

	HashMap<Long, TBrBrandVo> brandMap = null;
	HashMap<Long, String> categoryNameMap = null;
	HashMap<Long, Integer> categoryNumMap = null;
	HashMap<Long, Integer> categorysaleMap1 = null;
	HashMap<Long, Integer> categorysaleMap2 = null;
	HashMap<Long, Integer> categorysaleMap3 = null;
	HashMap<Long, TBrEnterpriseVo> enterpriseMap = null;
	HashMap<Long, TBrIngredient> ingredientMap = null;
	HashMap<Long, TBrProduct> productMap = null;
	
	List<TBrManifestProduct> plist = null;
	List<TBrManifestBrand> blist = null;
	List<TBrManifestEnterprise> elist = null;
	List<TBrManifestCategory> clist = null;
	List<TBrManifestIngredient> ilist = null;
	
	
	int bl1 = 0, bl2 = 0, bl3 = 0, bl4 = 0, bl5 = 0, bl6 = 0;

	private void init() {
		brandIds = Sets.newHashSet();
		categoryIds = Sets.newHashSet();
		enterpriseIds = Sets.newHashSet();
		ingredientIds = Sets.newHashSet();
		productIds = Sets.newHashSet();
		brandMap = Maps.newHashMap();
		categoryNameMap = Maps.newHashMap();
		categoryNumMap = Maps.newHashMap();
		categorysaleMap1 = Maps.newHashMap();
		categorysaleMap2 = Maps.newHashMap();
		categorysaleMap3 = Maps.newHashMap();
		enterpriseMap = Maps.newHashMap();
		ingredientMap = Maps.newHashMap();
		productMap = Maps.newHashMap();
		bl1 = 0;
		bl2 = 0;
		bl3 = 0;
		bl4 = 0;
		bl5 = 0;
		bl6 = 0;
		
		plist = Lists.newArrayList();
		blist = Lists.newArrayList();
		elist = Lists.newArrayList();
		clist = Lists.newArrayList();
		ilist = Lists.newArrayList();
		
	}

	// #######################################产品###############################################################
	private void queryProduct() {
		init();
		for (TBrManifestData dataOne : dataList) {
			Long pid = dataOne.getConnId();
			TBrProductVo product = tBrProductService.queryOne(new TBrProduct(pid));
			if (product != null) {
				setISByP(pid);
				setESByP(product);
				setBSByP(product);
				getCategoryNameAndNum(categoryIds, categoryNameMap, categoryNumMap, categorysaleMap1, categorysaleMap2,categorysaleMap3, product);
				//p-p-pbeci  
				saveManifestProduct(product);
			}
		}
		tBrManifestProductService.addInBatch(plist);
		log.info("###init product over start calculate");
		// ******************************************
		initList100();
		// ******************************************
		HashMap<String, Object> map111 = Maps.newHashMap();
		getPie(categoryNameMap, categoryNumMap,map111);
		overManifest(mId, ManifestConstant.TYPE_PRODUCT, ManifestConstant.P_P_PIE, JsonUtil.toJsonStr(map111));
		// ******************************************
		HashMap<String, Object> map112 = Maps.newHashMap();
		map112.put("legend", categoryNameMap.values());
		map112.put("series1", categorysaleMap1.values()); // 本月
		map112.put("series2", categorysaleMap2.values()); // 上月
		map112.put("series3", categorysaleMap3.values()); // 半年
		overManifest(mId, ManifestConstant.TYPE_PRODUCT, ManifestConstant.P_P_BAR, JsonUtil.toJsonStr(map112));
		// ******************************************
		HashMap<String, Object> map121 = Maps.newHashMap();
		map121.put("bl1", bl1);
		map121.put("bl2", bl2);
		map121.put("bl3", bl3);
		map121.put("bl4", bl4);
		map121.put("bl5", bl5);
		map121.put("bl6", bl6);
		overManifest(mId, ManifestConstant.TYPE_PRODUCT, ManifestConstant.P_B_PIE1, JsonUtil.toJsonStr(map121));
//       ******************************************
		HashMap<String, Object> map122 = Maps.newHashMap();
		ArrayList<EnterpriseTopPo> list123 = Lists.newArrayList();
		getByBrands(brandMap.values(), map122, list123);
		overManifest(mId, ManifestConstant.TYPE_PRODUCT, ManifestConstant.P_B_PIE2, JsonUtil.toJsonStr(map122));
		overManifest(mId, ManifestConstant.TYPE_PRODUCT, ManifestConstant.P_B_TOP, JsonUtil.toJsonStr(list123));
		// ******************************************
		HashMap<String, Object> map131 = Maps.newHashMap();
		ArrayList<EnterpriseTopPo> list132 = Lists.newArrayList();
		ArrayList<KvBean> list133 = Lists.newArrayList();
		getByEnterprises(enterpriseMap.values(), map131, list132, list133,ManifestConstant.TYPE_PRODUCT);
		overManifest(mId, ManifestConstant.TYPE_PRODUCT, ManifestConstant.P_E_PIE, JsonUtil.toJsonStr(map131));
		overManifest(mId, ManifestConstant.TYPE_PRODUCT, ManifestConstant.P_E_TOP, JsonUtil.toJsonStr(list132));
		overManifest(mId, ManifestConstant.TYPE_PRODUCT, ManifestConstant.P_E_CN, JsonUtil.toJsonStr(list133));
		// ******************************************
		// tab4和tab1 相同   
		overManifest(mId, ManifestConstant.TYPE_PRODUCT, ManifestConstant.P_C_PIE, JsonUtil.toJsonStr(map111));
		overManifest(mId, ManifestConstant.TYPE_PRODUCT, ManifestConstant.P_C_BAR, JsonUtil.toJsonStr(map112));
		
		// ******************************************
		HashMap<Long, Integer> labelNumMap = Maps.newHashMap();
		HashMap<Long, String> labelNameMap = Maps.newHashMap();
		getIngredient(ingredientIds,  labelNumMap, labelNameMap);
		HashMap<String, Object> map151 = Maps.newHashMap(); 
		// ******************************************
		// 处理完毕，更新状态
		log.info("处理完毕，更新状态");
		overManifestStatus(mId);
		// ******************************************
		getPie(labelNameMap, labelNumMap,map151);
		overManifest(mId, ManifestConstant.TYPE_PRODUCT, ManifestConstant.P_I_PIE, JsonUtil.toJsonStr(map151));
		// 获取成分趋势
		Map<String, Object> map152 = tBrIngredientService.getIngredientTrend(ingredientMap.values());
		overManifest(mId, ManifestConstant.TYPE_PRODUCT, ManifestConstant.P_I_LINE, JsonUtil.toJsonStr(map152));
		//p-c-pbeci
		saveManifestCategory(categoryIds);	
	}
	

	private void setBSByP(TBrProductVo product) {
		TBrBrandVo brandVo = tBrBrandService.queryByProductId(product.getId());
		if (brandVo != null) {
			product.setProductBrandName(brandVo.getName());
			brandIds.add(brandVo.getId());
			brandMap.put(brandVo.getId(), brandVo);
			Byte level = brandVo.getLevel();
			if(level !=null){
				if (level == 1)
					bl1++;
				if (level == 2)
					bl2++;
				if (level == 3)
					bl3++;
				if (level == 4)
					bl4++;
				if (level == 5)
					bl5++;
				if (level == 6)
					bl6++;
			}
		}
	}

	private void setESByP(TBrProductVo product) {
		List<TBrEnterpriseVo> enterpriseList = tBrEnterpriseService.queryEnterpriseListByProductId(product.getId());
		String eNames = "";
		for (TBrEnterpriseVo tBrEnterprise : enterpriseList) {
			if (tBrEnterprise != null) {
				enterpriseIds.add(tBrEnterprise.getId());
				enterpriseMap.put(tBrEnterprise.getId(), tBrEnterprise);
				eNames+=tBrEnterprise.getEnterpriseName();
			}
		}
		product.setRealEnterpriseName(eNames);
	}

	private void setISByP(Long pid) {
		List<TBrIngredient> ingredientList = tBrIngredientService.queryTBrIngredientList(pid, true);
		for (TBrIngredient tBrIngredient : ingredientList) {
			ingredientIds.add(tBrIngredient.getId());
			ingredientMap.put(tBrIngredient.getId(), tBrIngredient);
		}
	}

	// #######################################品牌###################################################
	private void queryBrand() {
		init();
		int bl1PCount = 0, bl2PCount = 0, bl3PCount = 0, bl4PCount = 0, bl5PCount = 0, bl6PCount = 0;
		for (TBrManifestData dataOne : dataList) {
			Long bid = dataOne.getConnId();
			TBrBrandVo brand = tBrBrandService.queryOne(new TBrBrand(bid));
			List<TBrProductVo> productList = tBrProductService.queryProductVoListByBrandId(bid);
			HashSet<Object> eids4one = Sets.newHashSet();
			HashSet<Object> cids4one = Sets.newHashSet();
			for (TBrProductVo tBrProductVo : productList) {
				productIds.add(tBrProductVo.getId());
				List<TBrEnterpriseVo> enterpriseList = tBrEnterpriseService.queryEnterpriseListByProductId(tBrProductVo.getId());
				for (TBrEnterpriseVo tBrEnterprise : enterpriseList) {
					if (tBrEnterprise != null) {
						enterpriseIds.add(tBrEnterprise.getId());
						enterpriseMap.put(tBrEnterprise.getId(), tBrEnterprise);
						eids4one.add(tBrEnterprise.getId());
					}
				}
				List<TBrIngredient> ingredientList = tBrIngredientService.queryTBrIngredientList(tBrProductVo.getId(),true);
				for (TBrIngredient tBrIngredient : ingredientList) {
					ingredientIds.add(tBrIngredient.getId());
					ingredientMap.put(tBrIngredient.getId(), tBrIngredient);
				}
				getCategoryNameAndNum(categoryIds, categoryNameMap, categoryNumMap, categorysaleMap1, categorysaleMap2,categorysaleMap3, tBrProductVo);
				cids4one.add(tBrProductVo.getCategoryId());
			}
			Byte level = brand.getLevel();
			long count = productList.size();
			if(level !=null){
				if (level == 1) {
					bl1++;
					bl1PCount += count;
				}
				if (level == 2) {
					bl2++;
					bl2PCount += count;
				}
				if (level == 3) {
					bl3++;
					bl3PCount += count;
				}
				if (level == 4) {
					bl4++;
					bl4PCount += count;
				}
				if (level == 5) {
					bl5++;
					bl5PCount += count;
				}
				if (level == 6) {
					bl6++;
					bl6PCount += count;
				}
			}
			//b-b-bpeci
			brand.setEenum(eids4one.size());
			brand.setCnum(cids4one.size());
			brand.setPnum(Integer.parseInt(count+""));
			saveManifestBrand(brand);
		}
		tBrManifestBrandService.addInBatch(blist);
		//b-p-bpeci
		getByProductIds(productIds);
		log.info("###init brand over start calculate");
		// ******************************************
		initList200();
		// ******************************************
		HashMap<String, Object> map211 = Maps.newHashMap();
		List<KvBean> series211 = Lists.newArrayList();
		String[] legend = { "顶级品牌", "一线品牌", "二线品牌", "三线品牌", "四线品牌", "其他品牌" };
		kvBean = new KvBean();kvBean.setName(legend[0]); kvBean.setValue(bl1); series211.add(kvBean);
		kvBean = new KvBean();kvBean.setName(legend[1]); kvBean.setValue(bl2); series211.add(kvBean);
		kvBean = new KvBean();kvBean.setName(legend[2]); kvBean.setValue(bl3); series211.add(kvBean);
		kvBean = new KvBean();kvBean.setName(legend[3]); kvBean.setValue(bl4); series211.add(kvBean);
		kvBean = new KvBean();kvBean.setName(legend[4]); kvBean.setValue(bl5); series211.add(kvBean);
		kvBean = new KvBean();kvBean.setName(legend[5]); kvBean.setValue(bl6); series211.add(kvBean);
		
		map211.put("legend", legend);
		map211.put("series", series211);
		overManifest(mId, ManifestConstant.TYPE_BRAND, ManifestConstant.B_B_PIE1, JsonUtil.toJsonStr(map211));

		ArrayList<KvBean> list212 = Lists.newArrayList();
		kvBean = new KvBean("1", bl1PCount);
		list212.add(kvBean);
		kvBean = new KvBean("2", bl2PCount);
		list212.add(kvBean);
		kvBean = new KvBean("3", bl3PCount);
		list212.add(kvBean);
		kvBean = new KvBean("4", bl4PCount);
		list212.add(kvBean);
		kvBean = new KvBean("5", bl5PCount);
		list212.add(kvBean);
		kvBean = new KvBean("6", bl6PCount);
		list212.add(kvBean);
		list212.sort((a, b) -> ((Integer) b.getValue()).compareTo((Integer) a.getValue()));
		overManifest(mId, ManifestConstant.TYPE_BRAND, ManifestConstant.B_B_TOP1, JsonUtil.toJsonStr(list212));
		// ******************************************
		HashMap<String, Object> map213 = Maps.newHashMap();
		getPie(categoryNameMap, categoryNumMap,map213);
		overManifest(mId, ManifestConstant.TYPE_BRAND, ManifestConstant.B_B_PIE2, JsonUtil.toJsonStr(map213));
//		ArrayList<EnterpriseTopPo> list214 = Lists.newArrayList();
//		ArrayList<EnterpriseTopPo> list214Tmp = Lists.newArrayList();
//		int size2 = enterpriseMap.values().size();
//		for (TBrEnterprise distinctEnterprise : enterpriseMap.values()) {
//			log.info("queryBrand enterpriseMap:"+size2--);
//			list214Tmp.add(getETop( distinctEnterprise));
//		}
//		list214Tmp.sort((a, b) -> b.getEnterpriseWeight().compareTo(a.getEnterpriseWeight()));
//		int size = list214Tmp.size()>10?10:list214Tmp.size();
//		for (int i=0 ; i< size ; i++) {
//			list214.add(list214Tmp.get(i));
//		}
//		overManifest(mId, ManifestConstant.TYPE_BRAND, ManifestConstant.B_B_TOP2, JsonUtil.toJsonStr(list214));
		//  B_B_TOP2和B_E_TOP 相同
		// ******************************************
		HashMap<String, Object> map231 = Maps.newHashMap();
		ArrayList<EnterpriseTopPo> list232 = Lists.newArrayList();
		ArrayList<KvBean> list233 = Lists.newArrayList();
		getByEnterprises(enterpriseMap.values(), map231, list232, list233,ManifestConstant.TYPE_BRAND);
		overManifest(mId, ManifestConstant.TYPE_BRAND, ManifestConstant.B_B_TOP2, JsonUtil.toJsonStr(list232));
		overManifest(mId, ManifestConstant.TYPE_BRAND, ManifestConstant.B_E_PIE, JsonUtil.toJsonStr(map231));
		overManifest(mId, ManifestConstant.TYPE_BRAND, ManifestConstant.B_E_TOP, JsonUtil.toJsonStr(list232));
		overManifest(mId, ManifestConstant.TYPE_BRAND, ManifestConstant.B_E_CN, JsonUtil.toJsonStr(list233));

		// ******************************************
		// tab4-1和tab1-3相同
		overManifest(mId, ManifestConstant.TYPE_BRAND, ManifestConstant.B_C_PIE, JsonUtil.toJsonStr(map213));
		// 类似112
		HashMap<String, Object> map242 = Maps.newHashMap();
		map242.put("legend", categoryNameMap.values());
		map242.put("series1", categorysaleMap1.values()); // 本月
		map242.put("series2", categorysaleMap2.values()); // 上月
		map242.put("series3", categorysaleMap3.values()); // 半年
		overManifest(mId, ManifestConstant.TYPE_BRAND, ManifestConstant.B_C_BAR, JsonUtil.toJsonStr(map242));
		// ******************************************
		// 处理完毕，更新状态
		overManifestStatus(mId);
		// ******************************************
		//b-c-bpeci
		saveManifestCategory(categoryIds);	
		// ******************************************
		HashMap<Long, Integer> labelNumMap = Maps.newHashMap();
		HashMap<Long, String> labelNameMap = Maps.newHashMap();
		getIngredient(ingredientIds,  labelNumMap, labelNameMap);
		HashMap<String, Object> map251 = Maps.newHashMap();
		getPie(labelNameMap, labelNumMap,map251);
		overManifest(mId, ManifestConstant.TYPE_BRAND, ManifestConstant.B_I_PIE, JsonUtil.toJsonStr(map251));
		// 获取成分趋势
		Map<String, Object> map252 = tBrIngredientService.getIngredientTrend(ingredientMap.values());
		overManifest(mId, ManifestConstant.TYPE_BRAND, ManifestConstant.B_I_LINE, JsonUtil.toJsonStr(map252));
		// ******************************************

	}


	// #######################################企业###############################################################
	private void queryEnterprise() {
	
		init();
		for (TBrManifestData dataOne : dataList) {
			Long eid = dataOne.getConnId();
			TBrEnterpriseVo enterprise = tBrEnterpriseService.queryOne(new TBrEnterprise(eid));
			enterpriseMap.put(enterprise.getId(), enterprise);
			List<TBrProductVo> pvoList = tBrProductService.queryProductVoListByRealEnterpriseId(eid);
			for (TBrProductVo product : pvoList) {
				productIds.add(product.getId());
				TBrBrandVo brandVo = tBrBrandService.queryByProductId(product.getId());
				if (brandVo != null) {
					brandIds.add(brandVo.getId());
					brandMap.put(brandVo.getId(), brandVo);
					Byte level = brandVo.getLevel();
					if(level !=null){
						if (level == 1)
							bl1++;
						if (level == 2)
							bl2++;
						if (level == 3)
							bl3++;
						if (level == 4)
							bl4++;
						if (level == 5)
							bl5++;
						if (level == 6)
							bl6++;
					}
				}
				getCategoryNameAndNum(categoryIds, categoryNameMap, categoryNumMap, categorysaleMap1, categorysaleMap2,categorysaleMap3, product);
			}
		}
		
		//e-p-ebpc
		getByProductIds(productIds);
		log.info("###init enterprise over start calculate");
//		 ******************************************
		initList300();
		// ******************************************
		HashMap<String, Object> map311 = Maps.newHashMap();
		ArrayList<EnterpriseTopPo> list312 = Lists.newArrayList();
		ArrayList<KvBean> list313 = Lists.newArrayList();
		getByEnterprises(enterpriseMap.values(), map311, list312, list313,null);
		overManifest(mId, ManifestConstant.TYPE_ENTERPRISE, ManifestConstant.E_E_PIE, JsonUtil.toJsonStr(map311));
		overManifest(mId, ManifestConstant.TYPE_ENTERPRISE, ManifestConstant.E_E_TOP, JsonUtil.toJsonStr(list312));
		overManifest(mId, ManifestConstant.TYPE_ENTERPRISE, ManifestConstant.E_E_CN, JsonUtil.toJsonStr(list313));
		// ******************************************
		HashMap<String, Object> map321 = Maps.newHashMap();		
		map321.put("bl1", bl1);
		map321.put("bl2", bl2);
		map321.put("bl3", bl3);
		map321.put("bl4", bl4);
		map321.put("bl5", bl5);
		map321.put("bl6", bl6);
		overManifest(mId, ManifestConstant.TYPE_ENTERPRISE, ManifestConstant.E_B_PIE1, JsonUtil.toJsonStr(map321));
		// ******************************************
		HashMap<String, Object> map322 = Maps.newHashMap();
		ArrayList<EnterpriseTopPo> list323 = Lists.newArrayList();
		getByBrands(brandMap.values(), map322, list323);
		overManifest(mId, ManifestConstant.TYPE_ENTERPRISE, ManifestConstant.E_B_PIE2, JsonUtil.toJsonStr(map322));
		overManifest(mId, ManifestConstant.TYPE_ENTERPRISE, ManifestConstant.E_B_TOP, JsonUtil.toJsonStr(list323));
		// ******************************************
		HashMap<String, Object> map341 = Maps.newHashMap();
		getPie(categoryNameMap, categoryNumMap,map341);
		overManifest(mId, ManifestConstant.TYPE_ENTERPRISE, ManifestConstant.E_C_PIE, JsonUtil.toJsonStr(map341));
		// ******************************************
		overManifestStatus(mId);
		// ******************************************
		HashMap<String, Object> map342 = Maps.newHashMap();
		map342.put("legend", categoryNameMap.values());
		map342.put("series1", categorysaleMap1.values()); // 本月
		map342.put("series2", categorysaleMap2.values()); // 上月
		map342.put("series3", categorysaleMap3.values()); // 半年
		overManifest(mId, ManifestConstant.TYPE_ENTERPRISE, ManifestConstant.E_C_BAR, JsonUtil.toJsonStr(map342));
		//e-c-ebpc
		saveManifestCategory(categoryIds);	
	}
	// ###########################成分####################################
	private void queryIngredient() {
		init();
		HashSet<Long> labelIds = Sets.newHashSet();
		HashMap<Long, Integer> labelNumMap = Maps.newHashMap();
		HashMap<Long, String> labelNameMap = Maps.newHashMap();
		int iSize =dataList.size();
		for (TBrManifestData dataOne : dataList) {
			log.info("iSize=="+iSize--);
			Long iid = dataOne.getConnId();
			ingredientIds.add(iid);
			List<TBrProductVo> pvoList = tBrProductService.queryProductVoListByIngredientId(iid);
			int pvoSize =pvoList.size();
			for (TBrProductVo product : pvoList) {
				log.info("pvoSize=="+pvoSize--);
				productIds.add(product.getId());
				productMap.put(product.getId(), product);
				List<TBrEnterpriseVo> enterpriseList = tBrEnterpriseService.queryEnterpriseListByProductId(product.getId());
				for (TBrEnterpriseVo tBrEnterprise : enterpriseList) {
					if (tBrEnterprise != null) {
						enterpriseIds.add(tBrEnterprise.getId());
						enterpriseMap.put(tBrEnterprise.getId(), tBrEnterprise);
					}
				}
				TBrBrandVo brandVo = tBrBrandService.queryByProductId(product.getId());
				if (brandVo != null) {
					brandIds.add(brandVo.getId());
					brandMap.put(brandVo.getId(), brandVo);
					Byte level = brandVo.getLevel();
					if(level !=null){
						if (level == 1)
							bl1++;
						if (level == 2)
							bl2++;
						if (level == 3)
							bl3++;
						if (level == 4)
							bl4++;
						if (level == 5)
							bl5++;
						if (level == 6)
							bl6++;
					}
				}
				getCategoryNameAndNum(categoryIds, categoryNameMap, categoryNumMap, categorysaleMap1, categorysaleMap2,categorysaleMap3, product);
			}
			getILabel(labelNumMap, labelNameMap, labelIds, dataOne.getConnId());
//			i-i-ibecp
			try {
				saveManifestIngredient(iid);
			} catch (Exception e) {
				log.info("###saveManifestIngredient exception i");
				e.printStackTrace();
			}
		}
		tBrManifestIngredientService.addInBatch(ilist);
		//i-p-ibecp
		getByProductIds(productIds);
		log.info("###init ingredient over start calculate");
		// ******************************************
		initList400();
		// ******************************************
		HashMap<String, Object> map411 = Maps.newHashMap();
		getPie(labelNameMap, labelNumMap,map411);
		overManifest(mId, ManifestConstant.TYPE_INGREDIENT, ManifestConstant.I_I_PIE1, JsonUtil.toJsonStr(map411));
		// ******************************************
		HashMap<String, Object> map412 = Maps.newHashMap(); 
		getPie(categoryNameMap, categoryNumMap,map412);
		overManifest(mId, ManifestConstant.TYPE_INGREDIENT, ManifestConstant.I_I_PIE2, JsonUtil.toJsonStr(map412));
		// ******************************************
		HashMap<String, Object> map421 = Maps.newHashMap();
		map421.put("bl1", bl1);
		map421.put("bl2", bl2);	
		map421.put("bl3", bl3);
		map421.put("bl4", bl4);
		map421.put("bl5", bl5);
		map421.put("bl6", bl6);
		overManifest(mId, ManifestConstant.TYPE_INGREDIENT, ManifestConstant.I_B_PIE1, JsonUtil.toJsonStr(map421));
		// ******************************************
		HashMap<String, Object> map422 = Maps.newHashMap();
		ArrayList<EnterpriseTopPo> list423 = Lists.newArrayList();
		getByBrands(brandMap.values(), map422, list423);
		overManifest(mId, ManifestConstant.TYPE_INGREDIENT, ManifestConstant.I_B_PIE2, JsonUtil.toJsonStr(map422));
		overManifest(mId, ManifestConstant.TYPE_INGREDIENT, ManifestConstant.I_B_TOP, JsonUtil.toJsonStr(list423));
		// ******************************************
		HashMap<String, Object> map431 = Maps.newHashMap();
		ArrayList<EnterpriseTopPo> list432 = Lists.newArrayList();
		ArrayList<KvBean> list433 = Lists.newArrayList();
		getByEnterprises(enterpriseMap.values(), map431, list432, list433,ManifestConstant.TYPE_INGREDIENT);
		overManifest(mId, ManifestConstant.TYPE_INGREDIENT, ManifestConstant.I_E_PIE, JsonUtil.toJsonStr(map431));
		overManifest(mId, ManifestConstant.TYPE_INGREDIENT, ManifestConstant.I_E_TOP, JsonUtil.toJsonStr(list432));
		overManifest(mId, ManifestConstant.TYPE_INGREDIENT, ManifestConstant.I_E_CN, JsonUtil.toJsonStr(list433));
		// ******************************************
		// 441和412相同
		overManifest(mId, ManifestConstant.TYPE_INGREDIENT, ManifestConstant.I_C_PIE, JsonUtil.toJsonStr(map412));
		// ******************************************
		// 处理完毕，更新状态
		overManifestStatus(mId);
		// ******************************************
		HashMap<String, Object> map442 = Maps.newHashMap();
		map442.put("legend", categoryNameMap.values());
		map442.put("series1", categorysaleMap1.values()); // 本月
		map442.put("series2", categorysaleMap2.values()); // 上月
		map442.put("series3", categorysaleMap3.values()); // 半年
		overManifest(mId, ManifestConstant.TYPE_INGREDIENT, ManifestConstant.I_C_BAR, JsonUtil.toJsonStr(map442));
		//i-c-ibecp
		saveManifestCategory(categoryIds);
	}

	// ####################################END####################################################################
	private void initList100() {
		ArrayList<KvBean> list100 = Lists.newArrayList();
		kvBean = new KvBean("关注产品数", dataList.size());
		list100.add(kvBean);
		brandIds.remove(null);
		kvBean = new KvBean("关联品牌", brandIds.size());
		list100.add(kvBean);
		categoryIds.remove(null);
		kvBean = new KvBean("关联品类", categoryIds.size());
		list100.add(kvBean);
		enterpriseIds.remove(null);
		kvBean = new KvBean("关联供应商", enterpriseIds.size());
		list100.add(kvBean);
		overManifest(mId, ManifestConstant.TYPE_PRODUCT, ManifestConstant.P_BASE, JsonUtil.toJsonStr(list100));
	}

	private void initList200() {
		ArrayList<KvBean> list200 = Lists.newArrayList();
		kvBean = new KvBean("关注品牌", dataList.size());
		list200.add(kvBean);
		productIds.remove(null);
		kvBean = new KvBean("关联产品数", productIds.size());
		list200.add(kvBean);
		categoryIds.remove(null);
		kvBean = new KvBean("关联品类", categoryIds.size());
		list200.add(kvBean);
		enterpriseIds.remove(null);
		kvBean = new KvBean("关联供应商", enterpriseIds.size());
		list200.add(kvBean);
		overManifest(mId, ManifestConstant.TYPE_BRAND, ManifestConstant.B_BASE, JsonUtil.toJsonStr(list200));
	}

	private void initList300() {
		ArrayList<KvBean> list300 = Lists.newArrayList();
		kvBean = new KvBean("关注供应商", dataList.size());
		list300.add(kvBean);
		brandIds.remove(null);
		kvBean = new KvBean("关联品牌", brandIds.size());
		list300.add(kvBean);
		productIds.remove(null);
		kvBean = new KvBean("关联产品数", productIds.size());
		list300.add(kvBean);
		categoryIds.remove(null);
		kvBean = new KvBean("关联品类", categoryIds.size());
		list300.add(kvBean);
		overManifest(mId, ManifestConstant.TYPE_ENTERPRISE, ManifestConstant.E_BASE, JsonUtil.toJsonStr(list300));
	}

	private void initList400() {
		ArrayList<KvBean> list400 = Lists.newArrayList();
		kvBean = new KvBean("关注成分", dataList.size());
		list400.add(kvBean);
		productIds.remove(null);
		kvBean = new KvBean("关联产品", productIds.size());
		list400.add(kvBean);
		brandIds.remove(null);
		kvBean = new KvBean("关联品牌", brandIds.size());
		list400.add(kvBean);
		enterpriseIds.remove(null);
		kvBean = new KvBean("关联供应商", enterpriseIds.size());
		list400.add(kvBean);
		overManifest(mId, ManifestConstant.TYPE_INGREDIENT, ManifestConstant.I_BASE, JsonUtil.toJsonStr(list400));
	}
	
	private void initListX30(Integer eSize,Integer bSize, Integer cSize, Integer pSize,  Byte type) {
		if(type != null) {
			ArrayList<KvBean> listX30 = Lists.newArrayList();
			enterpriseIds.remove(null);
			kvBean = new KvBean("关联供应商", eSize);
			listX30.add(kvBean);
			brandIds.remove(null);
			kvBean = new KvBean("供应合作品牌", bSize);
			listX30.add(kvBean);
			kvBean = new KvBean("供应商生成品类", cSize);
			listX30.add(kvBean);
			productIds.remove(null);
			kvBean = new KvBean("供应商备案产品数", pSize);
			listX30.add(kvBean);
			Integer keyId = 0;
			if(type==ManifestConstant.TYPE_PRODUCT){
				keyId = ManifestConstant.P_E_BASE;
			}else if(type==ManifestConstant.TYPE_BRAND){
				keyId = ManifestConstant.B_E_BASE;
			}else if(type==ManifestConstant.TYPE_INGREDIENT){
				keyId = ManifestConstant.I_E_BASE;
			}
			overManifest(mId,type,keyId, JsonUtil.toJsonStr(listX30));
		}
	}
	
	
	//***************以下5个方法用于保存*********************
	//保存清单产品列表
	private void saveManifestProduct(TBrProductVo product) {
		TBrManifestProduct tBrManifestProduct = new TBrManifestProduct();
		BeanUtils.copyProperties(product, tBrManifestProduct,"id");
		tBrManifestProduct.setManifestId(mId);
		tBrManifestProduct.setProductId(product.getId());
		BaseDataUtil.setDefaultData(tBrManifestProduct);
		plist.add(tBrManifestProduct);
		if(plist.size()>30){
			log.info("###saveManifestProduct--30");
			tBrManifestProductService.addInBatch(plist);
			plist.clear();
		}
	}
	
	private void saveManifestBrand(TBrBrandVo brand){
		TBrManifestBrand tBrManifestBrand = new TBrManifestBrand();
		BeanUtils.copyProperties(brand, tBrManifestBrand,"id");
		tBrManifestBrand.setManifestId(mId);
		tBrManifestBrand.setBrandId(brand.getId());
		BaseDataUtil.setDefaultData(tBrManifestBrand);
		blist.add(tBrManifestBrand);
		if(blist.size()>30){
			log.info("###saveManifestBrand--30");
			tBrManifestBrandService.addInBatch(blist);
			blist.clear();
		}
	}
	
	
	private void saveManifestEnterprise(TBrEnterpriseVo enterprise){
		TBrManifestEnterprise tBrManifestEnterprise = new TBrManifestEnterprise();
		BeanUtils.copyProperties(enterprise, tBrManifestEnterprise,"id");
		tBrManifestEnterprise.setManifestId(mId);
		tBrManifestEnterprise.setEnterpriseId(enterprise.getId());
		BaseDataUtil.setDefaultData(tBrManifestEnterprise);
		elist.add(tBrManifestEnterprise);
		if(elist.size()>30){
			log.info("###saveManifestEnterprise--30");
			tBrManifestEnterpriseService.addInBatch(elist);
			elist.clear();
		}
	}
	
	
	
	
	private void saveManifestCategory(HashSet<Long> categoryIds){
		try {
			categoryIds.remove(null);
			TBrProductQuery tBrProductQuery = new TBrProductQuery();
			int csize = categoryIds.size();
			for (Long categoryId : categoryIds) {
				log.info("###saveManifestCategory - csize= "+csize--);
				Integer taobaoSale =0;
				BigDecimal taobaoTurnover = BigDecimal.ZERO;
				TBrCategory category = tBrCategoryService.queryById(categoryId);
				if(category == null){
					continue;
				}
				tBrProductQuery.setCategoryId(categoryId);
				List<TBrProduct> pList = tBrProductService.queryList(tBrProductQuery);
				HashSet<Long> bids = Sets.newHashSet();
				HashSet<Long> eids = Sets.newHashSet();
				for (TBrProduct tBrProduct : pList) {
					if(tBrProduct!=null){
						Integer taobaoSaleTmp = tBrProduct.getTaobaoSale();
						if(taobaoSaleTmp != null){
							taobaoSale+=taobaoSaleTmp;
						}
						BigDecimal taobaoTurnoverTmp = tBrProduct.getTaobaoTurnover();
						if(taobaoTurnoverTmp != null){
							taobaoTurnover = taobaoTurnover.add(taobaoTurnoverTmp);
						}
						TBrBrandVo brand = tBrBrandService.queryByProductId(tBrProduct.getId());
						if(brand !=null){
							bids.add(brand.getId());
						}
						List<TBrEnterpriseVo> eList = tBrEnterpriseService.queryEnterpriseListByProductId(tBrProduct.getId());
						for (TBrEnterpriseVo tBrEnterpriseVo : eList) {
							eids.add(tBrEnterpriseVo.getId());
						}
					}
				}
				TBrManifestCategory tBrManifestCategory = new TBrManifestCategory();
				tBrManifestCategory.setManifestId(mId);
				tBrManifestCategory.setCategoryId(categoryId);
				tBrManifestCategory.setName(category.getName());
				tBrManifestCategory.setPnum(pList.size());
				tBrManifestCategory.setBnum(bids.size());
				tBrManifestCategory.setEenum(eids.size());
				tBrManifestCategory.setTaobaoSale(taobaoSale);
				tBrManifestCategory.setTaobaoTurnover(taobaoTurnover);
				BaseDataUtil.setDefaultData(tBrManifestCategory);
//				clist.add(tBrManifestCategory);
				log.info("###saveManifestCategory--each");
				tBrManifestCategoryService.add(tBrManifestCategory);
			}
//			tBrManifestCategoryService.addInBatch(clist);
		} catch (Exception e) {
			log.info("saveManifestCategory exception");
			e.printStackTrace();
		}
		
	}
	
	
	private void saveManifestIngredient(Long iid) throws Exception{
		TBrIngredientVo ingredient = tBrIngredientService.queryOne(new TBrIngredient(iid));
		List<TBrProductVo> pList = tBrProductService.queryProductVoListByIngredientId(iid);
		Integer taobaoSale =0;
		BigDecimal taobaoTurnover = BigDecimal.ZERO;
		for (TBrProductVo tBrProductVo : pList) {
			if(tBrProductVo!= null){
				Integer taobaoSaleTmp = tBrProductVo.getTaobaoSale();
				if(taobaoSaleTmp!=null){
					taobaoSale+=taobaoSaleTmp;
				}
				BigDecimal taobaoTurnoverTmp = tBrProductVo.getTaobaoTurnover();
				if(taobaoTurnoverTmp!=null){
					taobaoTurnover = taobaoTurnover.add(taobaoTurnoverTmp);
				}
				
			}
		}
		tBrIngredientService.getAims(ingredient);
		TBrManifestIngredient tBrManifestIngredient = new TBrManifestIngredient();
		tBrManifestIngredient.setManifestId(mId);
		tBrManifestIngredient.setIngredientId(iid);
		tBrManifestIngredient.setPnum(pList.size());
		tBrManifestIngredient.setTaobaoSale(taobaoSale);
		tBrManifestIngredient.setTaobaoTurnover(taobaoTurnover);
		tBrManifestIngredient.setAims(tBrManifestIngredient.getAims());
		tBrManifestIngredient.setName(ingredient.getName());
		BaseDataUtil.setDefaultData(tBrManifestIngredient);
		ilist.add(tBrManifestIngredient);
		if(ilist.size()>30){
			log.info("###saveManifestIngredient--30");
			tBrManifestIngredientService.addInBatch(ilist);
			ilist.clear();
		}
	}
	
}
