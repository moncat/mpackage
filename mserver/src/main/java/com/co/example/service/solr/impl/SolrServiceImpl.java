package com.co.example.service.solr.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.StringUtil;
import com.co.example.entity.brand.TBrBrand;
import com.co.example.entity.brand.aide.TBrBrandQuery;
import com.co.example.entity.label.TBrLabel;
import com.co.example.entity.label.aide.TBrLabelQuery;
import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.TBrLog;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrEnterpriseQuery;
import com.co.example.entity.product.aide.TBrIngredientQuery;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.entity.product.aide.TBrProductSolr;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.label.TBrLabelService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrIngredientService;
import com.co.example.service.product.TBrLogService;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.solr.SolrService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SolrServiceImpl implements SolrService {

	@Autowired
	private SolrClient solrClient;
	@Autowired
	private TBrProductService tBrProductService;
	
	@Inject
	TBrBrandService tBrBrandService;
	
	@Inject
	TBrLabelService tBrLabelService;	

	@Inject
	TBrEnterpriseService tBrEnterpriseService;	

	@Inject
	TBrIngredientService tBrIngredientService;

	@Inject
	TBrLogService tBrLogService;

	@Override
	public Boolean syncProducts(List<TBrProductSolr> tBrProductSolrList) {
		try {
			solrClient.addBeans(tBrProductSolrList);
			solrClient.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	@Override
	public Map<String, Object> querySolr(TBrProductQuery query, int start, int rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<TBrProductSolr> list = new ArrayList<TBrProductSolr>();
		long count = 0l;
		try {
			// 第二种方式
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.setQuery("*:*");
			solrQuery.set("q.op", "and");

			// solrQuery.addFilterQuery("id:" +
			// "(2325490857967616,2325490930434048,2325490884100096)");

			String normal = query.getNormal();
			String normalKey = "productName";
			Integer normalType = query.getNormalType();
			if (normalType != null) {
				if (normalType == 1) {
					normalKey = "productName";
				} else if (normalType == 2) {
					normalKey = "ingredients";
				} else if (normalType == 3) {
					normalKey = "enterpriseName";
				} else if (normalType == 4) {
					normalKey = "brands";
				}
			}
			if (StringUtils.isNotBlank(normal)) {
				normal = normal.trim();
				
				
//				String[] split = normal.split(" ");
//				List<String> tmp = Lists.newArrayList();
//				for (int i = 0; i < split.length; i++) {
//					String item = "\"" + split[i] + "\"";
//					tmp.add(item);
//				}
//				String tmpStr = String.join("|", tmp);
				
				String tmpStr = normal;
				if (StringUtil.isChinese(tmpStr)) {
					solrQuery.addFilterQuery(normalKey + ":" + tmpStr + "");
				} else {
					solrQuery.addFilterQuery(normalKey + ":*" + tmpStr + "*");
				}
			}
			
			
			
			// solrQuery.addFilterQuery("id:" +
			// "(2325490857967616,2325490930434048,2325490884100096)");

			// 企业 生产企业 品牌 成分 标签
			String startTime = query.getStartTime();
			String endTime = query.getEndTime();
			if (StringUtils.isNotBlank(startTime) || StringUtils.isNotBlank(endTime)) {
				String timeFilter = "confirmDate:[*1 TO *2] ";
				if (StringUtils.isNotBlank(startTime)) {
					timeFilter = timeFilter.replace("*1", startTime);
				}
				if (StringUtils.isNotBlank(endTime)) {
					timeFilter = timeFilter.replace("*2", endTime);
				}
				timeFilter = timeFilter.replace("*1", "*");
				timeFilter = timeFilter.replace("*2", "*");
				solrQuery.addFilterQuery(timeFilter);
			}
			String eIds = query.getEIds();
			if (StringUtils.isNotBlank(eIds)) {
				eIds = eIds.replace(",", " OR ");
				solrQuery.addFilterQuery("beid:(" + eIds + ")");
			}

			String peIds = query.getPeIds();
			if (StringUtils.isNotBlank(peIds)) {
				peIds = peIds.replace(",", " OR ");
				solrQuery.addFilterQuery("peids:(" + peIds + ")");
			}
			
//			String peIds = query.getPeIds();
//			String peIdsNew = "";
//			if (StringUtils.isNotBlank(peIds)) {
//				String[] split = peIds.split(",");
//				for (int i = 0; i < split.length; i++) {
//					String tmp = "*" + split[i] + "* ";
//					peIdsNew += tmp;
//				}
//				solrQuery.addFilterQuery("peids:(" + peIdsNew + ")");
//			}

//			String bIds = query.getBIds();
//			String bIdsNew = "";
//			if (StringUtils.isNotBlank(bIds)) {
//				String[] split = bIds.split(",");
//				for (int i = 0; i < split.length; i++) {
//					String tmp = "*" + split[i] + "* ";
//					bIdsNew += tmp;
//				}
//				solrQuery.addFilterQuery("bids:(" + bIdsNew + ")");
//			}

			String bIds = query.getBIds();
			if (StringUtils.isNotBlank(bIds)) {
				bIds = bIds.replace(",", " OR ");
				solrQuery.addFilterQuery("bids:(" + bIds + ")");
			}
			
			
			String iIds = query.getIIds();
			String iIdsNew = "";
			if (StringUtils.isNotBlank(iIds)) {
				String[] split = iIds.split(",");
				for (int i = 0; i < split.length; i++) {
					String tmp = "*" + split[i] + "* ";
					iIdsNew += tmp;
				}
				solrQuery.addFilterQuery("iids:(" + iIdsNew + ")");
			}

//			String lIds = query.getLIds();
//			String lIdsNew = "";
//			if (StringUtils.isNotBlank(lIds)) {
//				String[] split = lIds.split(",");
//				for (int i = 0; i < split.length; i++) {
//					String tmp = "*" + split[i] + "* ";
//					lIdsNew += tmp;
//				}
//				solrQuery.addFilterQuery("lids:(" + lIdsNew + ")");
//			}

			String lIds = query.getLIds();
			if (StringUtils.isNotBlank(lIds)) {
				lIds = lIds.replace(",", " OR ");
				solrQuery.addFilterQuery("lids:(" + lIds + ")");
			}
			// beid:(2320609604927488-2320609655685120-2320609668743168)
			// peids:(2320609604927488 2320609655685120)
			// bids:(2330490594508800 2330491738832896)
			// iids:(*2320609608187904* *2320609618968576*)
			// lids:(*24* *13*)

			// 设置查询的开始
			if (start > 0) {
				solrQuery.setStart(start);
			}
			// 设置查询的条数
			solrQuery.setRows(rows);
			solrQuery.set("df", normalKey);
			solrQuery.set("fl",	"id,productName,enterpriseName,applySn,confirmDate,peName,applyType,confirmStatus,brands");
			// solrQuery.setSort("confirmDate", SolrQuery.ORDER.desc);
			solrQuery.addSort("confirmDate", SolrQuery.ORDER.desc);
			solrQuery.addSort("id", SolrQuery.ORDER.desc);
			System.out.println(solrQuery);
			QueryResponse response = solrClient.query(solrQuery);
			SolrDocumentList documentList = response.getResults();
			count = documentList.getNumFound();
			for (SolrDocument solrDocument : documentList) {
				// System.out.println("solrDocument==============" +
				// solrDocument);
				try {
					TBrProductSolr tBrProductSolr = new TBrProductSolr();
					Long idTmp = Long.parseLong(solrDocument.getFieldValue("id").toString());
					tBrProductSolr.setProductName(checkNull(solrDocument.getFieldValue("productName")));
					tBrProductSolr.setEnterpriseName(checkNull(solrDocument.getFieldValue("enterpriseName")));
					tBrProductSolr.setApplySn(checkNull(solrDocument.getFieldValue("applySn")));
					tBrProductSolr.setConfirmDate(checkNull(solrDocument.getFieldValue("confirmDate")));
					tBrProductSolr.setId(idTmp);
					tBrProductSolr.setBrands(checkNull(solrDocument.getFieldValue("brands")));
					tBrProductSolr.setPeName(checkNull(solrDocument.getFieldValue("peName")));
					tBrProductSolr.setConfirmStatus(checkNull(solrDocument.getFieldValue("confirmStatus")));
					tBrProductSolr.setApplyType(checkNull(solrDocument.getFieldValue("applyType")));
					list.add(tBrProductSolr);
				} catch (Exception e) {
					continue;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		map.put("list", list);
		map.put("count", count);
		return map;
	}

	String checkNull(Object obj) {
		if (obj != null) {
			return obj.toString();
		}
		return null;
	}

	/**
	 * 需要补充更多字段，而且添加高级查询，该方法废弃
	 * 
	 * @param query
	 * @param start
	 * @param rows
	 * @return
	 */
	@Deprecated
	@Override
	public Map<String, Object> queryProductSolr(TBrProductQuery query, int start, int rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<TBrProductSolr> list = new ArrayList<TBrProductSolr>();
		long count = 0l;
		try {
			// 第二种方式
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.setQuery("*:*");
			solrQuery.set("q.op", "and");

			// solrQuery.addFilterQuery("id:" +
			// "(2325490857967616,2325490930434048,2325490884100096)");

			String productName = query.getProductNameLike();
			if (StringUtils.isNotBlank(productName)) {
				if (StringUtil.isChinese(productName)) {
					solrQuery.addFilterQuery("productName:" + productName.trim());
				} else {
					solrQuery.addFilterQuery("productName:*" + productName.trim() + "*");
				}
			}
			String enterpriseName = query.getEnterpriseName();
			if (StringUtils.isNotBlank(enterpriseName)) {
				if (StringUtil.isChinese(enterpriseName)) {
					solrQuery.addFilterQuery("enterpriseName:" + enterpriseName.trim());
				} else {
					solrQuery.addFilterQuery("enterpriseName:*" + enterpriseName.trim() + "*");
				}
			}
			String applySn = query.getApplySn();
			if (StringUtils.isNotBlank(applySn)) {
				if (StringUtil.isChinese(applySn)) {
					solrQuery.addFilterQuery("applySn:" + applySn.trim());
				} else {
					solrQuery.addFilterQuery("applySn:*" + applySn.trim() + "*");
				}
			}
			String labelNames = query.getLabelNames();
			if (StringUtils.isNotBlank(labelNames)) {
				String[] labelNameArr = labelNames.split(",");
				for (String labelName : labelNameArr) {
					if (StringUtil.isChinese(labelNames)) {
						solrQuery.addFilterQuery("labelNames:" + labelName.trim());
					} else {
						solrQuery.addFilterQuery("labelNames:*" + labelName.trim() + "*");
					}
				}
			}
			// 设置查询的开始
			if (start > 0) {
				solrQuery.setStart(start);
			}
			// 设置查询的条数
			solrQuery.setRows(rows);
			solrQuery.set("df", "productName");
			solrQuery.set("fl", "id,productName,enterpriseName,applySn");
			// solrQuery.setSort("confirmDate", SolrQuery.ORDER.desc);
			solrQuery.setSort("id", SolrQuery.ORDER.desc);

			System.out.println(solrQuery);
			QueryResponse response = solrClient.query(solrQuery);

			SolrDocumentList documentList = response.getResults();
			count = documentList.getNumFound();
			for (SolrDocument solrDocument : documentList) {
				System.out.println("solrDocument==============" + solrDocument);
				TBrProductSolr tBrProductSolr = new TBrProductSolr();
				String productNameTmp = solrDocument.getFieldValue("productName").toString();
				String confirmDateTmp = solrDocument.getFieldValue("confirmDate").toString();
				String enterpriseNameTmp = solrDocument.getFieldValue("enterpriseName").toString();
				Object fieldValue = solrDocument.getFieldValue("applySn") == null ? ""
						: solrDocument.getFieldValue("applySn");
				String applySnTmp = fieldValue.toString();
				Long idTmp = Long.parseLong(solrDocument.getFieldValue("id").toString());
				tBrProductSolr.setProductName(productNameTmp);
				tBrProductSolr.setEnterpriseName(enterpriseNameTmp);
				tBrProductSolr.setApplySn(applySnTmp);
				tBrProductSolr.setConfirmDate(confirmDateTmp);
				tBrProductSolr.setId(idTmp);
				list.add(tBrProductSolr);
			}
			// return documentList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		map.put("list", list);
		map.put("count", count);
		return map;
	}

	@Override
	public Boolean deleteProducts() {
		try {
			solrClient.deleteByQuery("*:*");
			solrClient.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Boolean deleteProductById(Long id) {
		try {
			solrClient.deleteById(id + "");
			solrClient.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public int updateByIdSelective(String id, String fieldName, Object fieldValue) {
		try {
			HashMap<String, Object> oper = new HashMap<String, Object>();
			oper.put("set", fieldValue);
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", id);
			doc.addField(fieldName, oper);
			UpdateResponse rsp = solrClient.add(doc);
			log.info("updateByIdSelective doc id:" + id  +" fieldName:"+fieldName+" fieldValue:"+fieldValue + " result:" + rsp.getStatus() + " Qtime:" + rsp.getQTime());
			UpdateResponse rspCommit = solrClient.commit();
			log.info("commit updateByIdSelective doc to index" + " result:" + rspCommit.getStatus() + " Qtime:"
					+ rspCommit.getQTime());
			return 0;
		} catch (SolrServerException e) {
			log.info("updateByIdSelective SolrServerException");
			e.printStackTrace();
		} catch (IOException e) {
			log.info("updateByIdSelective IOException");
			e.printStackTrace();
		} catch (Exception e) {
			log.info("updateByIdSelective Exception");
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void updateInBatch(String id, Map<String, String> maps) {
		try {
			Set<String> keys = maps.keySet();
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", id);
			for (String key : keys) {
				HashMap<String, Object> oper = new HashMap<String, Object>();
				oper.put("set", maps.get(key));
				doc.addField(key, oper);
			}
			UpdateResponse rsp = solrClient.add(doc);
			log.info("updateInBatch doc id:" + id + " result:" + rsp.getStatus() + " Qtime:" + rsp.getQTime());
			UpdateResponse rspCommit = solrClient.commit();
			log.info("commit updateInBatch doc to index" + " result:" + rspCommit.getStatus() + " Qtime:"+ rspCommit.getQTime());
		} catch (SolrServerException e) {
			log.info("updateByIdSelective SolrServerException");
			e.printStackTrace();
		} catch (IOException e) {
			log.info("updateByIdSelective IOException");
			e.printStackTrace();
		} catch (Exception e) {
			log.info("updateByIdSelective Exception");
			e.printStackTrace();
		}
	}


	@Override
	public Map<String, Object> querySolr2(TBrProductQuery query, int start, int rows) {

		Map<String, Object> map = new HashMap<String, Object>();
		List<TBrProductSolr> list = new ArrayList<TBrProductSolr>();
		long count = 0l;
		try {
			// 第二种方式
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.setQuery("*:*");
			solrQuery.set("q.op", "and");

			String normal = query.getNormal();
			String normalKey = "productName";
			Integer normalType = query.getNormalType();
			if (normalType != null) {
				if (normalType == 1) {
					normalKey = "productName";
				} else if (normalType == 2) {
					normalKey = "ingredients";
				} else if (normalType == 3) {
					normalKey = "enterpriseName";
				} else if (normalType == 4) {
					normalKey = "brands";
				}
			}
			if (StringUtils.isNotBlank(normal)) {
				normal = normal.trim();
				normal = normal.replace("  ", " ");
				normal = normal.replace("  ", " ");
				normal = normal.replace(" ", ",");
				normal = normal.replace("|", ",");
				String[] split = normal.split(",");
				List<String> tmp = Lists.newArrayList();
				String item = null;
				for (int i = 0; i < split.length; i++) {
					if (normalType == 1){
						item = "*" + split[i] + "*";
					}else{
						item = split[i];
					}
					tmp.add(item);
				}
				String join = StringUtils.join(tmp, " OR ");
				solrQuery.addFilterQuery(normalKey + ":(" + join + ")");
			}

			// 企业 生产企业 品牌 成分 标签
			String startTime = query.getStartTime();
			String endTime = query.getEndTime();
			if (StringUtils.isNotBlank(startTime) || StringUtils.isNotBlank(endTime)) {
				String timeFilter = "confirmDate:[*1 TO *2] ";
				if (StringUtils.isNotBlank(startTime)) {
					timeFilter = timeFilter.replace("*1", startTime);
				}
				if (StringUtils.isNotBlank(endTime)) {
					timeFilter = timeFilter.replace("*2", endTime);
				}
				timeFilter = timeFilter.replace("*1", "*");
				timeFilter = timeFilter.replace("*2", "*");
				solrQuery.addFilterQuery(timeFilter);
			}
			String eIds = query.getEIds();
			if (StringUtils.isNotBlank(eIds)) {
				eIds = eIds.replace(",", " OR ");
				solrQuery.addFilterQuery("beid:(" + eIds + ")");
			}

			String peIds = query.getPeIds();
			if (StringUtils.isNotBlank(peIds)) {
				peIds = peIds.replace(",", " OR ");
				solrQuery.addFilterQuery("peids:(" + peIds + ")");
			}
			
			String bIds = query.getBIds();
			if (StringUtils.isNotBlank(bIds)) {
				bIds = bIds.replace(",", " OR ");
				solrQuery.addFilterQuery("bids:(" + bIds + ")");
			}
			
			
			String iIds = query.getIIds();
			List<String> iIdsNew = Lists.newArrayList();
			if (StringUtils.isNotBlank(iIds)) {
				String[] split = iIds.split(",");
				for (int i = 0; i < split.length; i++) {
					iIdsNew.add("*" + split[i] + "*");
				}
				String join = StringUtils.join(iIdsNew, " OR ");
				solrQuery.addFilterQuery("iids:(" + join + ")");
			}

			
			String lIds = query.getLIds();
			List<String> lIdsNew = Lists.newArrayList();
			if (StringUtils.isNotBlank(lIds)) {
				String[] split = lIds.split(",");
				for (int i = 0; i < split.length; i++) {
					lIdsNew.add("*" + split[i] + "*");
				}
				String join = StringUtils.join(lIdsNew, " OR ");
				solrQuery.addFilterQuery("lids:(" + join + ")");
			}
		 
			
			// 设置查询的开始
			if (start > 0) {
				solrQuery.setStart(start);
			}
			// 设置查询的条数
			solrQuery.setRows(rows);
			solrQuery.set("df", normalKey);
			solrQuery.set("fl",	"id,productName,enterpriseName,applySn,confirmDate,peName,applyType,confirmStatus,brands");
			solrQuery.addSort("confirmDate", SolrQuery.ORDER.desc);
			solrQuery.addSort("id", SolrQuery.ORDER.desc);
			System.out.println(solrQuery);
			QueryResponse response = solrClient.query(solrQuery);
			SolrDocumentList documentList = response.getResults();
			count = documentList.getNumFound();
			for (SolrDocument solrDocument : documentList) {
				// System.out.println("solrDocument==============" +solrDocument);
				try {
					TBrProductSolr tBrProductSolr = new TBrProductSolr();
					Long idTmp = Long.parseLong(solrDocument.getFieldValue("id").toString());
					tBrProductSolr.setProductName(checkNull(solrDocument.getFieldValue("productName")));
					tBrProductSolr.setEnterpriseName(checkNull(solrDocument.getFieldValue("enterpriseName")));
					tBrProductSolr.setApplySn(checkNull(solrDocument.getFieldValue("applySn")));
					tBrProductSolr.setConfirmDate(checkNull(solrDocument.getFieldValue("confirmDate")));
					tBrProductSolr.setId(idTmp);
					tBrProductSolr.setBrands(checkNull(solrDocument.getFieldValue("brands")));
					tBrProductSolr.setPeName(checkNull(solrDocument.getFieldValue("peName")));
					tBrProductSolr.setConfirmStatus(checkNull(solrDocument.getFieldValue("confirmStatus")));
					tBrProductSolr.setApplyType(checkNull(solrDocument.getFieldValue("applyType")));
					list.add(tBrProductSolr);
				} catch (Exception e) {
					continue;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		map.put("list", list);
		map.put("count", count);
		return map;
	
	}


	@Override
	public HashSet<String>  queryByIds(HashSet<String> ids) {
		// 第二种方式
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("*:*");
		solrQuery.set("q.op", "and");
		solrQuery.set("fq","id:" + "("+String.join(" OR ", ids)+")");
		// 设置查询的条数
		solrQuery.setRows(50);
		solrQuery.set("fl",	"id");
		QueryResponse response;
		try {
			response = solrClient.query(solrQuery);
			SolrDocumentList documentList = response.getResults();
			long count = documentList.getNumFound();
			if(ids.size()==count){
				return null;
			}else{
				for (SolrDocument solrDocument : documentList) {
					Long lon = Long.parseLong(solrDocument.getFieldValue("id").toString());
					tBrProductService.updateStatus(lon, 10l);  //已经索引了，更新为10
					ids.remove(lon);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ids;
	
	}


	@Override
	public List<Long> querySolr3(TBrProductQuery query, int start, int rows) {
		List<Long> list = new ArrayList<Long>();
		try {
			// 第二种方式
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.setQuery("*:*");
			solrQuery.set("q.op", "and");
			// 设置查询的条数
			solrQuery.setStart(start);
			solrQuery.setRows(rows);
			solrQuery.set("fl",	"id");
			QueryResponse response = solrClient.query(solrQuery);
			SolrDocumentList documentList = response.getResults();
			for (SolrDocument solrDocument : documentList) {
				try {
					Long idTmp = Long.parseLong(solrDocument.getFieldValue("id").toString());
					list.add(idTmp);
				} catch (Exception e) {
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public TBrProductSolr queryById(Long id) {
		try {
			// 第二种方式
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.setQuery("*:*");
			solrQuery.set("q.op", "and");
			solrQuery.set("fl",	"id,productName,enterpriseName,applySn,confirmDate,peName,applyType,confirmStatus,brands");
			solrQuery.addFilterQuery("id:" + id);
			QueryResponse response = solrClient.query(solrQuery);
			SolrDocumentList documentList = response.getResults();
			for (SolrDocument solrDocument : documentList) {
				try {
					TBrProductSolr tBrProductSolr = new TBrProductSolr();
					Long idTmp = Long.parseLong(solrDocument.getFieldValue("id").toString());
					tBrProductSolr.setProductName(checkNull(solrDocument.getFieldValue("productName")));
					tBrProductSolr.setEnterpriseName(checkNull(solrDocument.getFieldValue("enterpriseName")));
					tBrProductSolr.setApplySn(checkNull(solrDocument.getFieldValue("applySn")));
					tBrProductSolr.setConfirmDate(checkNull(solrDocument.getFieldValue("confirmDate")));
					tBrProductSolr.setId(idTmp);
					tBrProductSolr.setBrands(checkNull(solrDocument.getFieldValue("brands")));
					tBrProductSolr.setPeName(checkNull(solrDocument.getFieldValue("peName")));
					tBrProductSolr.setConfirmStatus(checkNull(solrDocument.getFieldValue("confirmStatus")));
					tBrProductSolr.setApplyType(checkNull(solrDocument.getFieldValue("applyType")));
					return tBrProductSolr;
					
				} catch (Exception e) {
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 *  1、已成功
	 *  2、 检测已存在
	 *  3、计算solr对象错误
	 *  4、同步一条数据错误
	 *  
	 */
	@Override
	public Integer syncOne(TBrProduct tBrProduct,Boolean check,Long status) {
		if(check){	
			TBrProductSolr one = queryById(tBrProduct.getId());
			if(one!=null){
				return 2;
			}
		}		
		TBrProductSolr tBrProductSolr = getSolrBean(tBrProduct);
		if(tBrProductSolr == null){
			return 3;
		}
		Boolean syncOne = syncOne(tBrProductSolr);
		if(!syncOne){
			return 4;
		}
		tBrProductService.updateStatus(tBrProduct.getId(), status);
		return 1;
	}

	@Override
	public Boolean syncOne(TBrProductSolr tBrProductSolr) {
		try {
			solrClient.addBean(tBrProductSolr);
			solrClient.commit();
		} catch (Exception e) {
			tBrLogService.add(new TBrLog("syncOne",e));
			e.printStackTrace();
			return false;
		}
		return true;
	}


	@Override
	public TBrProductSolr getSolrBean(TBrProduct tBrProduct) {
		try {

			TBrProductSolr tBrProductSolr = new TBrProductSolr();
			Long id = tBrProduct.getId();
			tBrProductSolr.setId(id);
			tBrProductSolr.setEnterpriseName(tBrProduct.getEnterpriseName());
			tBrProductSolr.setProductName(tBrProduct.getProductName());
			tBrProductSolr.setConfirmDate(tBrProduct.getConfirmDate());
			tBrProductSolr.setApplySn(tBrProduct.getApplySn());
			tBrProductSolr.setBeid(tBrProduct.getEnterpriseId() + ""); // 企业ID
			tBrProductSolr.setConfirmStatus(tBrProduct.getMoreData2());
			tBrProductSolr.setApplyType(tBrProduct.getApplyType());
			// 生产企业ids
			TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
			tBrEnterpriseQuery.setIsProduct(Constant.YES);
			tBrEnterpriseQuery.setJoinFlg(true);
			tBrEnterpriseQuery.setProductId(id);
			List<TBrEnterprise> tBrEnterpriseList = tBrEnterpriseService.queryList(tBrEnterpriseQuery);
			List<String> enterpriseIdsArr = Lists.newArrayList();
			List<String> enterpriseNamesArr = Lists.newArrayList();
			for (TBrEnterprise tBrEnterprise : tBrEnterpriseList) {
				enterpriseIdsArr.add(tBrEnterprise.getId() + "");
				enterpriseNamesArr.add(tBrEnterprise.getEnterpriseName());
			}
			tBrProductSolr.setPeids(String.join(",", enterpriseIdsArr));
			tBrProductSolr.setPeName(String.join(",", enterpriseNamesArr));

			// 成分id 成分名称
			TBrIngredientQuery tBrIngredientQuery = new TBrIngredientQuery();
			tBrIngredientQuery.setJoinFlg(true);
			tBrIngredientQuery.setProductId(id);
			List<TBrIngredient> tBrIngredientList = tBrIngredientService.queryList(tBrIngredientQuery);
			List<String> iNameArr = Lists.newArrayList();
			List<String> iIdsArr = Lists.newArrayList();
			for (TBrIngredient tBrIngredient : tBrIngredientList) {
				iNameArr.add(tBrIngredient.getName());
				iIdsArr.add(tBrIngredient.getId() + "");
			}
			tBrProductSolr.setIngredients(String.join(",", iNameArr));
			tBrProductSolr.setIids(String.join(",", iIdsArr));

			// 品牌
			TBrBrandQuery tBrBrandQuery = new TBrBrandQuery();
			tBrBrandQuery.setJoinFlg(true);
			tBrBrandQuery.setProductId(id);
			List<TBrBrand> brandList = tBrBrandService.queryList(tBrBrandQuery);
			List<String> bNameArr = Lists.newArrayList();
			List<String> bIdsArr = Lists.newArrayList();
			for (TBrBrand tBrBrand : brandList) {
				bNameArr.add(tBrBrand.getName());
				bIdsArr.add(tBrBrand.getId() + "");
			}
			tBrProductSolr.setBrands(String.join(",", bNameArr));
			tBrProductSolr.setBids(String.join(",", bIdsArr));

			// 标签
			TBrLabelQuery tBrLabelQuery = new TBrLabelQuery();
			tBrLabelQuery.setProductJoinFlg(true);
			tBrLabelQuery.setProductId(id);
			List<TBrLabel> labelList = tBrLabelService.queryList(tBrLabelQuery);
			List<String> lIdsArr = Lists.newArrayList();
			for (TBrLabel tBrLabel : labelList) {
				lIdsArr.add(tBrLabel.getId() + "");
			}
			tBrProductSolr.setLids(String.join(",", lIdsArr));
			return tBrProductSolr;
		} catch (Exception e) {
			tBrLogService.add(new TBrLog("getSolrBean",e));
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public Long queryCount() {
		Long count = 0l;
		try {
			// 第二种方式
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.setQuery("*:*");
			solrQuery.set("q.op", "and");
			solrQuery.set("fl",	"id");
			solrQuery.setStart(1);
			solrQuery.setRows(1);
			count =  solrClient.query(solrQuery).getResults().getNumFound();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}



}
