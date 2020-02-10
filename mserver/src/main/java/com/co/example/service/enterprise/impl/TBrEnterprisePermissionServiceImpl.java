package com.co.example.service.enterprise.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.co.example.common.utils.HttpUtils;
import com.co.example.dao.enterprise.TBrEnterprisePermissionDao;
import com.co.example.entity.enterprise.TBrEnterprisePermission;
import com.co.example.entity.enterprise.aide.TBrEnterprisePermissionQuery;
import com.co.example.entity.product.TBrEnterprise;
import com.co.example.service.enterprise.TBrEnterprisePermissionService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.utils.BaseDataUtil;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TBrEnterprisePermissionServiceImpl extends BaseServiceImpl<TBrEnterprisePermission, Long>
		implements TBrEnterprisePermissionService {
	@Resource
	private TBrEnterprisePermissionDao tBrEnterprisePermissionDao;
	@Resource
	private TBrEnterpriseService tBrEnterpriseService;

	@Override
	protected BaseDao<TBrEnterprisePermission, Long> getBaseDao() {
		return tBrEnterprisePermissionDao;
	}

	String epListUrl = "http://125.35.6.84:81/xk/itownet/portalAction.do?method=getXkzsList";
	String epOneUrl = "http://125.35.6.84:81/xk/itownet/portalAction.do?method=getXkzsById";

	@Override
	public int getEnterprisePermission(int page, String dateStr, String idLimit) {
		// 记录json数据
		HashMap<String, String> params = Maps.newHashMap();
		params.put("on", "true");
		params.put("page", page + "");
		params.put("pageSize", "15");
		params.put("conditionType", "1");
		TBrEnterprisePermission tBrEnterprisePermission = null;
		String text = HttpUtils.postData(epListUrl, params);
		if (StringUtils.isBlank(text)) {
			log.info("***该页无数据*** " + page);
			return 2;
		} else {
			JSONObject base = JSON.parseObject(text);
			JSONArray eList = base.getJSONArray("list");
			if (CollectionUtils.isNotEmpty(eList)) {
				for (int i = 0; i < eList.size(); i++) {
					JSONObject obj = eList.getJSONObject(i);
					String id = obj.get("ID").toString();
					if (StringUtils.isNotBlank(id)) {
						TBrEnterprisePermissionQuery tBrEnterprisePermissionQuery = new TBrEnterprisePermissionQuery();
						tBrEnterprisePermissionQuery.setMoreData1(id);
						List<TBrEnterprisePermission> queryList = queryList(tBrEnterprisePermissionQuery);
						if (queryList.size() > 0) {
							log.info("已经有该数据，请勿重复抓取： " + id);
							continue;
						}
					}

					HashMap<String, String> map = Maps.newHashMap();
					map.put("id", id);
					String text2 = HttpUtils.postData(epOneUrl, map);
					JSONObject one = JSON.parseObject(text2);
					//
					tBrEnterprisePermission = new TBrEnterprisePermission();
					/** 企业名称 */
					String enterpriseName = checkNull(one.get("epsName"));
					tBrEnterprisePermission.setEnterpriseName(enterpriseName);
					try {
						TBrEnterprise tBrEnterprise = new TBrEnterprise();
						tBrEnterprise.setEnterpriseName(enterpriseName);
						List<TBrEnterprise> list = tBrEnterpriseService.queryList(tBrEnterprise);
						if (list != null && list.size() > 0) {
							TBrEnterprise tBrEnterpriseTmp = list.get(0);
							tBrEnterprisePermission.setEid(tBrEnterpriseTmp.getId());
						}
					} catch (Exception e) {
						log.info("未匹配上企业");
					}

					/** 许可证id */
					String permissionId = checkNull(one.get("productSn"));
					tBrEnterprisePermission.setPermissionId(permissionId);
					/** 许可项目 */
					String permissionProject = checkNull(one.get("certStr"));
					tBrEnterprisePermission.setPermissionProject(permissionProject);

					/** 企业住所 */
					String enterpriseLocal = checkNull(one.get("epsAddress"));
					tBrEnterprisePermission.setEnterpriseLocal(enterpriseLocal);
					/** 生产地址 */
					String productingLocal = checkNull(one.get("epsProductAddress"));
					tBrEnterprisePermission.setProductingLocal(productingLocal);
					/** 社会信用代码 */
					String creditId = checkNull(one.get("businessLicenseNumber"));
					tBrEnterprisePermission.setCreditId(creditId);
					/** 法定代表人 */
					String personLegal = checkNull(one.get("legalPerson"));
					tBrEnterprisePermission.setPersonLegal(personLegal);
					/** 企业负责人 */
					String personIncharge = checkNull(one.get("businessPerson"));
					tBrEnterprisePermission.setPersonIncharge(personIncharge);
					/** 质量负责人 */
					String personQuality = checkNull(one.get("qualityPerson"));
					tBrEnterprisePermission.setPersonQuality(personQuality);
					/** 发证机关 */
					String licenseOffice = checkNull(one.get("qfManagerName"));
					tBrEnterprisePermission.setLicenseOffice(licenseOffice);
					/** 签发人 */
					String licensePerson = checkNull(one.get("xkName"));
					tBrEnterprisePermission.setLicensePerson(licensePerson);
					/** 日常监督管理机构 */
					String supervisionOffice = checkNull(one.get("rcManagerDepartName"));
					tBrEnterprisePermission.setSupervisionOffice(supervisionOffice);
					/** 日常监督管理人员 */
					String supervisionPerson = checkNull(one.get("rcManagerUser"));
					tBrEnterprisePermission.setSupervisionPerson(supervisionPerson);
					/** 有效期至 */
					String endDate = checkNull(formatDate(one.get("xkDate").toString()));
					tBrEnterprisePermission.setEndDate(endDate);
					/** 发证日期 */
					String startDate = checkNull(formatDate(one.get("xkDateStr").toString()));
					tBrEnterprisePermission.setStartDate(startDate);
					/** 状态 */
					String status = checkNull(one.get("xkType"));
					if (status.contains("20")) {
						tBrEnterprisePermission.setStatus("正常");
					}
					/** 投诉举报电话 */
					// tBrEnterprisePermission.setHotline("12331");
					/** 用于冗余数据 用来存储用于去重的id */
					tBrEnterprisePermission.setMoreData1(id);
					BaseDataUtil.setDefaultData(tBrEnterprisePermission);
					add(tBrEnterprisePermission);
				}

			}
		}
		return 0;
	}

	String checkNull(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString();
		}
	}

	@Override
	public TBrEnterprisePermission queryVoByEId(Long id) {
		TBrEnterprisePermission tBrEnterprisePermission = new TBrEnterprisePermission();
		tBrEnterprisePermission.setEid(id);
		return queryOne(tBrEnterprisePermission);
	}

	TBrEnterprisePermission tBrEnterprisePermission = null;
	TBrEnterprisePermissionQuery query = null;

	@Override
	public int getEnterprisePermission(String line) {
		String[] split = line.split(",");
		/** 企业名称 */
		String enterpriseName = checkNull(split[9]);
		if (StringUtils.isBlank(enterpriseName)) {
			log.info("企业名称为空");
			return 0;
		}
		// 检查许可表是否已经有该企业
		query = new TBrEnterprisePermissionQuery();
		query.setEnterpriseName(enterpriseName);
		long queryCount = queryCount(query);
		if (queryCount > 0) {
			log.info("请勿重复保存许可数据==" + enterpriseName);
			return 0;
		}

		tBrEnterprisePermission = new TBrEnterprisePermission();
		tBrEnterprisePermission.setEnterpriseName(enterpriseName);
		try {
			TBrEnterprise tBrEnterprise = new TBrEnterprise();
			tBrEnterprise.setEnterpriseName(enterpriseName);
			List<TBrEnterprise> list = tBrEnterpriseService.queryList(tBrEnterprise);
			if (list != null && list.size() > 0) {
				TBrEnterprise tBrEnterpriseTmp = list.get(0);
				tBrEnterprisePermission.setEid(tBrEnterpriseTmp.getId());
			}
		} catch (Exception e) {
			log.info("未匹配上企业");
		}

		/** 许可证id */
		String permissionId = checkNull(split[19]);
		tBrEnterprisePermission.setPermissionId(permissionId);
		/** 许可项目 */
		String permissionProject = checkNull(split[2]);
		tBrEnterprisePermission.setPermissionProject(permissionProject);

		/** 企业住所 */
		String enterpriseLocal = checkNull(split[8]);
		tBrEnterprisePermission.setEnterpriseLocal(enterpriseLocal);
		/** 生产地址 */
		String productingLocal = checkNull(split[10]);
		tBrEnterprisePermission.setProductingLocal(productingLocal);
		/** 社会信用代码 */
		String creditId = checkNull(split[0]);
		tBrEnterprisePermission.setCreditId(creditId);
		/** 法定代表人 */
		String personLegal = checkNull(split[13]);
		tBrEnterprisePermission.setPersonLegal(personLegal);
		/** 企业负责人 */
		String personIncharge = checkNull(split[1]);
		tBrEnterprisePermission.setPersonIncharge(personIncharge);
		/** 质量负责人 */
		String personQuality = checkNull(split[23]);
		tBrEnterprisePermission.setPersonQuality(personQuality);
		/** 发证机关 */
		String licenseOffice = checkNull(split[22]);
		tBrEnterprisePermission.setLicenseOffice(licenseOffice);
		/** 签发人 */
		String licensePerson = checkNull(split[29]);
		tBrEnterprisePermission.setLicensePerson(licensePerson);
		/** 日常监督管理机构 */
		String supervisionOffice = checkNull(split[24]);
		tBrEnterprisePermission.setSupervisionOffice(supervisionOffice);
		/** 日常监督管理人员 */
		String supervisionPerson = checkNull(split[25]);
		tBrEnterprisePermission.setSupervisionPerson(supervisionPerson);
		/** 有效期至 */
		String endDate = checkNull(split[27]);
		tBrEnterprisePermission.setEndDate(formatDate(endDate));
		/** 发证日期 */
		String startDate = checkNull(split[28]);
		tBrEnterprisePermission.setStartDate(formatDate(startDate));
		/** 状态 */
		String status = checkNull(split[32]);
		if (status.contains("20")) {
			tBrEnterprisePermission.setStatus("正常");
		}
		/** 投诉举报电话 */
		tBrEnterprisePermission.setHotline("12331");
		/** 用于冗余数据 用来存储用于去重的id */
		BaseDataUtil.setDefaultData(tBrEnterprisePermission);
		add(tBrEnterprisePermission);
		return 1;
	}

	String formatDate(String dateStr) {
		try {
			String date = dateStr.replace("/", "-");
			String[] split = date.split("-");
			String mm = split[1].length() == 1 ? "0" + split[1] : split[1];
			String dd = split[2].length() == 1 ? "0" + split[2] : split[2];
			date = split[0] + "-" + mm + "-" + dd;
			return date;
		} catch (Exception e) {
			// e.printStackTrace();
			return dateStr;
		}
	}

	@Override
	public int getEnterprisePermissionJson(Map<String, String> one) {
		// 记录json数据
		/** 企业名称 */
		String enterpriseName = checkNull(one.get("epsName"));
		query = new TBrEnterprisePermissionQuery();
		query.setEnterpriseName(enterpriseName);
		long queryCount = queryCount(query);
		if (queryCount > 0) {
			log.info("请勿重复保存许可数据==" + enterpriseName);
			return 0;
		}
		tBrEnterprisePermission = new TBrEnterprisePermission();
		tBrEnterprisePermission.setEnterpriseName(enterpriseName);
		try {
			TBrEnterprise tBrEnterprise = new TBrEnterprise();
			tBrEnterprise.setEnterpriseName(enterpriseName);
			List<TBrEnterprise> list = tBrEnterpriseService.queryList(tBrEnterprise);
			if (list != null && list.size() > 0) {
				TBrEnterprise tBrEnterpriseTmp = list.get(0);
				tBrEnterprisePermission.setEid(tBrEnterpriseTmp.getId());
			}
		} catch (Exception e) {
			log.info("未匹配上企业");
		}
		/** 许可证id */
		String permissionId = checkNull(one.get("productSn"));
		tBrEnterprisePermission.setPermissionId(permissionId);
		/** 许可项目 */
		String permissionProject = checkNull(one.get("certStr"));
		tBrEnterprisePermission.setPermissionProject(permissionProject);

		/** 企业住所 */
		String enterpriseLocal = checkNull(one.get("epsAddress"));
		tBrEnterprisePermission.setEnterpriseLocal(enterpriseLocal);
		/** 生产地址 */
		String productingLocal = checkNull(one.get("epsProductAddress"));
		tBrEnterprisePermission.setProductingLocal(productingLocal);
		/** 社会信用代码 */
		String creditId = checkNull(one.get("businessLicenseNumber"));
		tBrEnterprisePermission.setCreditId(creditId);
		/** 法定代表人 */
		String personLegal = checkNull(one.get("legalPerson"));
		tBrEnterprisePermission.setPersonLegal(personLegal);
		/** 企业负责人 */
		String personIncharge = checkNull(one.get("businessPerson"));
		tBrEnterprisePermission.setPersonIncharge(personIncharge);
		/** 质量负责人 */
		String personQuality = checkNull(one.get("qualityPerson"));
		tBrEnterprisePermission.setPersonQuality(personQuality);
		/** 发证机关 */
		String licenseOffice = checkNull(one.get("qfManagerName"));
		tBrEnterprisePermission.setLicenseOffice(licenseOffice);
		/** 签发人 */
		String licensePerson = checkNull(one.get("xkName"));
		tBrEnterprisePermission.setLicensePerson(licensePerson);
		/** 日常监督管理机构 */
		String supervisionOffice = checkNull(one.get("rcManagerDepartName"));
		tBrEnterprisePermission.setSupervisionOffice(supervisionOffice);
		/** 日常监督管理人员 */
		String supervisionPerson = checkNull(one.get("rcManagerUser"));
		tBrEnterprisePermission.setSupervisionPerson(supervisionPerson);
		/** 有效期至 */
		String endDate = checkNull(formatDate(one.get("xkDate").toString()));
		tBrEnterprisePermission.setEndDate(endDate);
		/** 发证日期 */
		String startDate = checkNull(formatDate(one.get("xkDateStr").toString()));
		tBrEnterprisePermission.setStartDate(startDate);
		/** 状态 */
		String status = checkNull(one.get("xkType"));
		if (status.contains("20")) {
			tBrEnterprisePermission.setStatus("正常");
		}
		/** 投诉举报电话 */
		 tBrEnterprisePermission.setHotline("12331");
		/** 用于冗余数据 用来存储用于去重的id */
		BaseDataUtil.setDefaultData(tBrEnterprisePermission);
		add(tBrEnterprisePermission);
		return 0;
	}
}