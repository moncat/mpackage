package com.co.example.service.product.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.eclipse.jetty.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.co.example.common.constant.Constant;
import com.co.example.common.utils.HttpUtils;
import com.co.example.dao.product.TBrProductDao;
import com.co.example.entity.product.TBrAim;
import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.TBrIngredientAim;
import com.co.example.entity.product.TBrLog;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.TBrProductEnterprise;
import com.co.example.entity.product.TBrProductImage;
import com.co.example.entity.product.TBrProductIngredient;
import com.co.example.entity.product.aide.ProductConstant;
import com.co.example.entity.product.aide.TBrAimQuery;
import com.co.example.entity.product.aide.TBrEnterpriseQuery;
import com.co.example.entity.product.aide.TBrIngredientQuery;
import com.co.example.entity.product.aide.TBrIngredientVo;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.entity.product.aide.TBrProductVo;
import com.co.example.service.product.TBrAimService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrIngredientAimService;
import com.co.example.service.product.TBrIngredientService;
import com.co.example.service.product.TBrLogService;
import com.co.example.service.product.TBrProductEnterpriseService;
import com.co.example.service.product.TBrProductImageService;
import com.co.example.service.product.TBrProductIngredientService;
import com.co.example.service.product.TBrProductService;
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

    @Override
    protected BaseDao<TBrProduct, Long> getBaseDao() {
        return tBrProductDao;
    }
    
	private Long pId = null;
    
	@Override
	public int addProductFromCFDA(String page,String dateStr)  {
		//记录json数据
		HashMap<String, String> params = Maps.newHashMap();
		params.put("on", "true");
		params.put("page", page); //67133
		params.put("pageSize", "15");
		String text = HttpUtils.postData(ProductConstant.CFDA_PRODUCT_URL, params);
		if(StringUtils.isBlank(text)){
			saveLog(ProductConstant.PRODUCT_SOURCE_CFDA, ProductConstant.CFDA_PRODUCT_URL, -1, "该页无数据"+page,null);
			log.info("***该页无数据*** "+page);
			return 2;
		}
		Integer index = 0;
		try {
			TBrProduct tBrProduct = null;
			JSONObject  base =  JSON.parseObject(text);
			JSONArray productList =  base.getJSONArray("list");
			JSONObject product = null;
			
			if(CollectionUtils.isNotEmpty(productList)){
				for (int i=0; i<productList.size();i++) {
					index =i;
					product = productList.getJSONObject(i);
					String date = product.getString("provinceConfirm");
					if(StringUtils.isBlank(dateStr)){
						//不设置时间，全量更新
					}else{
						//设置时间，增量更新
						if(!date.equals(dateStr)){
							//增量截止
							saveLog(ProductConstant.PRODUCT_SOURCE_CFDA, ProductConstant.CFDA_PRODUCT_URL, index, HttpUtils.getRequestData(params).toString(),null);
							return 1;
						}
					}
					
					String productName = product.getString("productName");
					
					TBrProductQuery tBrProductQuery = new TBrProductQuery();
					tBrProductQuery.setProductName(productName);
					List<TBrProduct> oldList = queryList(tBrProductQuery);
					//名称已经重复，则进入下一次循环（重复原因：1网上数据错误  2增量补传时网站又更新了数据，导致起始页面不正确）
					if(CollectionUtils.isNotEmpty(oldList)){
						log.info("***已经有*** "+productName);
						continue;
					}
					tBrProduct = new TBrProduct();
					tBrProduct.setProductName(productName);
					tBrProduct.setProductAlias(null);
					tBrProduct.setApplyType(ProductConstant.PRODUCT_APPLYTYPE_DOMESTIC);
					tBrProduct.setApplySn(product.getString("applySn"));
					tBrProduct.setEnterpriseName(product.getString("enterpriseName"));
					tBrProduct.setEnterpriseNameEn(null);
					tBrProduct.setProducingArea(null);
					tBrProduct.setConfirmDate(date);
					String isOff = product.getString("is_off");
					if(StringUtils.isEmpty(isOff) || isOff.toUpperCase().equals("N")){
						tBrProduct.setIsOff(ProductConstant.PRODUCT_ISOFF_NO);
					}else{
						tBrProduct.setIsOff(ProductConstant.PRODUCT_ISOFF_YES);
					}
					String processid = product.getString("processid");
					tBrProduct.setCfdaProcessid(processid);
					tBrProduct.setCfdaNewProcessid(product.getString("newProcessid"));
					tBrProduct.setBevolMid(null);
					tBrProduct.setSource(ProductConstant.PRODUCT_SOURCE_CFDA);
					tBrProduct.setCreateTime(new Date());
					tBrProduct.setDelFlg(Constant.NO);
					tBrProduct = addIngredientFromCFDA(tBrProduct,processid);
					addImageFromCFDA(tBrProduct, processid);	
				}
			}
			saveLog(ProductConstant.PRODUCT_SOURCE_CFDA, ProductConstant.CFDA_PRODUCT_URL, productList.size(), HttpUtils.getRequestData(params).toString(),null);
		} catch (Exception e) {
			log.info("error",e.fillInStackTrace());
			//e.printStackTrace();
			saveLog(ProductConstant.PRODUCT_SOURCE_CFDA, ProductConstant.CFDA_PRODUCT_URL, index,HttpUtils.getRequestData(params).toString(),e);
		}
		return 0;
	}
	
	
	//保存成分   药监局
	private TBrProduct addIngredientFromCFDA(TBrProduct tBrProduct ,String processid){
		String text = HttpUtils.postData(ProductConstant.CFDA_INGREDIENT_URL, "processid="+processid);
		int index =0;
		try {
			//处理部分产品信息
			JSONObject  base =  JSON.parseObject(text);
			String remark = base.getString("remark");
			String remark1 = base.getString("remark1");
			String remark2 = base.getString("remark2");
			remark = remark +" "+ remark1 +" "+ remark2;
			tBrProduct.setRemark(remark);
			JSONObject unitInfo = base.getJSONObject("scqyUnitinfo");
			tBrProduct.setProducingArea(unitInfo.getString("enterprise_address"));
			//将产品保存方法重构到成分保存方法中，用于保存备注等信息，也可以保存冗余信息
			tBrProduct.setCreateBy(0l);
			//天大的坑 
			String eNameStr = unitInfo.getString("enterprise_name");
			if(StringUtils.isNotBlank(eNameStr)){
				tBrProduct.setEnterpriseName(eNameStr);
			}
			add(tBrProduct);
			Long tBrProductId = tBrProduct.getId();
			pId = tBrProductId;
			//实际生产企业
			JSONArray realEnterpriseList = base.getJSONArray("sjscqyList");
			if(CollectionUtils.isNotEmpty(realEnterpriseList)){
				List<TBrEnterprise> tBrEnterpriseList =null;
				TBrEnterprise tBrEnterprise =null;
				JSONObject enterprise = null;
				TBrEnterpriseQuery tBrEnterpriseQuery = null;
				for (int k = 0; k < realEnterpriseList.size(); k++) {
					enterprise=realEnterpriseList.getJSONObject(k);
					String enterpriseName = enterprise.getString("enterprise_name");
					if(StringUtils.isNoneBlank(enterpriseName)){
						tBrEnterpriseQuery = new TBrEnterpriseQuery();
						tBrEnterpriseQuery.setEnterpriseName(enterpriseName);
						tBrEnterpriseList = tBrEnterpriseService.queryList(tBrEnterpriseQuery);
						//如果实际生产企业在库中不存在
						if(CollectionUtils.isEmpty(tBrEnterpriseList)){
							tBrEnterprise= new TBrEnterprise();
							tBrEnterprise.setApplySn(enterprise.getString("enterprise_healthpermits"));
							tBrEnterprise.setEnterpriseName(enterpriseName);
							tBrEnterprise.setProducingArea(enterprise.getString("enterprise_address"));
							tBrEnterprise.setRemark(enterprise.getString("remark"));
							tBrEnterprise.setCreateBy(0l);
							tBrEnterprise.setIsActive(Constant.STATUS_ACTIVE);
							tBrEnterprise.setDelFlg(Constant.YES);
							tBrEnterprise.setCreateTime(new Date());
							tBrEnterpriseService.add(tBrEnterprise);
						}else{
							tBrEnterprise = tBrEnterpriseList.get(0);
						}
						addProductEnterprise(tBrProductId,tBrEnterprise.getId());
					}
				}
			}
			//处理成分信息
			JSONArray ingredientList = base.getJSONArray("pfList");
			TBrIngredient tBrIngredient = null;
			JSONObject ingredient = null;
			String cname = null;
			TBrIngredientQuery tBrIngredientQuery = null;
			if(CollectionUtils.isNotEmpty(ingredientList)){
				for (int i = 0; i < ingredientList.size(); i++) {
					index = i;
					ingredient = ingredientList.getJSONObject(i);
					cname = ingredient.getString("cname");
					cname = cname.replace("？", " ");
					cname = cname.trim();
					if(StringUtils.isNoneBlank(cname)){
						tBrIngredientQuery = new TBrIngredientQuery();
						tBrIngredientQuery.setName(cname);
						tBrIngredient = tBrIngredientService.queryOne(tBrIngredientQuery);
						//没有查询到该成分，则说明这是新成分，保存到数据库
						if(tBrIngredient == null){
							tBrIngredient = new TBrIngredient();
							tBrIngredient.setName(cname);
							tBrIngredient.setSecurityRisks(null);
							tBrIngredient.setActiveIngredient(ProductConstant.ZERO_BYTE);
							tBrIngredient.setBlainRisk(ProductConstant.ZERO_BYTE);
							//设置该成分来源
							tBrIngredient.setResource(ProductConstant.PRODUCT_SOURCE_CFDA);
							tBrIngredient.setCreateTime(new Date());
							tBrIngredient.setDelFlg(Constant.NO);
							tBrIngredientService.add(tBrIngredient);
						}
						addProductIngredient(tBrProductId,tBrIngredient.getId());
					}
				}
			}
			saveLog(ProductConstant.PRODUCT_SOURCE_CFDA, ProductConstant.CFDA_INGREDIENT_URL, ingredientList.size(),"processid="+processid,null);
		} catch (Exception e) {
			saveLog(ProductConstant.PRODUCT_SOURCE_CFDA, ProductConstant.CFDA_INGREDIENT_URL, index, "processid="+processid,e);
			e.printStackTrace();
		}
		return tBrProduct;
	}
	
	Byte oldImage =12;
	
	
	/**
	 * 保存药监局图片
	 */
	private void addImageFromCFDA(TBrProduct tBrProduct ,String processid){
		//这里processId的I为大写
		String text = HttpUtils.postData(ProductConstant.CFDA_PRODUCT_IMAGE_URL, "processId="+processid);
		if(StringUtil.isBlank(text)){
			return;
		}
		int index = 0;
		try {
			JSONObject  base =  JSON.parseObject(text);
			JSONArray imageList = base.getJSONArray("result");
			JSONObject image = null;
			String zltype = null;
			if(CollectionUtils.isNotEmpty(imageList)){
				for (int i = 0; i < imageList.size(); i++) {
					index = i;
					image = imageList.getJSONObject(i);
					TBrProductImage tBrProductImage = new TBrProductImage();
					tBrProductImage.setName(image.getString("fileName"));
					tBrProductImage.setCfdaImageId(image.getString("id"));
					Long id = tBrProduct.getId();
					if(id == null){
						id=pId;
					}
					tBrProductImage.setProductId(id);
					//tBrProductImage.setBevolUrl(null);
					tBrProductImage.setCfdaSsid(base.getString("ssid"));
					tBrProductImage.setSource(oldImage);
					tBrProductImage.setFileType(image.getString("fileType"));
					zltype = image.getString("zltype");
					if(StringUtils.equalsIgnoreCase(zltype, "babacpbzpm")){
						tBrProductImage.setImageType(ProductConstant.IMAGETYPE_PLANE);
					}else if(StringUtils.equalsIgnoreCase(zltype, "babacpbzlt")){
						tBrProductImage.setImageType(ProductConstant.IMAGETYPE_CUBE);
					}else if(StringUtils.equalsIgnoreCase(zltype, "babaqtcl")){
						tBrProductImage.setImageType(ProductConstant.IMAGETYPE_BRAND);
					}
					tBrProductImage.setSource(ProductConstant.PRODUCT_SOURCE_CFDA);
					tBrProductImage.setCreateTime(new Date());
					tBrProductImage.setDelFlg(Constant.NO);
					tBrProductImageService.add(tBrProductImage);
				}
			}
			saveLog(ProductConstant.PRODUCT_SOURCE_CFDA, ProductConstant.CFDA_PRODUCT_IMAGE_URL, imageList.size(),  "processId="+processid,null);
		} catch (Exception e) {
			saveLog(ProductConstant.PRODUCT_SOURCE_CFDA, ProductConstant.CFDA_PRODUCT_IMAGE_URL, index,  "processId="+processid,e);
			e.printStackTrace();
		}
	}
	
	Boolean goon = true;
	//保存产品   美丽修行
	@Override
	public void addProductFromBEVOL(int page , int category) {
		String params="p="+page+"&category="+category;
		String text = HttpUtils.getData(ProductConstant.BEVOL_PRODUCT_URL+"?"+params);
		int index = 0;
		try {
			TBrProduct tBrProduct = null;
			JSONObject  base =  JSON.parseObject(text);
			JSONObject  data =  base.getJSONObject("data");
			JSONArray productList =  data.getJSONArray("items");
			JSONObject product = null;
			if(CollectionUtils.isNotEmpty(productList)){
				for (int i=0; i<productList.size();i++) {
					index =i;
					product = productList.getJSONObject(i);
					tBrProduct = new TBrProduct();
					tBrProduct.setProductName(product.getString("title"));
					tBrProduct.setProductAlias(product.getString("alias")+" "+product.getString("alias_2"));
					String applyType = product.getString("data_type");
					if(applyType.equals("1")){
						//进口
						tBrProduct.setApplyType(ProductConstant.PRODUCT_APPLYTYPE_IMPORT);
					}else{
						//国产
						tBrProduct.setApplyType(ProductConstant.PRODUCT_APPLYTYPE_DOMESTIC);
					}
					tBrProduct.setApplySn(product.getString("approval"));
					tBrProduct.setIsOff(ProductConstant.PRODUCT_ISOFF_NO);
					tBrProduct.setBevolMid(product.getString("mid"));
					tBrProduct.setRemark(product.getString("remark")+" "+product.getString("remark3"));
					tBrProduct.setSource(ProductConstant.PRODUCT_SOURCE_BEVOL);
					tBrProduct.setCreateTime(new Date());
					tBrProduct.setDelFlg(Constant.NO);
					tBrProduct = addIngredientFromBEVOL(tBrProduct);
					if(goon){
						addImageFromBEVOL(tBrProduct,product.getString("imageSrc"));
					}
				}
			}
			saveLog(ProductConstant.PRODUCT_SOURCE_BEVOL, ProductConstant.BEVOL_PRODUCT_URL, productList.size(), params,null);
		} catch (Exception e) {
			e.printStackTrace();
			saveLog(ProductConstant.PRODUCT_SOURCE_BEVOL, ProductConstant.BEVOL_PRODUCT_URL, index,params,e);
		}
		
	}
	
	//保存成分   美丽修行
	private TBrProduct addIngredientFromBEVOL(TBrProduct tBrProduct ){
		String bevolMid = tBrProduct.getBevolMid();
		String html = HttpUtils.getData(ProductConstant.BEVOL_INGREDIENT_URL+bevolMid+".html");
		Document doc=Jsoup.parse(html);
		Elements approvals=doc.select(".approval_box p");
		int approvalsSize = approvals.size();
		String productName = tBrProduct.getProductName();
		
		Elements elements;
		int index = 0;
		try {
			TBrProductQuery tBrProductQuery = new TBrProductQuery();
			tBrProductQuery.setProductName(productName);
			List<TBrProduct> oldList = queryList(tBrProductQuery);
			boolean flg = false;
			if(CollectionUtils.isEmpty(oldList)){
				//数据表中没有该产品，需要添加
				flg = true;
				//处理部分产品信息
				String producingArea =	processInfo(approvals.eq(1).text());
				String enterpriseName =	processInfo(approvals.eq(2).text());
				String enterpriseNameEn = null;
				if(approvalsSize == 5){
					 enterpriseNameEn = processInfo(approvals.eq(3).text());
				}
				String confirmDate = doc.select("#approval_date").text();
				
				confirmDate = confirmDate.replaceAll(" " , "");
				confirmDate = confirmDate.replaceAll(",", "");
				String cDate = "";
				try {
					cDate = DateFormatUtils.format(Long.parseLong(confirmDate+"000"), "yyyy-MM-dd");
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
			}else{
				log.info("***已包含此产品***"+productName);
				tBrProduct = oldList.get(0);
				goon = false;
			}
			//处理成分信息
			Long tBrProductId = tBrProduct.getId();
			TBrIngredient tBrIngredient = null;
			String cname = null;
			TBrIngredientQuery tBrIngredientQuery = null;
			elements = doc.select(".chengfenbiao .table tr");
			Elements href = null;
			String hrefAttr = null;
			List<TBrIngredient> tBrIngredientList = null;
			if(CollectionUtils.isNotEmpty(elements)){
				for (int i = 1; i < elements.size(); i++) {
					index = i;
					//成分名称
					href = elements.get(i).select("td").eq(0).select("a");
					hrefAttr = href.attr("href");
					cname = href.text();
					tBrIngredientQuery = new TBrIngredientQuery();
					tBrIngredientQuery.setName(cname);
					tBrIngredientList = tBrIngredientService.queryList(tBrIngredientQuery);
					if(tBrIngredientList.size()>0){
						tBrIngredient = tBrIngredientList.get(0);
					}
					//没有查询到该成分，则说明这是新成分，保存到数据库
					if(tBrIngredient == null){
						tBrIngredient = new TBrIngredient();
						tBrIngredient.setName(cname);
						tBrIngredient.setCreateTime(new Date());
						tBrIngredient.setDelFlg(Constant.NO);
						//分析需要set的成分数据
						analyzeIngredient(elements, tBrIngredient, hrefAttr, i);
						tBrIngredientService.add(tBrIngredient);
						analyzeAim(elements, tBrIngredient, i);
					}else{
						//该成分为已有成分，但该成分来源为药监局，需要对其成分进行补充
						if(ProductConstant.PRODUCT_SOURCE_CFDA.equals(tBrIngredient.getResource())){
							analyzeIngredient(elements, tBrIngredient, hrefAttr, i);
							tBrIngredient.setResource(ProductConstant.PRODUCT_SOURCE_BEVOL);
							tBrIngredientService.updateByIdSelective(tBrIngredient);
							analyzeAim(elements, tBrIngredient, i);
						}
					}
					if(flg){
						//关联表  
						addProductIngredient(tBrProductId,tBrIngredient.getId());
					}
				}
			}
			saveLog(ProductConstant.PRODUCT_SOURCE_BEVOL, ProductConstant.BEVOL_INGREDIENT_URL, elements.size(), bevolMid,null);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			saveLog(ProductConstant.PRODUCT_SOURCE_BEVOL, ProductConstant.BEVOL_INGREDIENT_URL, index, bevolMid,e);
		}
		return tBrProduct;
	}

	private void analyzeIngredient(Elements elements, TBrIngredient tBrIngredient, String hrefAttr, int i) {
		Elements ai;
		Elements br;
		//抓取的成分链接，方便扩展使用
		tBrIngredient.setDataId(hrefAttr);
		//安全等级
		tBrIngredient.setSecurityRisks(elements.get(i).select("td").eq(1).select("span").text());
		//活性成分
		ai = elements.get(i).select("td").eq(2).select("img");
		if(CollectionUtils.isEmpty(ai)){
			tBrIngredient.setActiveIngredient(Constant.NO);
		}else{
			tBrIngredient.setActiveIngredient(Constant.YES);						
		}
		//致痘风险
		br = elements.get(i).select("td").eq(3).select("img");
		if(CollectionUtils.isEmpty(br)){
			tBrIngredient.setBlainRisk(Constant.NO);
		}else{
			tBrIngredient.setBlainRisk(Constant.YES);						
		}
		tBrIngredient.setResource(ProductConstant.PRODUCT_SOURCE_BEVOL);
		//成分简介
		String descriptionhtml = HttpUtils.getData(ProductConstant.BEVOL_PRODUCT+hrefAttr);
		Document descriptionDoc=Jsoup.parse(descriptionhtml);
		Elements descriptionE=descriptionDoc.select(".cosmetics-info-title.goods-tpl .component-info-box p");
		if(descriptionE != null){
			tBrIngredient.setDescription(descriptionE.text());
		}
		Elements titleE=descriptionDoc.select(".component-info-title p");
		Elements nameEnE = titleE.eq(0);
		Elements aliasE = titleE.eq(1);
		Elements casNumE = titleE.eq(2);
		if(nameEnE != null){
			tBrIngredient.setNameEn(nameEnE.text().replace("英文名（INCI）：", ""));
		};
		if(aliasE != null){
			tBrIngredient.setAlias(aliasE.text().replace("成分别名：", ""));
		};
		if(casNumE != null){
			tBrIngredient.setCasNum(casNumE.text().replace("CAS号：", ""));
		};
		
		
	}

	//分析目的数据
	private void analyzeAim(Elements elements, TBrIngredient tBrIngredient, int i) {
		//使用目的 ,保存使用目的
		TBrAim tBrAim = null;
		String aimStr = elements.get(i).select("td").eq(4).text();
		String[] aimArr = aimStr.split(" ");
		String aimOne = null;
		TBrAimQuery tBrAimQuery = null;
		for (int j = 0; j < aimArr.length; j++) {
			aimOne = aimArr[j];
			if(StringUtils.isNotBlank(aimOne)){
				if(StringUtils.isBlank(aimOne)){
					continue;
				}
				tBrAimQuery = new TBrAimQuery();
				tBrAimQuery.setName(aimOne);
				//含有该使用目的，直接关联；不含有，则保存后关联
				tBrAim = tBrAimService.queryOne(tBrAimQuery);
				if(tBrAim == null){
					tBrAim = new TBrAim();
					tBrAim.setName(aimOne);
					tBrAim.setCreateTime(new Date());
					tBrAim.setDelFlg(Constant.NO);
					tBrAimService.add(tBrAim);
				}
				addIngredientAim(tBrIngredient.getId(),tBrAim.getId());
			}
		}
	}
	
	//保存图片   美丽修行
	private void addImageFromBEVOL(TBrProduct tBrProduct,String imageSrc){
		TBrProductImage tBrProductImage = new TBrProductImage();
		Long id = tBrProduct.getId();
		if(id == null){
			id=pId;
		}
		tBrProductImage.setProductId(id);
		tBrProductImage.setName(tBrProduct.getProductName());
		if(StringUtils.isNotBlank(imageSrc)){
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
	
	//保存产品及成分关联表信息
	private void addProductIngredient(Long productId,Long ingredientId){
		if(productId == null){
			productId=pId;
		}
		TBrProductIngredient tBrProductIngredient = new TBrProductIngredient();
		tBrProductIngredient.setProductId(productId);
		tBrProductIngredient.setIngredientId(ingredientId);
		tBrProductIngredient.setCreateTime(new Date());
		tBrProductIngredient.setDelFlg(Constant.NO);
		tBrProductIngredientService.add(tBrProductIngredient);
	}
	//保存成分及使用目的关联表信息
	private void addIngredientAim(Long ingredientId,Long aimId){
		TBrIngredientAim tBrIngredientAim = new TBrIngredientAim();
		tBrIngredientAim.setAimId(aimId);
		tBrIngredientAim.setIngredientId(ingredientId);
		tBrIngredientAim.setCreateTime(new Date());
		tBrIngredientAim.setDelFlg(Constant.NO);
		tBrIngredientAimService.add(tBrIngredientAim);
	}
	
	//保存实际生产企业和产品id关联信息
	private void addProductEnterprise(Long productId,Long enterpriseId){
		if(productId == null){
			productId=pId;
		}
		TBrProductEnterprise tBrProductEnterprise = new TBrProductEnterprise();
		tBrProductEnterprise.setEnterpriseId(enterpriseId);
		tBrProductEnterprise.setProductId(productId);
		tBrProductEnterprise.setCreateTime(new Date());
		tBrProductEnterprise.setDelFlg(Constant.NO);
		tBrProductEnterpriseService.add(tBrProductEnterprise);
	}
	
	//保存日志
	@Override
	public void saveLog(Byte source,String url,Integer count,String params ,Exception e ){
		TBrLog tBrLog = new TBrLog();
		tBrLog.setSource(source);
		tBrLog.setUrl(url);
		tBrLog.setParams(params);
		tBrLog.setCount(count);
		if(e!=null){
			tBrLog.setStatus(500);
			String trace = null;
			StackTraceElement[] traces = e.getStackTrace();
				trace=traces[0].toString()+traces[1].toString()+traces[2].toString();
			tBrLog.setTrace(trace);			
		}else{
			tBrLog.setStatus(200);
		}
		tBrLog.setCreateTime(new Date());
		tBrLog.setDelFlg(Constant.NO);
		tBrLogService.add(tBrLog);
	}
	
	private static String processInfo(String approvalInfo) {
		if(StringUtils.isNoneEmpty(approvalInfo)){
			approvalInfo = approvalInfo.replaceAll("：", ":");
			approvalInfo = approvalInfo.substring(approvalInfo.indexOf(":")+1);
		}
		return approvalInfo;
	}
	

	@Override
	public void doSomeThing() {	
		
	}
	
	
	public static void main(String[] args) {
	}

	private static final String essence = "香精";
	private static final String corrosion = "防腐";
	private static final String clean = "清洁";
	private static final String active = "表面活性";
	

	@Override
	public TBrProduct getStatisticsInfo(TBrProduct tBrProduct, List<TBrIngredient> ingredientList) {
		TBrProductVo vo = (TBrProductVo)tBrProduct;
		List<String> essenceList = Lists.newArrayList();
		List<String> corrosionList = Lists.newArrayList();
		List<String> riskList = Lists.newArrayList();
		List<String> cleanList = Lists.newArrayList();
		List<String> activeList = Lists.newArrayList();
		ingredientList.forEach(ing ->{
			String ingName = ing.getName();
			//安全风险
			String securityRisks = ing.getSecurityRisks();
			if(StringUtils.isNoneBlank(securityRisks)){
				if(securityRisks.indexOf("-")>0){
					securityRisks = securityRisks.substring(0,securityRisks.lastIndexOf("-"));
				}
				int safeInt = Integer.parseInt(securityRisks);
				if(safeInt > 6){
					riskList.add(ingName);
				}
			}
			TBrIngredientVo ingVo = (TBrIngredientVo)ing;
			List<TBrAim> tBrAims = ingVo.getTBrAims();
			if(CollectionUtils.isNotEmpty(tBrAims)){
				tBrAims.forEach(aim ->{
					String name = aim.getName();
					if(name.contains(essence)){
						essenceList.add(ingName);
					}
					if(name.contains(corrosion)){
						corrosionList.add(ingName);
					}
					if(name.contains(clean)){
						cleanList.add(ingName);
					}
					if(name.contains(active)){
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
		String sqlIdCount ="selectCount";
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
	
//	public static void main(String[] args) {
//		HashMap<String, String> params = Maps.newHashMap();
//		params.put("on", "true");
//		//修改此处的页数，确定数据从何处开始补传
//		String page ="501";
//		params.put("page", page); //67133
//		params.put("pageSize", "15");
//		String text = HttpUtils.postData(ProductConstant.CFDA_PRODUCT_URL, params);
//		JSONObject  base =  JSON.parseObject(text);
//		JSONArray productList =  base.getJSONArray("list");
//		JSONObject product = null;
//		if(CollectionUtils.isNotEmpty(productList)){
//			for (int i=0; i<productList.size();i++) {
//				product = productList.getJSONObject(i);
//				String date = product.getString("provinceConfirm");
//				String productName = product.getString("productName");
//				System.out.println(" 在第 "+page+" 有 "+productName+" 备案确认时间是 "+date);
//			}
//		}
//	}
	

	
	
	
	
	
//	public static void main(String[] args) {
//		String html = HttpUtils.getData("https://www.bevol.cn/product/2021305f5363fcc98c0516565db26d4f.html");
//		Document doc=Jsoup.parse(html);
//		
//		Elements approvals=doc.select(".approval_box p");
//		int approvalsSize = approvals.size();
//		
//		
//		Elements elements;
//		int index = 0;
//			//处理部分产品信息
//			String producingArea =	processInfo(approvals.eq(1).text());
//			String enterpriseName =	processInfo(approvals.eq(2).text());
//			String enterpriseNameEn = null;
//			if(approvalsSize == 5){
//				 enterpriseNameEn = processInfo(approvals.eq(3).text());
//			}
//			String confirmDate = doc.select("#approval_date").text();
//			
//			confirmDate = confirmDate.replaceAll(" " , "");
//			confirmDate = confirmDate.replaceAll(",", "");
//			String cDate = "";
//		Elements elements=doc.select(".chengfenbiao .table tr");
//		for (int i = 1; i < elements.size(); i++) {
//			//成分名称
//			System.out.println(elements.get(i).select("td").eq(0).select("a").text());
//			//安全等级
//			System.out.println(elements.get(i).select("td").eq(1).select("span").text());
//			//活性成分
//			Elements imgs1 = elements.get(i).select("td").eq(2).select("img");
//			System.out.println(imgs1.size()>0 ? 1:0);
//			//致痘风险
//			Elements imgs2 = elements.get(i).select("td").eq(3).select("img");
//			System.out.println(imgs2.size()>0 ? 1:0);
//			//使用目的
//			String aimStr = elements.get(i).select("td").eq(4).text();
//			String[] aimArr = aimStr.split(" ");
//			for (int j = 0; j < aimArr.length; j++) {
//				System.out.println(i+"="+aimArr[j]);
//			}
//		}
		
//	}

}
	
	
	
	
	
	
	
	
	
	
