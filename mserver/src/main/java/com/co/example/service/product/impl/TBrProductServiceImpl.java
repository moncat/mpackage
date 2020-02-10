package com.co.example.service.product.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.assertj.core.util.Sets;
import org.eclipse.jetty.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.co.example.common.constant.Constant;
import com.co.example.common.utils.HttpUtils;
import com.co.example.common.utils.PageReq;
import com.co.example.dao.product.TBrProductDao;
import com.co.example.entity.label.TBrProductLabel;
import com.co.example.entity.label.aide.TBrProductLabelQuery;
import com.co.example.entity.product.TBrAim;
import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.TBrIngredientAim;
import com.co.example.entity.product.TBrLog;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.TBrProductEnterprise;
import com.co.example.entity.product.TBrProductImage;
import com.co.example.entity.product.TBrProductIngredient;
import com.co.example.entity.product.TBrProductMore;
import com.co.example.entity.product.aide.BeVo;
import com.co.example.entity.product.aide.ConfirmVo;
import com.co.example.entity.product.aide.ProductConstant;
import com.co.example.entity.product.aide.TBrAimQuery;
import com.co.example.entity.product.aide.TBrEnterpriseQuery;
import com.co.example.entity.product.aide.TBrIngredientQuery;
import com.co.example.entity.product.aide.TBrIngredientVo;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.entity.product.aide.TBrProductVo;
import com.co.example.entity.user.TBrUserPlanLabel;
import com.co.example.entity.user.aide.TBrUserPlanLabelQuery;
import com.co.example.service.label.TBrProductLabelService;
import com.co.example.service.product.TBrAimService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrIngredientAimService;
import com.co.example.service.product.TBrIngredientService;
import com.co.example.service.product.TBrLogService;
import com.co.example.service.product.TBrProductEnterpriseService;
import com.co.example.service.product.TBrProductImageService;
import com.co.example.service.product.TBrProductIngredientService;
import com.co.example.service.product.TBrProductMoreService;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.question.TBrQuestionTypeLabelService;
import com.co.example.service.region.TBrRegionService;
import com.co.example.service.user.TBrUserPlanItemService;
import com.co.example.service.user.TBrUserPlanLabelService;
import com.co.example.service.user.TBrUserPlanService;
import com.co.example.utils.BaseDataUtil;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TBrProductServiceImpl extends BaseServiceImpl<TBrProduct, Long> implements TBrProductService {
	@Resource
	private TBrProductDao tBrProductDao;

	@Resource
	private TBrIngredientService tBrIngredientService;

	@Resource
	private TBrProductIngredientService tBrProductIngredientService;

	@Resource
	private TBrLogService tBrLogService;

	@Resource
	private TBrProductImageService tBrProductImageService;

	@Resource
	private TBrAimService tBrAimService;

	@Resource
	private TBrIngredientAimService tBrIngredientAimService;

	@Resource
	private TBrEnterpriseService tBrEnterpriseService;

	@Resource
	private TBrProductEnterpriseService tBrProductEnterpriseService;

	@Resource
	private TBrUserPlanService tBrUserPlanService;

	@Resource
	private TBrUserPlanItemService tBrUserPlanItemService;

	@Resource
	private TBrQuestionTypeLabelService tBrQuestionTypeLabelService;

	@Resource
	private TBrUserPlanLabelService tBrUserPlanLabelService;

	@Resource
	private TBrProductLabelService tBrProductLabelService;

	@Resource
	private TBrRegionService tBrRegionService;
	
	@Resource
	private TBrProductMoreService tBrProductMoreService;

	@Override
	protected BaseDao<TBrProduct, Long> getBaseDao() {
		return tBrProductDao;
	}

	private Long pId = null;

	@Override
	@Deprecated
	public int addProductFromCFDA(String page, String dateStr) {
		// 记录json数据
		HashMap<String, String> params = Maps.newHashMap();
		params.put("on", "true");
		params.put("page", page); // 67133
		params.put("pageSize", "15");
		String text = HttpUtils.postData(ProductConstant.CFDA_PRODUCT_URL, params);
		if (StringUtils.isBlank(text)) {
			saveLog(ProductConstant.PRODUCT_SOURCE_CFDA, ProductConstant.CFDA_PRODUCT_URL, -1, "该页无数据" + page, null);
			log.info("***该页无数据*** " + page);
			return 2;
		}
		Integer index = 0;
		try {
			TBrProduct tBrProduct = null;
			JSONObject base = JSON.parseObject(text);
			JSONArray productList = base.getJSONArray("list");
			JSONObject product = null;

			if (CollectionUtils.isNotEmpty(productList)) {
				for (int i = 0; i < productList.size(); i++) {
					index = i;
					product = productList.getJSONObject(i);
					String date = product.getString("provinceConfirm");
					date = date.replace(" ", "");
					if (StringUtils.isBlank(dateStr)) {
						// 不设置时间，全量更新
					} else {
						// 设置时间，增量更新
						if (date != null && !date.equals("") && !date.equals(dateStr)) {
							// 增量截止
							saveLog(ProductConstant.PRODUCT_SOURCE_CFDA, ProductConstant.CFDA_PRODUCT_URL, index,
									HttpUtils.getRequestData(params).toString(), null);
							return 1;
						}
					}

					String productName = product.getString("productName");
					// 2019-02-23 添加。
					String applySn = product.getString("applySn");
					// 2019年9月2日 去掉去重逻辑，商品每4年需要备案一次，商品名称不变化，但其中的成分可能会变化，需要重新抓取。
					/** 2019年9月2日 del start */
					// TBrProductQuery tBrProductQuery = new TBrProductQuery();
					// tBrProductQuery.setProductName(productName);
					// tBrProductQuery.setApplySn(applySn);
					// List<TBrProduct> oldList = queryList(tBrProductQuery);
					// //名称已经重复，则进入下一次循环（重复原因：1网上数据错误 2增量补传时网站又更新了数据，导致起始页面不正确）
					// if(CollectionUtils.isNotEmpty(oldList)){
					// log.info("***已经有*** "+productName);
					// continue;
					// }else{
					// log.info("^^^没有^^^ "+productName);
					// }
					// Long munber = queryCount(tBrProductQuery);
					// if(munber!=null && munber > 0){
					// log.info("***已经有*** "+productName);
					// continue;
					// }
					/** 2019年9月2日 del end */

					String applySnNew = product.getString("applySn");
					// 组织产品基础数据，在抓成分时，一并保存
					tBrProduct = new TBrProduct();
					tBrProduct.setProductName(productName);
					tBrProduct.setApplyType(ProductConstant.PRODUCT_APPLYTYPE_DOMESTIC);
					tBrProduct.setApplySn(applySnNew);
					tBrProduct.setEnterpriseName(product.getString("enterpriseName"));
					tBrProduct.setEnterpriseNameEn(null);
					tBrProduct.setProducingArea(null);
					/** 2019年8月2日 add start */
					if (StringUtils.isNotBlank(applySnNew) && applySnNew.contains("已注销")) {
						tBrProduct.setMoreData2("1"); // 标记该产品已经注销 用于增量处理。
					}

					/** 2019年8月2日 add end */
					tBrProduct.setConfirmDate(date);
					String isOff = product.getString("is_off");
					if (StringUtils.isEmpty(isOff) || isOff.toUpperCase().equals("N")) {
						tBrProduct.setIsOff(ProductConstant.PRODUCT_ISOFF_NO);
					} else {
						tBrProduct.setIsOff(ProductConstant.PRODUCT_ISOFF_YES);
					}
					String processid = product.getString("processid");
					tBrProduct.setCfdaProcessid(processid);
					tBrProduct.setCfdaNewProcessid(product.getString("newProcessid"));
					tBrProduct.setBevolMid(null);
					tBrProduct.setSource(ProductConstant.PRODUCT_SOURCE_CFDA);
					tBrProduct.setJdUrl("0");
					tBrProduct.setTmallUrl("0");
					tBrProduct.setCreateTime(new Date());
					tBrProduct.setDelFlg(Constant.NO);
					// 作废
					// tBrProduct = addIngredientFromCFDA(tBrProduct,
					// processid);
					// addImageFromCFDA(tBrProduct, processid);
				}
			}
			saveLog(ProductConstant.PRODUCT_SOURCE_CFDA, ProductConstant.CFDA_PRODUCT_URL, productList.size(),
					HttpUtils.getRequestData(params).toString(), null);
		} catch (Exception e) {
			log.info("error", e.fillInStackTrace());
			// e.printStackTrace();
			saveLog(ProductConstant.PRODUCT_SOURCE_CFDA, ProductConstant.CFDA_PRODUCT_URL, index,
					HttpUtils.getRequestData(params).toString(), e);
		}
		return 0;
	}

	/**
	 * 先查询是否有该企业，有则查询出id并返回 查询有该企业时，查看其is_bus状态，是运营企业，如果没设置为1，则更新为1
	 * 查询无该企业，新增该企业，保存is_bus状态，返回id
	 */
	private Long saveBusE(TBrEnterprise tBrEnterpriseBus) {
		Long id = 0l;
		String enterpriseName = tBrEnterpriseBus.getEnterpriseName();
		if (StringUtils.isNoneBlank(enterpriseName)) {
			// log.info("**保存运营企业**");
			TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
			tBrEnterpriseQuery.setEnterpriseName(enterpriseName);
			List<TBrEnterprise> tBrEnterpriseList = tBrEnterpriseService.queryList(tBrEnterpriseQuery);
			// 如果实际生产企业在库中不存在
			if (CollectionUtils.isEmpty(tBrEnterpriseList)) {
				// 设置省份，根据名称和简称进行匹配
				tBrRegionService.setEnterpriseRegionByAll(tBrEnterpriseBus);
				tBrEnterpriseService.add(tBrEnterpriseBus);
				id = tBrEnterpriseBus.getId();
				log.info("**该运营企业不存在，保存并生成**" + id);
			} else {
				// log.info("**该运营企业已经存在**");
				TBrEnterprise tBrEnterpriseBus2 = tBrEnterpriseList.get(0);
				id = tBrEnterpriseBus2.getId();
				Byte isBus = tBrEnterpriseBus2.getIsBus();
				if (isBus != Constant.YES) {
					log.info("**该运营企业NO设置是运营企业的标识，更新一下**" + id);
					TBrEnterprise tBrEnterpriseUpdate = new TBrEnterprise();
					tBrEnterpriseUpdate.setIsBus(Constant.YES);
					tBrEnterpriseUpdate.setId(id);
					tBrEnterpriseService.updateByIdSelective(tBrEnterpriseUpdate);
				} else {
					// log.info("**该运营企业YES设置是运营企业的标识**" + id);
				}
			}
		}
		return id;

	}

	// 保存成分 药监局 保存生产企业
	@SuppressWarnings("deprecation")
	private TBrProduct addIngredientFromCFDA(TBrProduct tBrProduct) {
		String text = "";
		try {
			TBrProductQuery tBrProductQuery = new TBrProductQuery();
			text = FileUtils.readFileToString(
					new File(ProductConstant.CFDA_FILE_PATH + dateStr + "/info_" + processid + ".json"));
			// 处理部分产品信息
			int indexOf = text.lastIndexOf("}{");
			if (indexOf > -1) {
				text = text.substring(indexOf + 1);
				log.info("json有问题，截取最后一个对象：" + indexOf);
			}
			JSONObject base = JSON.parseObject(text);
			String productName = base.getString("productname");
			productName = productName.replace("\t", "=").trim();
			String applySn = base.getString("apply_sn");
			String date = base.getString("provinceConfirm");
			date = date.replace(" ", "");
			String isOff = base.getString("is_off");
			tBrProductQuery.setProductName(productName);
			tBrProductQuery.setApplySn(applySn);
			tBrProductQuery.setConfirmDate(date);
			if (StringUtils.isNotBlank(productName) && StringUtils.isNotBlank(applySn)
					&& StringUtils.isNotBlank(date)) {
				Long num = queryCount(tBrProductQuery);
				// 是否有
				// 是否注销
				if (num == 1) {
					TBrProduct one = queryOne(tBrProductQuery);
					// 更新注销
					if (isOff.equals("Y")) {
						String isOffTmp = one.getIsOff();
						if (isOffTmp == null || isOffTmp == ProductConstant.PRODUCT_ISOFF_NO) {
							TBrProduct tBrProductUpdate = new TBrProduct();
							tBrProductUpdate.setId(one.getId());
							;
							tBrProductUpdate.setIsOff(ProductConstant.PRODUCT_ISOFF_YES);
							updateByIdSelective(one);
						}
					}
					return null;
				} else if (num > 1) {
					// 可能因为历史原因，数据重复。
					return null;
				}
			}
			log.info(dateStr + " 该产品不重复，需要新增==" + productName);
			// 组织产品基础数据，在抓成分时，一并保存
			tBrProduct.setProductName(productName);
			tBrProduct.setApplyType(ProductConstant.PRODUCT_APPLYTYPE_DOMESTIC);
			tBrProduct.setApplySn(applySn);
			tBrProduct.setConfirmDate(date);

			if (StringUtils.isEmpty(isOff) || isOff.toUpperCase().equals("N")) {
				tBrProduct.setIsOff(ProductConstant.PRODUCT_ISOFF_NO);
			} else {
				tBrProduct.setIsOff(ProductConstant.PRODUCT_ISOFF_YES);
			}
			tBrProduct.setCfdaProcessid(processid);
			tBrProduct.setCfdaNewProcessid(base.getString("newProcessid"));
			tBrProduct.setBevolMid(null);
			tBrProduct.setSource(ProductConstant.PRODUCT_SOURCE_CFDA);
			tBrProduct.setJdUrl("0");
			tBrProduct.setTmallUrl("0");
			// TODO 根据情况会改变类型
			tBrProduct.setIsChina(Constant.YES);
			tBrProduct.setIsSpecial(Constant.NO);
			tBrProduct.setCreateTime(new Date());
			tBrProduct.setDelFlg(Constant.NO);
			String remark = base.getString("remark");
			String remark1 = base.getString("remark1");
			String remark2 = base.getString("remark2");
			remark = remark + " " + remark1 + " " + remark2;
			tBrProduct.setRemark(remark);
			JSONObject unitInfo = base.getJSONObject("scqyUnitinfo");
			tBrProduct.setProducingArea(unitInfo.getString("enterprise_address"));
			// 将产品保存方法重构到成分保存方法中，用于保存备注等信息，也可以保存冗余信息
			tBrProduct.setUpdateBy(6l); // 2019年8月28日 设置，将字段默认设置为6 ,
										// 在索引入solr时将该字段设置为7
			tBrProduct.setCreateBy(0l);
		
			
			String eNameStr = unitInfo.getString("enterprise_name");
			if (StringUtils.isNotBlank(eNameStr)) {
				tBrProduct.setEnterpriseName(eNameStr);
			}
			// 保存运营企业
			TBrEnterprise tBrEnterpriseBus = new TBrEnterprise();
			tBrEnterpriseBus.setApplySn(unitInfo.getString("enterprise_healthpermits"));
			tBrEnterpriseBus.setEnterpriseName(eNameStr);
			tBrEnterpriseBus.setProducingArea(unitInfo.getString("enterprise_address"));
			tBrEnterpriseBus.setRemark(unitInfo.getString("remark"));
			tBrEnterpriseBus.setCreateBy(0l);
			tBrEnterpriseBus.setIsBus(Constant.YES); // 是运营企业
			tBrEnterpriseBus.setIsActive(Constant.STATUS_ACTIVE);
			tBrEnterpriseBus.setDelFlg(Constant.NO);
			tBrEnterpriseBus.setCreateTime(new Date());
			Long enterpriseId = saveBusE(tBrEnterpriseBus);
			tBrProduct.setEnterpriseId(enterpriseId); // 2019年8月7日 新增字段，保存运营企业id
			// 将ItemOrder初始设置为0，以计算成分得分。
			tBrProduct.setItemOrder(Byte.valueOf("0"));
			// 保存产品
			add(tBrProduct);
			Long tBrProductId = tBrProduct.getId();
			pId = tBrProductId;
			// 实际生产企业
			JSONArray realEnterpriseList = base.getJSONArray("sjscqyList");
			if (CollectionUtils.isNotEmpty(realEnterpriseList)) {
				List<TBrEnterprise> tBrEnterpriseList = null;
				TBrEnterprise tBrEnterprise = null;
				JSONObject enterprise = null;
				TBrEnterpriseQuery tBrEnterpriseQuery = null;
				for (int k = 0; k < realEnterpriseList.size(); k++) {
					enterprise = realEnterpriseList.getJSONObject(k);
					String enterpriseName = enterprise.getString("enterprise_name");
					if (StringUtils.isNoneBlank(enterpriseName)) {
						tBrEnterpriseQuery = new TBrEnterpriseQuery();
						tBrEnterpriseQuery.setEnterpriseName(enterpriseName);
						tBrEnterpriseList = tBrEnterpriseService.queryList(tBrEnterpriseQuery);
						// 如果实际生产企业在库中不存在
						if (CollectionUtils.isEmpty(tBrEnterpriseList)) {
							tBrEnterprise = new TBrEnterprise();
							tBrEnterprise.setApplySn(enterprise.getString("enterprise_healthpermits"));
							tBrEnterprise.setEnterpriseName(enterpriseName);
							tBrEnterprise.setProducingArea(enterprise.getString("enterprise_address"));
							tBrEnterprise.setRemark(enterprise.getString("remark"));
							tBrEnterprise.setCreateBy(0l);
							tBrEnterprise.setIsProduct(Constant.YES); // 是生产企业
							tBrEnterprise.setIsActive(Constant.STATUS_ACTIVE);
							tBrEnterprise.setDelFlg(Constant.NO);
							tBrEnterprise.setCreateTime(new Date());
							tBrRegionService.setEnterpriseRegionByAll(tBrEnterprise);
							tBrEnterpriseService.add(tBrEnterprise);
						} else {
							tBrEnterprise = tBrEnterpriseList.get(0);
							TBrEnterprise up = new TBrEnterprise(); 
							up.setId(tBrEnterprise.getId());
							up.setIsProduct(Constant.YES); // 是生产企业
							tBrEnterpriseService.updateByIdSelective(up);
							
						}
						addProductEnterprise(tBrProductId, tBrEnterprise.getId());
					}
				}
			}
			// 处理成分信息
			JSONArray ingredientList = base.getJSONArray("pfList");
			TBrIngredient tBrIngredient = null;
			JSONObject ingredient = null;
			String cname = null;
			TBrIngredientQuery tBrIngredientQuery = null;
			if (CollectionUtils.isNotEmpty(ingredientList)) {
				HashSet<String> nameSet = Sets.newHashSet();
				for (int i = 0; i < ingredientList.size(); i++) {
					ingredient = ingredientList.getJSONObject(i);
					cname = ingredient.getString("cname");
					cname = cname.replace("？", " ");
					cname = cname.trim();
					if (StringUtils.isNoneBlank(cname) && !nameSet.contains(cname)) {
						nameSet.add(cname);
						tBrIngredientQuery = new TBrIngredientQuery();
						tBrIngredientQuery.setName(cname);
						long iNum = tBrIngredientService.queryCount(tBrIngredientQuery);
						if (iNum == 0) {
							// 没有查询到该成分，则说明这是新成分，保存到数据库
							tBrIngredient = new TBrIngredient();
							tBrIngredient.setName(cname);
							tBrIngredient.setSecurityRisks(null);
							tBrIngredient.setActiveIngredient(ProductConstant.ZERO_BYTE);
							tBrIngredient.setBlainRisk(ProductConstant.ZERO_BYTE);
							// 设置该成分来源
							tBrIngredient.setResource(ProductConstant.PRODUCT_SOURCE_CFDA);
							tBrIngredient.setCreateTime(new Date());
							tBrIngredient.setDelFlg(Constant.NO);
							tBrIngredientService.add(tBrIngredient);
						} else if (iNum == 1) {
							tBrIngredient = tBrIngredientService.queryOne(tBrIngredientQuery);
						} else {
							return tBrProduct;
						}
						addProductIngredient(tBrProductId, tBrIngredient.getId());
					}
				}
			}
			// saveLog(ProductConstant.PRODUCT_SOURCE_CFDA,
			// ProductConstant.CFDA_INGREDIENT_URL, ingredientList.size(),
			// "processid=" + processid, null);
		} catch (Exception e) {
			log.info("addIngredientFromCFDA Exception");
			log.info("--: " + text);
			// saveLog(ProductConstant.PRODUCT_SOURCE_CFDA,
			// ProductConstant.CFDA_INGREDIENT_URL, index,
			// "processid=" + processid, e);
			e.printStackTrace();
		}
		return tBrProduct;
	}

	Byte oldImage = 12;

	/**
	 * 保存药监局图片
	 */
	@SuppressWarnings("deprecation")
	private void addImageFromCFDA(TBrProduct tBrProduct) {
		// 这里processId的I为大写
		// String text =
		// HttpUtils.postData(ProductConstant.CFDA_PRODUCT_IMAGE_URL,
		// "processId=" + processid);
		int index = 0;
		if (tBrProduct == null || tBrProduct.getId() == null) {
			return;
		}
		try {
			String text = FileUtils.readFileToString(
					new File(ProductConstant.CFDA_FILE_PATH + dateStr + "/attach_" + processid + ".json"));
			if (StringUtil.isBlank(text)) {
				return;
			}
			int indexOf = text.lastIndexOf("}{");
			if (indexOf > -1) {
				text = text.substring(indexOf + 1);
				log.info("json有问题，截取最后一个对象（attach）：" + indexOf);
			}
			JSONObject base = JSON.parseObject(text);
			JSONArray imageList = base.getJSONArray("result");
			JSONObject image = null;
			String zltype = null;
			if (CollectionUtils.isNotEmpty(imageList)) {
				for (int i = 0; i < imageList.size(); i++) {
					index = i;
					image = imageList.getJSONObject(i);
					if (image == null) {
						continue;
					}
					TBrProductImage tBrProductImage = new TBrProductImage();
					tBrProductImage.setName(image.getString("fileName"));
					tBrProductImage.setCfdaImageId(image.getString("id"));
					Long id = tBrProduct.getId();
					if (id == null) {
						id = pId;
					}
					tBrProductImage.setProductId(id);
					// tBrProductImage.setBevolUrl(null);
					tBrProductImage.setCfdaSsid(base.getString("ssid"));
					tBrProductImage.setSource(oldImage);
					tBrProductImage.setFileType(image.getString("fileType"));
					zltype = image.getString("zltype");
					if (StringUtils.equalsIgnoreCase(zltype, "babacpbzpm")) {
						tBrProductImage.setImageType(ProductConstant.IMAGETYPE_PLANE);
					} else if (StringUtils.equalsIgnoreCase(zltype, "babacpbzlt")) {
						tBrProductImage.setImageType(ProductConstant.IMAGETYPE_CUBE);
					} else if (StringUtils.equalsIgnoreCase(zltype, "babaqtcl")) {
						tBrProductImage.setImageType(ProductConstant.IMAGETYPE_BRAND);
					}
					tBrProductImage.setSource(ProductConstant.PRODUCT_SOURCE_CFDA);
					tBrProductImage.setCreateTime(new Date());
					tBrProductImage.setDelFlg(Constant.NO);
					tBrProductImageService.add(tBrProductImage);
				}
			}
		} catch (Exception e) {
			log.info("addImageFromCFDA exception");
			e.printStackTrace();
		}
	}

	Boolean goon = true;

	// 保存产品 美丽修行
	@Override
	public void addProductFromBEVOL(int page, int category) {
		String params = "p=" + page + "&category=" + category;
		String text = HttpUtils.getData(ProductConstant.BEVOL_PRODUCT_URL + "?" + params);
		int index = 0;
		try {
			TBrProduct tBrProduct = null;
			JSONObject base = JSON.parseObject(text);
			JSONObject data = base.getJSONObject("data");
			JSONArray productList = data.getJSONArray("items");
			JSONObject product = null;
			if (CollectionUtils.isNotEmpty(productList)) {
				for (int i = 0; i < productList.size(); i++) {
					index = i;
					product = productList.getJSONObject(i);
					tBrProduct = new TBrProduct();
					tBrProduct.setProductName(product.getString("title"));
					tBrProduct.setProductAlias(product.getString("alias") + " " + product.getString("alias_2"));
					String applyType = product.getString("data_type");
					if (applyType.equals("1")) {
						// 进口
						tBrProduct.setApplyType(ProductConstant.PRODUCT_APPLYTYPE_IMPORT);
					} else {
						// 国产
						tBrProduct.setApplyType(ProductConstant.PRODUCT_APPLYTYPE_DOMESTIC);
					}
					tBrProduct.setApplySn(product.getString("approval"));
					tBrProduct.setIsOff(ProductConstant.PRODUCT_ISOFF_NO);
					tBrProduct.setBevolMid(product.getString("mid"));
					tBrProduct.setRemark(product.getString("remark") + " " + product.getString("remark3"));
					tBrProduct.setSource(ProductConstant.PRODUCT_SOURCE_BEVOL);
					tBrProduct.setJdUrl("0");
					tBrProduct.setTmallUrl("0");
					tBrProduct.setCreateTime(new Date());
					tBrProduct.setDelFlg(Constant.NO);
					tBrProduct = addIngredientFromBEVOL(tBrProduct);
					if (goon) {
						addImageFromBEVOL(tBrProduct, product.getString("imageSrc"));
					}
				}
			}
			saveLog(ProductConstant.PRODUCT_SOURCE_BEVOL, ProductConstant.BEVOL_PRODUCT_URL, productList.size(), params,
					null);
		} catch (Exception e) {
			e.printStackTrace();
			saveLog(ProductConstant.PRODUCT_SOURCE_BEVOL, ProductConstant.BEVOL_PRODUCT_URL, index, params, e);
		}

	}

	// 保存成分 美丽修行
	private TBrProduct addIngredientFromBEVOL(TBrProduct tBrProduct) {
		String bevolMid = tBrProduct.getBevolMid();
		String html = HttpUtils.getData(ProductConstant.BEVOL_INGREDIENT_URL + bevolMid + ".html");
		Document doc = Jsoup.parse(html);
		Elements approvals = doc.select(".approval_box p");
		int approvalsSize = approvals.size();
		String productName = tBrProduct.getProductName();

		Elements elements;
		int index = 0;
		try {
			TBrProductQuery tBrProductQuery = new TBrProductQuery();
			tBrProductQuery.setProductName(productName);
			List<TBrProduct> oldList = queryList(tBrProductQuery);
			boolean flg = false;
			if (CollectionUtils.isEmpty(oldList)) {
				// 数据表中没有该产品，需要添加
				flg = true;
				// 处理部分产品信息
				String producingArea = processInfo(approvals.eq(1).text());
				String enterpriseName = processInfo(approvals.eq(2).text());
				String enterpriseNameEn = null;
				if (approvalsSize == 5) {
					enterpriseNameEn = processInfo(approvals.eq(3).text());
				}
				String confirmDate = doc.select("#approval_date").text();

				confirmDate = confirmDate.replaceAll(" ", "");
				confirmDate = confirmDate.replaceAll(",", "");
				String cDate = "";
				try {
					cDate = DateFormatUtils.format(Long.parseLong(confirmDate + "000"), "yyyy-MM-dd");
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				tBrProduct.setProducingArea(producingArea);
				tBrProduct.setEnterpriseName(enterpriseName);
				tBrProduct.setEnterpriseNameEn(enterpriseNameEn);
				tBrProduct.setConfirmDate(cDate);
				tBrProduct.setCreateBy(0l);
				add(tBrProduct);
				pId = tBrProduct.getId();
				goon = true;
			} else {
				log.info("***已包含此产品***" + productName);
				tBrProduct = oldList.get(0);
				goon = false;
			}
			// 处理成分信息
			Long tBrProductId = tBrProduct.getId();
			TBrIngredient tBrIngredient = null;
			String cname = null;
			TBrIngredientQuery tBrIngredientQuery = null;
			elements = doc.select(".chengfenbiao .table tr");
			Elements href = null;
			String hrefAttr = null;
			List<TBrIngredient> tBrIngredientList = null;
			if (CollectionUtils.isNotEmpty(elements)) {
				for (int i = 1; i < elements.size(); i++) {
					index = i;
					// 成分名称
					href = elements.get(i).select("td").eq(0).select("a");
					hrefAttr = href.attr("href");
					cname = href.text();
					tBrIngredientQuery = new TBrIngredientQuery();
					tBrIngredientQuery.setName(cname);
					tBrIngredientList = tBrIngredientService.queryList(tBrIngredientQuery);
					if (tBrIngredientList.size() > 0) {
						tBrIngredient = tBrIngredientList.get(0);
					}
					// 没有查询到该成分，则说明这是新成分，保存到数据库
					if (tBrIngredient == null) {
						tBrIngredient = new TBrIngredient();
						tBrIngredient.setName(cname);
						tBrIngredient.setCreateTime(new Date());
						tBrIngredient.setDelFlg(Constant.NO);
						// 分析需要set的成分数据
						analyzeIngredient(elements, tBrIngredient, hrefAttr, i);
						tBrIngredientService.add(tBrIngredient);
						analyzeAim(elements, tBrIngredient, i);
					} else {
						// 该成分为已有成分，但该成分来源为药监局，需要对其成分进行补充
						if (ProductConstant.PRODUCT_SOURCE_CFDA.equals(tBrIngredient.getResource())) {
							analyzeIngredient(elements, tBrIngredient, hrefAttr, i);
							tBrIngredient.setResource(ProductConstant.PRODUCT_SOURCE_BEVOL);
							tBrIngredientService.updateByIdSelective(tBrIngredient);
							analyzeAim(elements, tBrIngredient, i);
						}
					}
					if (flg) {
						// 关联表
						addProductIngredient(tBrProductId, tBrIngredient.getId());
					}
				}
			}
			saveLog(ProductConstant.PRODUCT_SOURCE_BEVOL, ProductConstant.BEVOL_INGREDIENT_URL, elements.size(),
					bevolMid, null);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			saveLog(ProductConstant.PRODUCT_SOURCE_BEVOL, ProductConstant.BEVOL_INGREDIENT_URL, index, bevolMid, e);
		}
		return tBrProduct;
	}

	private void analyzeIngredient(Elements elements, TBrIngredient tBrIngredient, String hrefAttr, int i) {
		Elements ai;
		Elements br;
		// 抓取的成分链接，方便扩展使用
		tBrIngredient.setDataId(hrefAttr);
		// 安全等级
		tBrIngredient.setSecurityRisks(elements.get(i).select("td").eq(1).select("span").text());
		// 活性成分
		ai = elements.get(i).select("td").eq(2).select("img");
		if (CollectionUtils.isEmpty(ai)) {
			tBrIngredient.setActiveIngredient(Constant.NO);
		} else {
			tBrIngredient.setActiveIngredient(Constant.YES);
		}
		// 致痘风险
		br = elements.get(i).select("td").eq(3).select("img");
		if (CollectionUtils.isEmpty(br)) {
			tBrIngredient.setBlainRisk(Constant.NO);
		} else {
			tBrIngredient.setBlainRisk(Constant.YES);
		}
		tBrIngredient.setResource(ProductConstant.PRODUCT_SOURCE_BEVOL);
		// 成分简介
		String descriptionhtml = HttpUtils.getData(ProductConstant.BEVOL_PRODUCT + hrefAttr);
		Document descriptionDoc = Jsoup.parse(descriptionhtml);
		Elements descriptionE = descriptionDoc.select(".cosmetics-info-title.goods-tpl .component-info-box p");
		if (descriptionE != null) {
			tBrIngredient.setDescription(descriptionE.text());
		}
		Elements titleE = descriptionDoc.select(".component-info-title p");
		Elements nameEnE = titleE.eq(0);
		Elements aliasE = titleE.eq(1);
		Elements casNumE = titleE.eq(2);
		if (nameEnE != null) {
			tBrIngredient.setNameEn(nameEnE.text().replace("英文名（INCI）：", ""));
		}
		;
		if (aliasE != null) {
			tBrIngredient.setAlias(aliasE.text().replace("成分别名：", ""));
		}
		;
		if (casNumE != null) {
			tBrIngredient.setCasNum(casNumE.text().replace("CAS号：", ""));
		}
		;

	}

	// 分析目的数据
	private void analyzeAim(Elements elements, TBrIngredient tBrIngredient, int i) {
		// 使用目的 ,保存使用目的
		TBrAim tBrAim = null;
		String aimStr = elements.get(i).select("td").eq(4).text();
		String[] aimArr = aimStr.split(" ");
		String aimOne = null;
		TBrAimQuery tBrAimQuery = null;
		for (int j = 0; j < aimArr.length; j++) {
			aimOne = aimArr[j];
			if (StringUtils.isNotBlank(aimOne)) {
				if (StringUtils.isBlank(aimOne)) {
					continue;
				}
				tBrAimQuery = new TBrAimQuery();
				tBrAimQuery.setName(aimOne);
				// 含有该使用目的，直接关联；不含有，则保存后关联
				tBrAim = tBrAimService.queryOne(tBrAimQuery);
				if (tBrAim == null) {
					tBrAim = new TBrAim();
					tBrAim.setName(aimOne);
					tBrAim.setCreateTime(new Date());
					tBrAim.setDelFlg(Constant.NO);
					tBrAimService.add(tBrAim);
				}
				addIngredientAim(tBrIngredient.getId(), tBrAim.getId());
			}
		}
	}

	// 保存图片 美丽修行
	private void addImageFromBEVOL(TBrProduct tBrProduct, String imageSrc) {
		TBrProductImage tBrProductImage = new TBrProductImage();
		Long id = tBrProduct.getId();
		if (id == null) {
			id = pId;
		}
		tBrProductImage.setProductId(id);
		tBrProductImage.setName(tBrProduct.getProductName());
		if (StringUtils.isNotBlank(imageSrc)) {
			tBrProductImage.setBevolUrl(imageSrc);
			tBrProductImage.setSource(oldImage);
			imageSrc = imageSrc.substring(imageSrc.lastIndexOf("."));
			tBrProductImage.setFileType(imageSrc);
		}
		tBrProductImage.setImageType(ProductConstant.IMAGETYPE_PLANE);
		tBrProductImage.setSource(ProductConstant.PRODUCT_SOURCE_BEVOL);
		tBrProductImage.setCreateTime(new Date());
		tBrProductImage.setDelFlg(Constant.NO);
		tBrProductImageService.add(tBrProductImage);
	}

	// 保存产品及成分关联表信息
	private void addProductIngredient(Long productId, Long ingredientId) {
		if (productId == null) {
			productId = pId;
		}
		TBrProductIngredient tBrProductIngredient = new TBrProductIngredient();
		tBrProductIngredient.setProductId(productId);
		tBrProductIngredient.setIngredientId(ingredientId);
		tBrProductIngredient.setCreateTime(new Date());
		tBrProductIngredient.setDelFlg(Constant.NO);
		tBrProductIngredientService.add(tBrProductIngredient);
	}

	// 保存成分及使用目的关联表信息
	private void addIngredientAim(Long ingredientId, Long aimId) {
		TBrIngredientAim tBrIngredientAim = new TBrIngredientAim();
		tBrIngredientAim.setAimId(aimId);
		tBrIngredientAim.setIngredientId(ingredientId);
		tBrIngredientAim.setCreateTime(new Date());
		tBrIngredientAim.setDelFlg(Constant.NO);
		tBrIngredientAimService.add(tBrIngredientAim);
	}

	// 保存实际生产企业和产品id关联信息
	private void addProductEnterprise(Long productId, Long enterpriseId) {
		if (productId == null) {
			productId = pId;
		}
		TBrProductEnterprise tBrProductEnterprise = new TBrProductEnterprise();
		tBrProductEnterprise.setEnterpriseId(enterpriseId);
		tBrProductEnterprise.setProductId(productId);
		tBrProductEnterprise.setCreateTime(new Date());
		tBrProductEnterprise.setDelFlg(Constant.NO);
		tBrProductEnterpriseService.add(tBrProductEnterprise);
	}

	// 保存日志
	@Override
	public void saveLog(Byte source, String url, Integer count, String params, Exception e) {
		TBrLog tBrLog = new TBrLog();
		tBrLog.setSource(source);
		tBrLog.setUrl(url);
		tBrLog.setParams(params);
		tBrLog.setCount(count);
		if (e != null) {
			tBrLog.setStatus(500);
			String trace = null;
			StackTraceElement[] traces = e.getStackTrace();
			trace = traces[0].toString() + traces[1].toString() + traces[2].toString();
			tBrLog.setTrace(trace);
		} else {
			tBrLog.setStatus(200);
		}
		tBrLog.setCreateTime(new Date());
		tBrLog.setDelFlg(Constant.NO);
		tBrLogService.add(tBrLog);
	}

	private static String processInfo(String approvalInfo) {
		if (StringUtils.isNoneEmpty(approvalInfo)) {
			approvalInfo = approvalInfo.replaceAll("：", ":");
			approvalInfo = approvalInfo.substring(approvalInfo.indexOf(":") + 1);
		}
		return approvalInfo;
	}

	@Override
	public void doSomeThing() {

	}

	private static final String essence = "香精";
	private static final String corrosion = "防腐";
	private static final String clean = "清洁";
	private static final String active = "表面活性";

	@Override
	public TBrProduct getStatisticsInfo(TBrProduct tBrProduct, List<TBrIngredient> ingredientList) {
		TBrProductVo vo = (TBrProductVo) tBrProduct;
		List<String> essenceList = Lists.newArrayList();
		List<String> corrosionList = Lists.newArrayList();
		List<String> riskList = Lists.newArrayList();
		List<String> cleanList = Lists.newArrayList();
		List<String> activeList = Lists.newArrayList();
		ingredientList.forEach(ing -> {
			String ingName = ing.getName();
			// 安全风险
			String securityRisks = ing.getSecurityRisks();
			if (StringUtils.isNoneBlank(securityRisks)) {
				if (securityRisks.indexOf("-") > 0) {
					securityRisks = securityRisks.substring(0, securityRisks.lastIndexOf("-"));
				}
				int safeInt = Integer.parseInt(securityRisks);
				if (safeInt > 6) {
					riskList.add(ingName);
				}
			}
			TBrIngredientVo ingVo = (TBrIngredientVo) ing;
			List<TBrAim> tBrAims = ingVo.getTBrAims();
			if (CollectionUtils.isNotEmpty(tBrAims)) {
				tBrAims.forEach(aim -> {
					String name = aim.getName();
					if (name.contains(essence)) {
						essenceList.add(ingName);
					}
					if (name.contains(corrosion)) {
						corrosionList.add(ingName);
					}
					if (name.contains(clean)) {
						cleanList.add(ingName);
					}
					if (name.contains(active)) {
						activeList.add(ingName);
					}

				});

			}

		});

		vo.setEssenceList(essenceList);
		vo.setCorrosionList(corrosionList);
		vo.setRiskList(riskList);
		vo.setCleanList(cleanList);
		vo.setActiveList(activeList);
		return vo;
	}

	@Override
	public Page<TBrProduct> querySimplePageList(TBrProduct query, Pageable pageable) {
		String sqlId = "simpleSelect";
		String sqlIdCount = "selectCount";
		return tBrProductDao.selectPageList(query, pageable, sqlId, sqlIdCount);
	}

	@Override
	public List<String> queryOperEnterpriseFromProduct() {
		return tBrProductDao.selectOperEnterpriseFromProduct();
	}

	@Override
	public int updateByArea(TBrProductQuery query) {
		return tBrProductDao.updateByArea(query);
	}

	@Deprecated
	@Override
	public TBrProduct showOneProduct4Mobile(Long id) {
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setId(id);
		TBrProductVo one = queryOne(tBrProductQuery);
		// Byte source = 3; //4取天猫的图片 3取京东数据 （2018年2月23日改动 ）
		// TBrProductImageQuery tBrProductImageQuery = new
		// TBrProductImageQuery();
		// tBrProductImageQuery.setProductId(id);
		// tBrProductImageQuery.setSource(source);
		// List<TBrProductImage> list =
		// tBrProductImageService.queryList(tBrProductImageQuery);
		// if(list.size()>0){
		// TBrProductImage image = list.get(0);
		// //String tmallUrl = image.getTmallUrl();
		// //tmallUrl = tmallUrl.replace("60x60", "240x240");
		// String jdUrl = image.getJdUrl();
		// jdUrl = "https://img11.360buyimg.com/n1/"+jdUrl;
		// image.setTmallUrl(jdUrl); //此处的set 方法名称暂时不改动
		// one.setImage(image);
		// }
		return one;
	}

	@Deprecated
	@Override
	public Page<TBrProduct> queryByLabel(Long id, PageReq pageReq) {
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setJoinLabelFlg(true);
		tBrProductQuery.setJoinRecommendFlg(true);
		tBrProductQuery.setLabelId(id);
		tBrProductQuery.setRecommendIsNotNullFlg(true);
		Page<TBrProduct> page = queryPageList(tBrProductQuery, pageReq);
		List<TBrProduct> newContent = Lists.newArrayList();
		for (TBrProduct tBrProduct : page.getContent()) {
			tBrProduct = queryById(tBrProduct.getId());
			newContent.add(tBrProduct);
		}
		return new PageImpl<TBrProduct>(newContent, pageReq, page.getTotalElements());
	}

	@Deprecated
	@Override
	public Page<TBrProduct> queryByLabel(Long id, PageReq pageReq, Long userId) {
		TBrUserPlanLabelQuery tBrUserPlanLabelQuery = new TBrUserPlanLabelQuery();
		tBrUserPlanLabelQuery.setCreateBy(userId);
		List<TBrUserPlanLabel> labelList = tBrUserPlanLabelService.queryList(tBrUserPlanLabelQuery);
		if (CollectionUtils.isEmpty(labelList)) {
			// 该用户虽然登录，但从未做过测试，因而选择默认推荐
			return queryByLabel(id, pageReq);
		}
		// 以下为个性推荐
		List<Long> alist = Lists.newArrayList();
		// 用户标签列表
		List<Long> blist = Lists.newArrayList();

		for (TBrUserPlanLabel tBrUserPlanLabel : labelList) {
			alist.add(tBrUserPlanLabel.getLabelId());
		}
		// 获得该分类下已推荐的商品列表
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setJoinLabelFlg(true);
		tBrProductQuery.setJoinRecommendFlg(true);
		tBrProductQuery.setLabelId(id);
		tBrProductQuery.setRecommendIsNotNullFlg(true);
		Page<TBrProduct> page = queryPageList(tBrProductQuery, pageReq);
		List<TBrProduct> productList = page.getContent();
		List<TBrProduct> clist = Lists.newArrayList();
		// 对商品列表进行过滤
		List<TBrProductLabel> plList = null;
		TBrProductLabelQuery tBrProductLabelQuery = new TBrProductLabelQuery();

		// 因为要删除部分元素 ，因而使用迭代器循环 防止出现 ConcurrentModificationException异常
		Iterator<TBrProduct> it = productList.iterator();
		while (it.hasNext()) {
			TBrProduct tBrProduct = it.next();
			blist.clear();
			// 获得该商品的标签列表
			tBrProductLabelQuery.setProductId(tBrProduct.getId());
			plList = tBrProductLabelService.queryList(tBrProductLabelQuery);
			for (TBrProductLabel tBrProductLabel : plList) {
				blist.add(tBrProductLabel.getLabelId());
			}
			// 用户label和商品label取交集
			Collection<Long> intersection = CollectionUtils.intersection(alist, blist);
			if (intersection.size() == 0) {
				// 没有交集，没有匹配上，移除该商品
				// it.remove();
			} else {
				// 该商品可用则进行图片装饰
				tBrProduct = queryById(tBrProduct.getId());
				clist.add(tBrProduct);
			}
		}
		if (clist.size() == 0) {
			return null;
		}
		// 对商品列表进行分页
		return new PageImpl<TBrProduct>(clist, pageReq, page.getTotalElements());

	}

	@Override
	public BigDecimal getCheapPrice(Long id) {
		TBrProduct one = queryById(id);
		BigDecimal tmallPrice = one.getTmallPrice();
		BigDecimal jdPrice = one.getJdPrice();
		BigDecimal price = null;
		if (tmallPrice != null && jdPrice != null) {
			int compareTo = tmallPrice.compareTo(jdPrice);
			if (compareTo > 0) {
				price = tmallPrice;
			} else {
				price = jdPrice;
			}
		}
		if (tmallPrice != null && jdPrice == null) {
			price = tmallPrice;
		}

		if (tmallPrice == null && jdPrice != null) {
			price = jdPrice;
		}

		return price;
	}

	@Override
	public TBrProduct setLabels(TBrProduct tBrProduct) {
		TBrProductVo vo = (TBrProductVo) tBrProduct;
		List<TBrProductLabel> labels = tBrProductLabelService.getProductLabels(tBrProduct.getId());
		vo.setLabels(labels);
		return vo;
	}

	@Override
	public List<ConfirmVo> queryConfirmData(String startTime, String endTime, Integer type) {
		if (type == null) {
			type = 2;
		}
		return tBrProductDao.selectConfirmData(startTime, endTime, type);
	}

	@Override
	public List<BeVo> queryBeData() {
		return tBrProductDao.selectBeData();
	}

	@Override
	public List<TBrProductVo> queryProductVoListByBrandId(Long id) {
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setBrandId(id);
		tBrProductQuery.setJoinBrandFlg(true);
		return queryList(tBrProductQuery);
	}

	@Override
	public List<TBrProductVo> queryProductVoListByRealEnterpriseId(Long id) {
		TBrProductQuery productQuery = new TBrProductQuery();
		productQuery.setRealEnterpriseId(id);
		productQuery.setJoinRealEnterpriseFlg(true);
		return queryList(productQuery);
	}

	@Override
	public long queryProductNumByBrandId(Long id) {
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setBrandId(id);
		tBrProductQuery.setJoinBrandFlg(true);
		return queryCount(tBrProductQuery);
	}

	@Override
	public List<TBrProductVo> queryProductVoListByIngredientId(Long id) {
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setIngredientId(id);
		tBrProductQuery.setJoinFlg(true);
		return queryList(tBrProductQuery);
	}

	// TODO 主方法在此
	String dateStr = null;
	String processid = null;

	@Override
	public int addProductFromCFDA(File file) {
		// 解析后的数据状态为6,解析完的文件移动到over里面
		TBrProduct tBrProduct = new TBrProduct();
		String fileName = file.getName();
		String filePath = file.getPath();
		processid = fileName.substring(fileName.lastIndexOf("_") + 1, fileName.indexOf(".json"));
		dateStr = filePath.replace("/", "$").replace("\\", "$");
		dateStr = dateStr.substring(dateStr.lastIndexOf("$") - 10, dateStr.lastIndexOf("$"));
		tBrProduct = addIngredientFromCFDA(tBrProduct);
		addImageFromCFDA(tBrProduct);
		// 移动文件
		try {
			FileUtils.moveFileToDirectory(file, new File(ProductConstant.CFDA_OVER_FILE_PATH + dateStr + "/"), true);
		} catch (FileExistsException e1) {
			try {
				log.info("force delete on file1 exit--" + file.getName());
				FileUtils.forceDeleteOnExit(file);

			} catch (IOException e) {
				log.info("force delete1 error");
				e.printStackTrace();
			}
		} catch (IOException e) {
			log.info("remove1 fail");
			e.printStackTrace();
		}

		String attach = null;
		try {
			attach = ProductConstant.CFDA_FILE_PATH + dateStr + "/attach_" + processid + ".json";
			FileUtils.moveFileToDirectory(new File(attach),
					new File(ProductConstant.CFDA_OVER_FILE_PATH + dateStr + "/"), true);
		} catch (FileExistsException e1) {
			try {
				log.info("force delete on file2 exit--" + attach);
				FileUtils.forceDeleteOnExit(new File(attach));
			} catch (IOException e) {
				log.info("force delete2 error");
				e.printStackTrace();
			}
		} catch (IOException e) {
			log.info("remove2 fail");
			e.printStackTrace();
		}

		return 0;
	}

	void addINProductFromCFDA(TBrProduct tBrProduct,File file) {
		// **************start****************
		String text = "";
		try {
			TBrProductQuery tBrProductQuery = new TBrProductQuery();
			text = FileUtils.readFileToString(file);
			// 处理部分产品信息
			int indexOf = text.lastIndexOf("}{");
			if (indexOf > -1) {
				text = text.substring(indexOf + 1);
				log.info("json有问题，截取最后一个对象：" + indexOf);
			}
			JSONObject base = JSON.parseObject(text);
			String productName = base.getString("productname");
			String applySn = base.getString("passno");
			String date = base.getString("passdate");
			date = date.replace(" ", "");
			tBrProductQuery.setProductName(productName);
			tBrProductQuery.setApplySn(applySn);
			tBrProductQuery.setConfirmDate(date);
			if (StringUtils.isNotBlank(productName) && StringUtils.isNotBlank(applySn) && StringUtils.isNotBlank(date)) {
				Long num = queryCount(tBrProductQuery);
				//数据重复
				if (num > 0 ) {
					return ;
				} 
			}
			log.info(dateStr + " 该产品不重复，需要新增==" + productName);
			// 组织产品基础数据，在抓成分时，一并保存
			tBrProduct.setProductName(productName);
			tBrProduct.setApplyType(ProductConstant.PRODUCT_APPLYTYPE_IMPORT);
			tBrProduct.setApplySn(applySn);
			tBrProduct.setConfirmDate(date);
			String productNameEn = base.getString("productnameen");
			tBrProduct.setProductAlias(productNameEn);	
			String enterprise = base.getString("enterprise");
			String enterpriseen = base.getString("enterpriseen");
			tBrProduct.setEnterpriseName(enterprise);
			tBrProduct.setEnterpriseNameEn(enterpriseen);
				 		
			tBrProduct.setCfdaProcessid(processid);
			tBrProduct.setBevolMid(null);
			tBrProduct.setSource(ProductConstant.PRODUCT_SOURCE_CFDA);
			tBrProduct.setJdUrl("0");
			tBrProduct.setTmallUrl("0");
			tBrProduct.setIsChina(Constant.NO);
			tBrProduct.setIsSpecial(Constant.NO);
			tBrProduct.setCreateTime(new Date());
			tBrProduct.setDelFlg(Constant.NO);		 
			// 将产品保存方法重构到成分保存方法中，用于保存备注等信息，也可以保存冗余信息
			tBrProduct.setUpdateBy(6l); // 2019年8月28日 设置，将字段默认设置为6 ,// 在索引入solr时将该字段设置为7
			tBrProduct.setCreateBy(0l);
			
			String enterpriseaddressen = base.getString("enterpriseaddressen");
			//1保存企业
			TBrEnterprise tBrEnterpriseBus = new TBrEnterprise();
			tBrEnterpriseBus.setApplySn("");
			tBrEnterpriseBus.setEnterpriseName(enterprise);
			tBrEnterpriseBus.setEnterpriseNameEn(enterpriseen);
			tBrEnterpriseBus.setProducingAreaEn(enterpriseaddressen);
			tBrEnterpriseBus.setCreateBy(0l);
			tBrEnterpriseBus.setIsBus(Constant.YES); // 是运营企业
			tBrEnterpriseBus.setIsActive(Constant.STATUS_ACTIVE);
			tBrEnterpriseBus.setDelFlg(Constant.NO);
			tBrEnterpriseBus.setCreateTime(new Date());
			Long enterpriseId = saveBusE(tBrEnterpriseBus);
			tBrProduct.setEnterpriseId(enterpriseId); // 2019年8月7日 新增字段，保存运营企业id
			// 将ItemOrder初始设置为0，以计算成分得分。
			tBrProduct.setItemOrder(Byte.valueOf("0"));
			// 2保存产品
			add(tBrProduct);
			pId = tBrProduct.getId();
			// 实际生产企业---无数据
			// 3保存进口企业
			TBrEnterprise tBrEnterpriseInter = new TBrEnterprise();
			String internalunitaddr = base.getString("internalunitaddr");
			String internalunitname = base.getString("internalunitname");
			tBrEnterpriseInter.setIsBus(Constant.YES);  //外国企业在中国的进口代理企业，也算是运营企业
			tBrEnterpriseInter.setEnterpriseName(internalunitname);
			tBrEnterpriseInter.setProducingArea(internalunitaddr);
			BaseDataUtil.setDefaultData(tBrEnterpriseInter);
			Long importEnterpriseId = saveBusE(tBrEnterpriseInter);
			
			
//			4保存进口产品更多信息
			TBrProductMore tBrProductMore = new TBrProductMore();
			String country = base.getString("Country");
			String importCity = base.getString("jksf");
			tBrProductMore.setFromCountry(country);
			tBrProductMore.setImportCity(importCity);
			tBrProductMore.setProductId(pId);
			tBrProductMore.setImportEnterpriseId(importEnterpriseId);
			tBrProductMoreService.add(tBrProductMore);

			// 5处理成分信息
			String ingredientStr = base.getString("cf");
			String[] ingredients = ingredientStr.split("、");
			TBrIngredient tBrIngredient = null;
			String cname = null;
			TBrIngredientQuery tBrIngredientQuery = null;
			if (ingredients.length>0) {
				HashSet<String> nameSet = Sets.newHashSet();
				for (int i = 0; i < ingredients.length; i++) {
					cname = ingredients[i];
					cname = cname.replace("？", " ");
					cname = cname.trim();
					if (StringUtils.isNoneBlank(cname) && !nameSet.contains(cname)) {
						nameSet.add(cname);
						tBrIngredientQuery = new TBrIngredientQuery();
						tBrIngredientQuery.setName(cname);
						long iNum = tBrIngredientService.queryCount(tBrIngredientQuery);
						if (iNum == 0) {
							// 没有查询到该成分，则说明这是新成分，保存到数据库
							tBrIngredient = new TBrIngredient();
							tBrIngredient.setName(cname);
							tBrIngredient.setSecurityRisks(null);
							tBrIngredient.setActiveIngredient(ProductConstant.ZERO_BYTE);
							tBrIngredient.setBlainRisk(ProductConstant.ZERO_BYTE);
							// 设置该成分来源
							tBrIngredient.setResource(ProductConstant.PRODUCT_SOURCE_CFDA);
							tBrIngredient.setCreateTime(new Date());
							tBrIngredient.setDelFlg(Constant.NO);
							tBrIngredientService.add(tBrIngredient);
						} else if (iNum == 1) {
							tBrIngredient = tBrIngredientService.queryOne(tBrIngredientQuery);
						} else {
						}
						addProductIngredient(pId, tBrIngredient.getId());
					}
				}
			}
			
			// 6保存图片链接 preview
			String preview = base.getString("preview");
			preview= preview.replace("\\","");
			preview = preview.replace("href=\"", "href=\"http://cpnp.nmpa.gov.cn/province/webquery/");
			Document document = Jsoup.parse(preview);
			Elements aTags = document.getElementsByTag("a");
			for(int i =0 ; i<aTags.size() ; i++){
				TBrProductImage tBrProductImage = new TBrProductImage();
				tBrProductImage.setProductId(pId);
				Element element = aTags.get(i);
				String href = element.attr("href");
				tBrProductImage.setCfdaImageId(href);
				tBrProductImage.setImageType(ProductConstant.IMAGETYPE_OTHER);
				tBrProductImage.setName("预览");
				if(href.contains("method=jsyq")){
					tBrProductImage.setName("查看技术要求");
					tBrProductImage.setImageType(ProductConstant.IMAGETYPE_TECHNICAL);
				}
				tBrProductImage.setSource(ProductConstant.PRODUCT_SOURCE_CFDA);
				tBrProductImage.setCreateTime(new Date());
				tBrProductImage.setDelFlg(Constant.NO);
				tBrProductImageService.add(tBrProductImage);
			}
		} catch (Exception e) {
			log.info("addINFromCFDA Exception");
			log.info("--: " + text);
			e.printStackTrace();
		}
	}

	@Override
	public int addINProductFromCFDA(File file) {
		// 解析后的数据状态为6,解析完的文件移动到over里面
		TBrProduct tBrProduct = new TBrProduct();
		String fileName = file.getName();
		String filePath = file.getPath();
		processid = fileName.substring(fileName.lastIndexOf("_") + 1, fileName.indexOf(".json"));
		dateStr = filePath.replace("/", "$").replace("\\", "$");
		dateStr = dateStr.substring(dateStr.lastIndexOf("$") - 10, dateStr.lastIndexOf("$"));
		addINProductFromCFDA(tBrProduct,file);
		// **************end******************
		// addImageFromCFDA(tBrProduct);
		// 移动文件
		try {
			FileUtils.moveFileToDirectory(file, new File(ProductConstant.CFDA_OVER_IN_PATH + dateStr + "/"), true);
		} catch (FileExistsException e1) {
			try {
				log.info("force delete on file1 exit--" + file.getName());
				FileUtils.forceDeleteOnExit(file);

			} catch (IOException e) {
				log.info("force delete1 error");
				e.printStackTrace();
			}
		} catch (IOException e) {
			log.info("remove1 fail");
			e.printStackTrace();
		}

		return 0;
	}

	public static void main(String[] args) {
		String filePath = "d:\\home/file\\cfda/2019-10-22\\info_12345.json";
		String dateStr = filePath.replace("/", "$").replace("\\", "$");
		dateStr = dateStr.substring(dateStr.lastIndexOf("$") - 10, dateStr.lastIndexOf("$"));
		System.out.println(dateStr);
		// String filePath = "/home/file/cfda/2019-10-22/info_12345.json";
		// String processid =
		// filePath.substring(filePath.lastIndexOf("cfda/")+5,filePath.lastIndexOf("/"));
		// System.out.println(processid);
		// try {
		// FileUtils.moveFileToDirectory(new File("d:/6.txt"), new
		// File("d:/over/"), true);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	@Override
	public Page<TBrProductVo> queryProductVoPageByBrandId(Long id, PageReq pageReq) {
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setBrandId(id);
		tBrProductQuery.setJoinBrandFlg(true);
		return queryPageList(tBrProductQuery, pageReq);
	}

	@Override
	public Page<TBrProductVo> queryProductVoPageByRealEnterpriseId(Long id, PageReq pageReq) {
		TBrProductQuery productQuery = new TBrProductQuery();
		productQuery.setRealEnterpriseId(id);
		productQuery.setJoinRealEnterpriseFlg(true);
		return queryPageList(productQuery, pageReq);
	}

	@Override
	public void updateStatus(Long id, Long status) {
		TBrProduct up = new TBrProduct();
		up.setId(id);
		up.setUpdateBy(status);
		updateByIdSelective(up);
	}

	@Override
	public long queryProductNumByRealEnterpriseId(Long id) {
		TBrProductQuery productQuery = new TBrProductQuery();
		productQuery.setRealEnterpriseId(id);
		productQuery.setJoinRealEnterpriseFlg(true);
		return queryCount(productQuery);
	}

	// public static void main(String[] args) {
	// HashMap<String, String> params = Maps.newHashMap();
	// params.put("on", "true");
	// //修改此处的页数，确定数据从何处开始补传
	// String page ="501";
	// params.put("page", page); //67133
	// params.put("pageSize", "15");
	// String text = HttpUtils.postData(ProductConstant.CFDA_PRODUCT_URL,
	// params);
	// JSONObject base = JSON.parseObject(text);
	// JSONArray productList = base.getJSONArray("list");
	// JSONObject product = null;
	// if(CollectionUtils.isNotEmpty(productList)){
	// for (int i=0; i<productList.size();i++) {
	// product = productList.getJSONObject(i);
	// String date = product.getString("provinceConfirm");
	// String productName = product.getString("productName");
	// System.out.println(" 在第 "+page+" 有 "+productName+" 备案确认时间是 "+date);
	// }
	// }
	// }

	// public static void main(String[] args) {
	// String html =
	// HttpUtils.getData("https://www.bevol.cn/product/2021305f5363fcc98c0516565db26d4f.html");
	// Document doc=Jsoup.parse(html);
	//
	// Elements approvals=doc.select(".approval_box p");
	// int approvalsSize = approvals.size();
	//
	//
	// Elements elements;
	// int index = 0;
	// //处理部分产品信息
	// String producingArea = processInfo(approvals.eq(1).text());
	// String enterpriseName = processInfo(approvals.eq(2).text());
	// String enterpriseNameEn = null;
	// if(approvalsSize == 5){
	// enterpriseNameEn = processInfo(approvals.eq(3).text());
	// }
	// String confirmDate = doc.select("#approval_date").text();
	//
	// confirmDate = confirmDate.replaceAll(" " , "");
	// confirmDate = confirmDate.replaceAll(",", "");
	// String cDate = "";
	// Elements elements=doc.select(".chengfenbiao .table tr");
	// for (int i = 1; i < elements.size(); i++) {
	// //成分名称
	// System.out.println(elements.get(i).select("td").eq(0).select("a").text());
	// //安全等级
	// System.out.println(elements.get(i).select("td").eq(1).select("span").text());
	// //活性成分
	// Elements imgs1 = elements.get(i).select("td").eq(2).select("img");
	// System.out.println(imgs1.size()>0 ? 1:0);
	// //致痘风险
	// Elements imgs2 = elements.get(i).select("td").eq(3).select("img");
	// System.out.println(imgs2.size()>0 ? 1:0);
	// //使用目的
	// String aimStr = elements.get(i).select("td").eq(4).text();
	// String[] aimArr = aimStr.split(" ");
	// for (int j = 0; j < aimArr.length; j++) {
	// System.out.println(i+"="+aimArr[j]);
	// }
	// }
	// }

}
