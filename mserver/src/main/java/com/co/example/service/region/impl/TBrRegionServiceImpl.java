package com.co.example.service.region.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.co.example.common.utils.JsoupUtil;
import com.co.example.dao.region.TBrRegionDao;
import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.aide.TBrEnterpriseQuery;
import com.co.example.entity.region.TBrRegion;
import com.co.example.entity.region.TBrRegionShort;
import com.co.example.entity.region.aide.TBrRegionQuery;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.region.TBrRegionService;
import com.co.example.service.region.TBrRegionShortService;
import com.co.example.utils.BaseDataUtil;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import com.github.moncat.common.utils.PageReq;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TBrRegionServiceImpl extends BaseServiceImpl<TBrRegion, Long> implements TBrRegionService {
	@Resource
	private TBrRegionDao tBrRegionDao;

	@Resource
	private TBrEnterpriseService tBrEnterpriseService;

	@Resource
	private TBrRegionShortService tBrRegionShortService;

	@Override
	protected BaseDao<TBrRegion, Long> getBaseDao() {
		return tBrRegionDao;
	}

	public static final String url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/";

	@Override
	public void crawRegionData() throws InterruptedException {
		log.info("***开始抓取***");
		// addProvince();
		// addCity();
		// addContry();
		addTown();
	}

	@Override
	public void findData(TBrRegion tBrRegion) throws InterruptedException {
		String regionId = tBrRegion.getRegionId();
		int length = regionId.length();
		String urlTmp = null;
		String tag = "";
		if (length == 6) {
			String substring = regionId.substring(0, 2);
			String substring2 = regionId.substring(0, 4);
			urlTmp = url + substring + "/" + substring2 + ".html";
			regionId = regionId + "000000";
			tag = ".countytr";
		} else if (length == 12) {
			String substring = regionId.substring(0, 2);
			String substring2 = regionId.substring(2, 4);
			String substring3 = regionId.substring(0, 6);
			urlTmp = url + substring + "/" + substring2 + "/" + substring3 + ".html";
			tag = ".towntr";
		}

		Document doc = JsoupUtil.getDoc(urlTmp, "gbk");
		// System.out.println(doc.text());
		Elements es = doc.select(tag);
		int size = es.size();
		for (int i = 0; i < size; i++) {
			Elements element = es.get(i).select("td").eq(0).select("a");
			Elements element2 = es.get(i).select("td").eq(1).select("a");
			if (StringUtils.equals(element.text(), regionId)) {
				String text = element2.text();
				TBrRegionQuery tBrRegionQuery = new TBrRegionQuery();
				tBrRegionQuery.setId(tBrRegion.getId());
				tBrRegionQuery.setName(text);
				updateByIdSelective(tBrRegionQuery);
				return;
			}

		}

	}

	private void addProvince() throws InterruptedException {
		Byte level = 2;
		String startUrl = url + "index.html";
		Document doc = JsoupUtil.getDoc(startUrl, "gb2312");
		Elements provinces = doc.select(".provincetr td a");
		Element element = null;
		String regionId = null;
		String name = null;
		for (int i = 0; i < provinces.size(); i++) {
			element = provinces.get(i);
			regionId = element.attr("href").replace(".html", "") + "0000";
			name = element.text();
			log.info("***got***" + name);
			saveData(regionId, name, level, 1 + "", element.attr("href"));
		}

	}

	private void addCity() throws InterruptedException {
		Byte level = 3;
		String regionId = null;
		String urlTmp = null;
		int size = 0;
		Elements element = null;
		Elements element2 = null;
		List<TBrRegion> data = getData(level);
		for (TBrRegion tBrRegion : data) {
			regionId = tBrRegion.getRegionId();
			String url2 = tBrRegion.getUrl();
			if (StringUtils.isNotBlank(url2)) {
				urlTmp = url + url2;
				Document doc = JsoupUtil.getDoc(urlTmp, "gb2312");
				Elements es = doc.select(".citytr");
				size = es.size();
				for (int i = 0; i < size; i++) {
					element = es.get(i).select("td").eq(0).select("a");
					element2 = es.get(i).select("td").eq(1).select("a");
					String subRegionId = element.text().substring(0, 6);
					String text = element2.text();
					String href = element.attr("href");
					log.info("***got***" + text);
					saveData(subRegionId, text, level, regionId, href);
				}
			}
		}
	}

	private void addContry() throws InterruptedException {
		Byte level = 4;
		String regionId = null;
		String urlTmp = null;
		int size = 0;
		Elements element = null;
		Elements element2 = null;
		TBrRegion tBrRegion = null;
		List<TBrRegion> data = getData(level);
		for (int j = 0; j < data.size(); j++) {
			tBrRegion = data.get(j);
			regionId = tBrRegion.getRegionId();
			String url2 = tBrRegion.getUrl();
			log.info("***got***" + url2);
			if (StringUtils.isNotBlank(url2)) {
				urlTmp = url + url2;
				Document doc = JsoupUtil.getDoc(urlTmp, "gb2312");
				Elements es = doc.select(".countytr");
				size = es.size();
				String href = null;
				for (int i = 0; i < size; i++) {
					element = es.get(i).select("td").eq(0).select("a");
					if (element.size() == 0) {
						element = es.get(i).select("td").eq(0);
						element2 = es.get(i).select("td").eq(1);
					} else {
						element2 = es.get(i).select("td").eq(1).select("a");
						href = element.attr("href");
					}
					String subRegionId = element.text().substring(0, 6);
					String text = element2.text();
					saveData(subRegionId, text, level, regionId, href);
				}
			}
		}
	}

	private void addTown() throws InterruptedException {
		Byte level = 5;
		String regionId = null;
		String urlTmp = null;
		int size = 0;
		Elements element = null;
		Elements element2 = null;
		TBrRegion tBrRegion = null;
		List<TBrRegion> data = getData(level);
		for (int j = 3123; j < data.size(); j++) {
			tBrRegion = data.get(j);
			regionId = tBrRegion.getRegionId();
			// Thread.sleep(400);
			String url2 = tBrRegion.getUrl();
			if (StringUtils.isNotBlank(url2)) {
				urlTmp = url + regionId.substring(0, 2) + "/" + url2;
				log.info("***got***" + j + "***" + urlTmp);
				Document doc = JsoupUtil.getDoc(urlTmp, "gb2312");
				Elements es = doc.select(".towntr");
				size = es.size();
				String href = null;
				for (int i = 0; i < size; i++) {
					element = es.get(i).select("td").eq(0).select("a");
					if (element.size() == 0) {
						element = es.get(i).select("td").eq(0);
						element2 = es.get(i).select("td").eq(1);
					} else {
						element2 = es.get(i).select("td").eq(1).select("a");
						href = element.attr("href");
					}
					String subRegionId = element.text();
					String text = element2.text();
					saveData(subRegionId, text, level, regionId, href);
				}
			}
		}
	}

	List<TBrRegion> getData(Byte level) {
		Byte levelParent = (byte) (level - 1);
		TBrRegionQuery query = new TBrRegionQuery();
		query.setLevel(levelParent);
		return queryList(query);
	}

	void saveData(String regionId, String name, Byte level, String parentRegionId, String url) {
		TBrRegion tBrRegion = new TBrRegion();
		tBrRegion.setRegionId(regionId);
		tBrRegion.setName(name);
		tBrRegion.setLevel(level);
		tBrRegion.setUrl(url);
		tBrRegion.setParentRegionId(parentRegionId);
		BaseDataUtil.setDefaultData(tBrRegion);
		add(tBrRegion);
	}

	@Override
	public List<TBrRegion> getRegionListByParentRegionId(String id) {
		TBrRegionQuery tBrRegionQuery = new TBrRegionQuery();
		tBrRegionQuery.setParentRegionId(id);
		List<TBrRegion> list = queryList(tBrRegionQuery);
		return list;
	}

	@Override
	public void setEnterpriseRegion() {
		TBrRegionQuery tBrRegionQuery = new TBrRegionQuery();
		tBrRegionQuery.setLevelLimit((byte) 4);
		List<TBrRegion> regionList = queryList(tBrRegionQuery);
		TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
		TBrEnterprise tBrEnterpriseTmp = null;
		tBrEnterpriseQuery.setProvince("1");
		PageReq pagereq = new PageReq();
		pagereq.setPageSize(2000);
		pagereq.setPage(0);
		long queryCount = tBrEnterpriseService.queryCount(tBrEnterpriseQuery);
		Boolean match = false;
		if (queryCount > 0) {
			queryCount = tBrEnterpriseService.queryCount(tBrEnterpriseQuery);
			Page<TBrEnterprise> queryPageList = tBrEnterpriseService.queryPageList(tBrEnterpriseQuery, pagereq);
			for (TBrEnterprise tBrEnterprise : queryPageList) {
				String enterpriseName = tBrEnterprise.getEnterpriseName();
//				String producingArea = tBrEnterprise.getProducingArea();
				System.out.println("--" + enterpriseName);
				match = false;
				for (TBrRegion tBrRegion : regionList) {
					String name = tBrRegion.getName();
					name = name.replace("省", "").replace("自治区", "").replace("市", "").replace("县", "").replace("自治州", "")
							.replace("地区", "").replace("盟", "");
					if ((enterpriseName.contains(name) /* || producingArea.contains(name)*/ )&& StringUtils.isNoneBlank(name)) {
						Byte level = tBrRegion.getLevel();
						Long regionId = tBrRegion.getId();
						String parentRegionId = tBrRegion.getParentRegionId();
						tBrEnterpriseTmp = new TBrEnterprise();
						String provinceName = getProvinceName(regionId, level, name, parentRegionId);
						tBrEnterpriseTmp.setProvince(provinceName);
						tBrEnterpriseTmp.setRegionId(regionId);
						tBrEnterpriseTmp.setId(tBrEnterprise.getId());
						tBrEnterpriseService.updateByIdSelective(tBrEnterpriseTmp);
						match = true;
						System.out.println("--" + enterpriseName + "--" + match + "--" + provinceName);
						break;
					}
				}
				if (!match) {
					System.out.println("--" + enterpriseName + "--" + match);
					tBrEnterpriseTmp = new TBrEnterprise();
					tBrEnterpriseTmp.setProvince("2");
					tBrEnterpriseTmp.setId(tBrEnterprise.getId());
					tBrEnterpriseService.updateByIdSelective(tBrEnterpriseTmp);
				}
				queryCount--;
				System.out.println("--" + queryCount);
			}
		}

	}

	String getProvinceName(Long id, Byte level, String name, String parentRegionId) {
		if (level == 2) {

		} else if (level == 3) {
			TBrRegion tBrRegion = new TBrRegion();
			tBrRegion.setRegionId(parentRegionId);
			TBrRegion queryOne = queryOne(tBrRegion);
			name = queryOne.getName();
		} else {
			System.out.println("*****level不正确*******");
		}
		name = name.replace("省", "").replace("市", "").replace("壮族自治区", "").replace("回族自治区", "").replace("维吾尔自治区", "")
				.replace("自治区", "");
		return name;
	}

	@Override
	public void setEnterpriseRegionByShort() {
		List<TBrRegionShort> regionList = tBrRegionShortService.queryList();
		TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
		TBrEnterprise tBrEnterpriseTmp = null;
		tBrEnterpriseQuery.setProvince("0");
		PageReq pagereq = new PageReq();
		pagereq.setPageSize(1000);
		pagereq.setPage(0);
		long queryCount = tBrEnterpriseService.queryCount(tBrEnterpriseQuery);
		Boolean match = false;
		while (queryCount > 0) {
			queryCount = tBrEnterpriseService.queryCount(tBrEnterpriseQuery);
			Page<TBrEnterprise> queryPageList = tBrEnterpriseService.queryPageList(tBrEnterpriseQuery, pagereq);
			for (TBrEnterprise tBrEnterprise : queryPageList) {
				String applySn = tBrEnterprise.getApplySn();
				System.out.println("--" + applySn);
				match = false;
				for (TBrRegionShort tBrRegion : regionList) {
					String sn = tBrRegion.getShortName();
					if (applySn.contains(sn)) {
						tBrEnterpriseTmp = new TBrEnterprise();
						String provinceName = tBrRegion.getName();
						provinceName = provinceName.replace("省", "").replace("市", "").replace("壮族自治区", "")
								.replace("回族自治区", "").replace("维吾尔自治区", "").replace("自治区", "");
						tBrEnterpriseTmp.setProvince(tBrRegion.getName());
						tBrEnterpriseTmp.setId(tBrEnterprise.getId());
						tBrEnterpriseService.updateByIdSelective(tBrEnterpriseTmp);
						match = true;
						System.out.println("--" + applySn + "--" + match + "--" + provinceName);
						break;
					}
				}
				if (!match) {
					System.out.println("--" + applySn + "--" + match);
					tBrEnterpriseTmp = new TBrEnterprise();
					tBrEnterpriseTmp.setProvince("1");
					tBrEnterpriseTmp.setId(tBrEnterprise.getId());
					tBrEnterpriseService.updateByIdSelective(tBrEnterpriseTmp);
				}
				queryCount--;
				System.out.println("--" + queryCount);
			}
		}

	}

	@Override
	public void setEnterpriseRegionByAll(TBrEnterprise tBrEnterprise) {
		TBrRegionQuery tBrRegionQuery = new TBrRegionQuery();
		//此处不包含县级市
		tBrRegionQuery.setLevelLimit((byte) 4);
		List<TBrRegion> regionList = queryList(tBrRegionQuery);
		Boolean match = false;
		String enterpriseName = tBrEnterprise.getEnterpriseName();
//		String producingArea = tBrEnterprise.getProducingArea();
		System.out.println("--" + enterpriseName);
		match = false;
		for (TBrRegion tBrRegion : regionList) {
			String name = tBrRegion.getName();
			name = name.replace("省", "").replace("自治区", "").replace("市", "").replace("县", "").replace("自治州", "")
					.replace("地区", "").replace("盟", "");
			//暂时取消以地点匹配，地点会出现  桂林市山东路的现象
			if ((enterpriseName.contains(name) /*|| producingArea.contains(name) */)&& StringUtils.isNoneBlank(name)) {
				Byte level = tBrRegion.getLevel();
				Long regionId = tBrRegion.getId();
				String parentRegionId = tBrRegion.getParentRegionId();
				String provinceName = getProvinceName(regionId, level, name, parentRegionId);
				tBrEnterprise.setRegionId(regionId);
				tBrEnterprise.setProvince(provinceName);
				match = true;
				System.out.println("--" + enterpriseName + "--" + match + "--" + provinceName);
				break;
			}
		}
		if(!match){
			List<TBrRegionShort> regionShortList = tBrRegionShortService.queryList();
			for (TBrRegionShort tBrRegionShort : regionShortList) {
				String sn = tBrRegionShort.getShortName();
				String applySn = tBrEnterprise.getApplySn();
				if (StringUtils.isNoneBlank(applySn) && applySn.contains(sn)) {
					String provinceName = tBrRegionShort.getName();
					provinceName = provinceName.replace("省", "").replace("市", "").replace("壮族自治区", "")
							.replace("回族自治区", "").replace("维吾尔自治区", "").replace("自治区", "");
					tBrEnterprise.setProvince(tBrRegionShort.getName());
					match = true;
					System.out.println("--" + applySn + "--" + match + "--" + provinceName);
					break;
				}
			}
		}
		if (!match) {
			System.out.println("--" + enterpriseName + "--" + match);
			tBrEnterprise.setProvince("2");
		}
	}

}
