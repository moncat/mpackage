package com.co.example.service.solr.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.example.common.utils.StringUtil;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.entity.product.aide.TBrProductSolr;
import com.co.example.service.solr.SolrService;

@Service
public class SolrServiceImpl implements SolrService {

	@Autowired
	private SolrClient solrClient;

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
			
//			solrQuery.addFilterQuery("id:" + "(2325490857967616,2325490930434048,2325490884100096)");
		 
			String normal = query.getNormal();
			String  normalKey ="productName";
			Integer normalType = query.getNormalType();
			if(normalType !=null){
				if(normalType == 1){
					normalKey ="productName";
				}else if(normalType == 2){
					normalKey ="ingredients";
				}else if(normalType == 3){
					normalKey ="enterpriseName";
				}else if(normalType == 4){
					normalKey ="brands";
				}				
			}			
			if (StringUtils.isNotBlank(normal)) {
				if (StringUtil.isChinese(normal)) {
					solrQuery.addFilterQuery(normalKey+":" + normal.trim());
				} else {
					solrQuery.addFilterQuery(normalKey+":*" + normal.trim() + "*");
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
//			solrQuery.addFilterQuery("id:" + "(2325490857967616,2325490930434048,2325490884100096)");
		
			//企业   生产企业   品牌    成分  标签
			String startTime =  query.getStartTime();
			String endTime =  query.getEndTime();
			if(StringUtils.isNotBlank(startTime) || StringUtils.isNotBlank(endTime)){
				String timeFilter = "confirmDate:[*1 TO *2] ";
				if(StringUtils.isNotBlank(startTime)){
					timeFilter.replace("*1", startTime);
				}
				if(StringUtils.isNotBlank(endTime)){
					timeFilter.replace("*2", endTime);
				}
				timeFilter.replace("*1", "*");
				timeFilter.replace("*2", "*");
				solrQuery.addFilterQuery(timeFilter);
			}
			
		
			
			Long lId = query.getLId();
			if(lId !=null){
				solrQuery.addFilterQuery("lids:*"+lId+"*");
			}
			
			Long iId = query.getIId();
			if(iId !=null){
				solrQuery.addFilterQuery("iids:*"+iId+"*");
			}
			
			Long bId = query.getBId();
			if(bId !=null){
				solrQuery.addFilterQuery("bids:*"+bId+"*");
			}
			
			String eIds = query.getEIds();
			if(StringUtils.isNotBlank(eIds)){
				eIds = eIds.replace(",", " ");
				solrQuery.addFilterQuery("beid:("+eIds+")");
			}
			
			String peIds = query.getPeIds();
			if(StringUtils.isNotBlank(peIds)){
				peIds = peIds.replace(",", " ");
				solrQuery.addFilterQuery("peids:("+peIds+")");
			}
		 
			
			// 设置查询的开始
			if (start > 0) {
				solrQuery.setStart(start);
			}
			// 设置查询的条数
			solrQuery.setRows(rows);
			solrQuery.set("df", "productName");
			solrQuery.set("fl", "id,productName,enterpriseName,applySn,confirmDate");
			solrQuery.setSort("confirmDate", SolrQuery.ORDER.desc);

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
				Object fieldValue = solrDocument.getFieldValue("applySn") == null ? "":solrDocument.getFieldValue("applySn");
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

	
	/**
	 * 需要补充更多字段，而且添加高级查询，该方法废弃
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
			
//			solrQuery.addFilterQuery("id:" + "(2325490857967616,2325490930434048,2325490884100096)");
		 
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
			solrQuery.setSort("confirmDate", SolrQuery.ORDER.desc);

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
				Object fieldValue = solrDocument.getFieldValue("applySn") == null ? "":solrDocument.getFieldValue("applySn");
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

}
