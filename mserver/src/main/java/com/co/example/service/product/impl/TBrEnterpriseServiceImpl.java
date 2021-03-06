package com.co.example.service.product.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.assertj.core.util.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import com.co.example.dao.product.TBrEnterpriseDao;
import com.co.example.entity.enterprise.TBrEnterpriseBase;
import com.co.example.entity.enterprise.TBrEnterpriseLawsuit;
import com.co.example.entity.enterprise.TBrEnterpriseManager;
import com.co.example.entity.enterprise.TBrEnterprisePunish;
import com.co.example.entity.enterprise.TBrEnterpriseRegister;
import com.co.example.entity.enterprise.TBrEnterpriseShareholder;
import com.co.example.entity.enterprise.aide.TBrEnterpriseLawsuitQuery;
import com.co.example.entity.enterprise.aide.TBrEnterprisePunishQuery;
import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrEnterpriseCountVo;
import com.co.example.entity.product.aide.TBrEnterpriseQuery;
import com.co.example.entity.product.aide.TBrEnterpriseVo;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.enterprise.TBrEnterpriseBaseService;
import com.co.example.service.enterprise.TBrEnterpriseLawsuitService;
import com.co.example.service.enterprise.TBrEnterpriseManagerService;
import com.co.example.service.enterprise.TBrEnterprisePunishService;
import com.co.example.service.enterprise.TBrEnterpriseRegisterService;
import com.co.example.service.enterprise.TBrEnterpriseShareholderService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrProductService;
import com.co.example.utils.BaseDataUtil;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Site;

@Slf4j
@Service
public class TBrEnterpriseServiceImpl extends BaseServiceImpl<TBrEnterprise, Long> implements TBrEnterpriseService {
	@Resource
	private TBrEnterpriseDao tBrEnterpriseDao;

	@Resource
	TBrEnterpriseBaseService tBrEnterpriseBaseService;

	@Resource
	TBrEnterpriseRegisterService tBrEnterpriseRegisterService;

	@Resource
	TBrEnterpriseManagerService tBrEnterpriseManagerService;

	@Resource
	TBrEnterpriseShareholderService tBrEnterpriseShareholderService;

	@Resource
	TBrProductService tBrProductService;

	@Resource
	TBrEnterpriseLawsuitService tBrEnterpriseLawsuitService;

	@Resource
	TBrEnterprisePunishService tBrEnterprisePunishService;

	@Override
	protected BaseDao<TBrEnterprise, Long> getBaseDao() {
		return tBrEnterpriseDao;
	}

	private static final Byte APPID_TYC = 1;

	int getIndex = 1;

	String enterpriseName;

	Long id;

	String urlSearch;
	String urlNew;

	/**
	 * 测试时 暂时不保存，测试完毕后保存
	 */
	Boolean saveFlg = true;

	WebDriver chrome;

	// 从数据库获取详情页面url
	@Override
	public void addMoreEnterpriseInfo(String url, Long id) {
		this.id = id;
		// this.url = url;

	}

	Document getbaseDoc(String urlSearch) {
		chrome.get(urlSearch);
		String data = chrome.getPageSource();
		return Jsoup.parse(data);
	}
	
	TBrEnterpriseQuery tBrEnterpriseQuery = null;
	List<TBrEnterprise> tBrEnterpriseList = null;

	// 从网页抓取详情页面url
	@Override
	public void addMoreEnterpriseInfo(String urlSearch, TBrEnterprise tBrEnterprise, WebDriver chrome)
			throws Exception {
		// 一、保存到企业表
		this.chrome = chrome;
		this.urlSearch = urlSearch;
		Document doc = getbaseDoc(urlSearch);
		this.enterpriseName = tBrEnterprise.getEnterpriseName();
		if (saveFlg) {
			//处理bug
			TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
			tBrEnterpriseQuery.setEnterpriseName(enterpriseName);
			tBrEnterpriseList = queryList(tBrEnterpriseQuery);
			// 如果实际生产企业在库中不存在
			if (CollectionUtils.isNotEmpty(tBrEnterpriseList)) {
				log.info("已经有该企业，请勿重复抓取");
				return;
			}
			add(tBrEnterprise);
			this.id = tBrEnterprise.getId();
			// 二、更新产品表的企业ID字段
			TBrProductQuery tBrProductQuery = new TBrProductQuery();
			tBrProductQuery.setEnterpriseName(enterpriseName);
			List<TBrProduct> queryList = tBrProductService.queryList(tBrProductQuery);
			List<TBrProduct> updateList = Lists.newArrayList();
			for (TBrProduct tBrProduct : queryList) {
				TBrProduct tBrProductTmp = new TBrProduct();
				tBrProductTmp.setId(tBrProduct.getId());
				tBrProductTmp.setEnterpriseId(id);
				updateList.add(tBrProductTmp);
			}
			tBrProductService.updateInBatch(updateList);
		}
		Elements eList = doc.select(".search-result-single");
		String title = doc.getElementsByTag("title").text();
		Elements noResult = doc.select(".no-result-right");
		Boolean noResultFlg = CollectionUtils.isNotEmpty(noResult);
		if (noResultFlg) {
			log.info("*************未搜索到该公司****" + new Date());
			return;
		} else {
			String currentUrl = chrome.getCurrentUrl();
			if (currentUrl.contains("antirobot") || title.contains("天眼查校验")) {
				while (getIndex < 4) {
					log.info("*************遇到机器人检测1，睡眠40s,次数**" + getIndex + "***********" + new Date());
					Thread.sleep(40000);
					Document docTmp = getbaseDoc(urlSearch);
					Elements eListTmp = docTmp.select(".search-result-single");
					Boolean hasTmp = CollectionUtils.isNotEmpty(eListTmp);
					if (hasTmp) {
						eList = eListTmp;
						getIndex = 1;
						break;
					} else {
						getIndex++;
					}
				}
				if (getIndex == 4) {
					getIndex = 1;
					throw new Exception("抛出异常，请手动点选验证码！");
				}
			}
		}
		if (CollectionUtils.isNotEmpty(eList)) {
			String urlNew = eList.get(0).select(".content a").attr("href");
			this.urlNew = urlNew;
			log.info("详细页面urlNew：" + urlNew);
			chrome.get(urlNew);
			String data = chrome.getPageSource();
			Document docNew = Jsoup.parse(data);
			addInfos2Table(docNew);
		} else {
			throw new Exception("依旧有问题！");
		}
		// chrome.close();
	}

	private void addInfos2Table(Document docInfo) {
		try {
			getBaseInfo(docInfo);
			getRegisterInfo(docInfo);
		} catch (Exception e) {
			System.out.println("解析出现异常");
			e.printStackTrace();
		}

	}

	private void getBaseInfo(Document docInfo) throws Exception {
		Elements detail = docInfo.select("#company_web_top .content .detail ");
		Elements line1 = detail.select(".f0");
		Elements line2 = detail.select(".f0.clearfix");
		String telePhone = null;
		try {
			telePhone = line1.select(".sup-ie-company-header-child-1 span").get(1).text();
		} catch (Exception e) {
			Thread.sleep(40000);
			System.out.println("解析出现异常，出现验证码");
			Document docTmp = getbaseDoc(urlSearch);
			detail = docTmp.select("#company_web_top .content .detail ");
			line1 = detail.select(".f0");
			line2 = detail.select(".f0.clearfix");

		}
		String email = line1.select(".sup-ie-company-header-child-2 span").get(1).text();
		String website = line2.select(".sup-ie-company-header-child-1 a").text();
		String intro = null;
		Elements detailObj = docInfo.select("#company_base_info_detail");
		if (detailObj != null) {
			intro = detailObj.text();
			if (detailObj.toString().indexOf("script") > 0) {
				intro = detailObj.toString().replace("<script type=\"text/html\" id=\"company_base_info_detail\">", "")
						.replace("</script>", "").trim();
			}
		}
		TBrEnterpriseBase tBrEnterpriseBase = new TBrEnterpriseBase();
		tBrEnterpriseBase.setTelePhone(telePhone);
		tBrEnterpriseBase.setEmail(email);
		tBrEnterpriseBase.setWebsite(website);
		tBrEnterpriseBase.setIntro(intro);

		tBrEnterpriseBase.setMoreData1(urlNew);
		tBrEnterpriseBase.setAppId(APPID_TYC);
		tBrEnterpriseBase.setCreateTime(new Date());
		tBrEnterpriseBase.setEid(id);
		if (saveFlg) {
			tBrEnterpriseBaseService.add(tBrEnterpriseBase);
		}
	}

	private void getRegisterInfo(Document docInfo) {
		Elements rTable = docInfo.select("#_container_baseInfo .table tbody");
		String legalPerson = rTable.select("td").eq(0).select(".humancompany .name a").text();

		Elements rTable2 = docInfo.select("#_container_baseInfo .table.-striped-col tbody");
		String registerCapital = rTable2.select("tr").eq(0).select("td").eq(1).select("div").text();
		String registerDate = rTable2.select("tr").eq(1).select("td").eq(1).select("div").text();
		String enterpriseStatus = rTable2.select("tr").eq(1).select("td").eq(3).text();
		String registerId = rTable2.select("tr").eq(2).select("td").eq(3).text();
		String orgId = rTable2.select("tr").eq(3).select("td").eq(3).text();
		String creditId = rTable2.select("tr").eq(2).select("td").eq(1).text();
		// *[@id="_container_baseInfo"]/table[2]/tbody/tr[5]/td[2]
		String enterpriseType = rTable2.select("tr").eq(4).select("td").eq(1).text();
		// *[@id="_container_baseInfo"]/table[2]/tbody/tr[4]/td[2]
		String revenueId = rTable2.select("tr").eq(3).select("td").eq(1).text();
		// *[@id="_container_baseInfo"]/table[2]/tbody/tr[5]/td[4]
		String industryType = rTable2.select("tr").eq(4).select("td").eq(3).text();
		// *[@id="_container_baseInfo"]/table[2]/tbody/tr[7]/td[2]/span
		String businessTerm = rTable2.select("tr").eq(6).select("td").eq(1).text();
		// *[@id="_container_baseInfo"]/table[2]/tbody/tr[6]/td[2]
		String approveDate = rTable2.select("tr").eq(5).select("td").eq(1).text();
		// *[@id="_container_baseInfo"]/table[2]/tbody/tr[6]/td[4]
		String registerAuthority = rTable2.select("tr").eq(5).select("td").eq(3).text();
		// *[@id="_container_baseInfo"]/table[2]/tbody/tr[10]/td[2]/text()
		String registerAddress = rTable2.select("tr").eq(9).select("td").eq(1).text();
		registerAddress = registerAddress.replace("附近公司", "");
		// *[@id="_container_baseInfo"]/table[2]/tbody/tr[9]/td[4]
		String nameEn = rTable2.select("tr").eq(8).select("td").eq(3).text();
		// *[@id="_container_baseInfo"]/table[2]/tbody/tr[11]/td[2]/span
		String businessScope = rTable2.select("tr").eq(10).select("td").eq(1).text();

		// 注册信息
		TBrEnterpriseRegister tBrEnterpriseRegister = new TBrEnterpriseRegister();
		/** 法人 */
		tBrEnterpriseRegister.setLegalPerson(legalPerson);
		/** 注册资本 */
		tBrEnterpriseRegister.setRegisterCapital(registerCapital);
		/** 注册日期 */
		try {
			tBrEnterpriseRegister.setRegisterDate(DateUtils.parseDate(registerDate, "yyyy-MM-dd"));
		} catch (ParseException e) {
			System.out.println("registerDate******err*******" + registerDate);
			tBrEnterpriseRegister.setRegisterDate(null);
			// e.printStackTrace();
		}
		/** 企业状态 */
		tBrEnterpriseRegister.setEnterpriseStatus(enterpriseStatus);
		/** 工商注册号 */
		tBrEnterpriseRegister.setRegisterId(registerId);
		/** 组织机构代码 */
		tBrEnterpriseRegister.setOrgId(orgId);
		/** 统一信用代码 */
		tBrEnterpriseRegister.setCreditId(creditId);
		/** 企业类型 */
		tBrEnterpriseRegister.setEnterpriseType(enterpriseType);
		/** 纳税人识别号 */
		tBrEnterpriseRegister.setRevenueId(revenueId);
		/** 行业类型 */
		tBrEnterpriseRegister.setIndustryType(industryType);
		/** 营业期限 */
		tBrEnterpriseRegister.setBusinessTerm(businessTerm);
		/** 核准日期 */
		try {
			tBrEnterpriseRegister.setApproveDate(DateUtils.parseDate(approveDate, "yyyy-MM-dd"));
		} catch (ParseException e) {
			tBrEnterpriseRegister.setApproveDate(null);
			// e.printStackTrace();
		}
		/** 登记机关 */
		tBrEnterpriseRegister.setRegisterAuthority(registerAuthority);
		/** 注册地址 */
		tBrEnterpriseRegister.setRegisterAddress(registerAddress);
		/** 英文名称 */
		tBrEnterpriseRegister.setNameEn(nameEn);
		/** 经营范围 */
		tBrEnterpriseRegister.setBusinessScope(businessScope);
		tBrEnterpriseRegister.setAppId(APPID_TYC);
		tBrEnterpriseRegister.setCreateTime(new Date());
		tBrEnterpriseRegister.setEid(id);
		BaseDataUtil.setDefaultData(tBrEnterpriseRegister);
		if (saveFlg) {
			tBrEnterpriseRegisterService.add(tBrEnterpriseRegister);
		}
	}

	// ***************************************************************************************************************************
	private void getManagers(Long id, Document docInfo) {
		String name = null;
		String post = null;
		Elements managers = docInfo.select(".staffinfo-module-container");
		for (Element manager : managers) {
			name = manager.select("a").eq(0).text();
			post = manager.select("span").text();
			// 企业高层信息
			TBrEnterpriseManager tBrEnterpriseManager = new TBrEnterpriseManager();
			/** 姓名 */
			tBrEnterpriseManager.setName(name);
			/** 职位 */
			tBrEnterpriseManager.setPost(post);
			/** 应用id */
			tBrEnterpriseManager.setAppId(APPID_TYC);
			tBrEnterpriseManager.setCreateTime(new Date());
			tBrEnterpriseManager.setEid(id);
			tBrEnterpriseManagerService.add(tBrEnterpriseManager);
		}
	}

	private void getShareholders(Long id, Document docInfo) {
		// 股东结构
		Elements shareholders = docInfo.select("#_container_holder tbody tr");
		for (Element shareholder : shareholders) {
			String holdersName = shareholder.select("td").eq(0).select("a").eq(0).text();
			Byte type = 1;
			if (holdersName.contains("公司")) {
				type = 2;
			}
			String moneyPercent = shareholder.select("td").eq(1).select("span").text();
			String moneyPlan = shareholder.select("td").eq(2).select("span").text();

			TBrEnterpriseShareholder tBrEnterpriseShareholder = new TBrEnterpriseShareholder();
			/** 股东名称 */
			tBrEnterpriseShareholder.setName(holdersName);
			/** 股东类型 1,自然人 2企业法人 */
			tBrEnterpriseShareholder.setType(type);
			/** 出资比例 */
			tBrEnterpriseShareholder.setMoneyPercent(moneyPercent);
			/** 认缴出资 */
			tBrEnterpriseShareholder.setMoneyPlan(moneyPlan);
			/** 实际出资 */
			// tBrEnterpriseShareholder.setMoneyActual(moneyActual);
			/** 时间 */
			// tBrEnterpriseShareholder.setOperDate(operDate);
			/** 应用id */
			tBrEnterpriseShareholder.setAppId(APPID_TYC);
			tBrEnterpriseShareholder.setCreateTime(new Date());
			tBrEnterpriseShareholder.setEid(id);
			tBrEnterpriseShareholderService.add(tBrEnterpriseShareholder);
		}
	}

	private void getLawsuits(Long id, Document docInfo) {
		// 法律诉讼
		Elements lawsuits = docInfo.select("#_container_lawsuit tbody tr");
		if (CollectionUtils.isNotEmpty(lawsuits)) {
			TBrEnterpriseLawsuitQuery tBrEnterpriseLawsuitQuery = new TBrEnterpriseLawsuitQuery();
			tBrEnterpriseLawsuitQuery.setEid(id);
			long queryCount = tBrEnterpriseLawsuitService.queryCount(tBrEnterpriseLawsuitQuery);
			if (queryCount == 0) {
				for (Element lawsuit : lawsuits) {
					String caseDate = lawsuit.select("td").eq(0).select("span").text();
					String caseWrit = lawsuit.select("td").eq(1).select("a").text();
					String moreData1 = lawsuit.select("td").eq(1).select("a").attr("href");
					String caseType = lawsuit.select("td").eq(2).select("span").text();
					String caseId = lawsuit.select("td").eq(4).select("span").text();

					TBrEnterpriseLawsuit tBrEnterpriseLawsuit = new TBrEnterpriseLawsuit();
					/** 日期 */
					tBrEnterpriseLawsuit.setCaseDate(caseDate);
					/** 裁判文书 */
					tBrEnterpriseLawsuit.setCaseWrit(caseWrit);
					/** 案件类型 */
					tBrEnterpriseLawsuit.setCaseType(caseType);
					/** 案件号 */
					tBrEnterpriseLawsuit.setCaseId(caseId);
					/** 更多数据1 */
					tBrEnterpriseLawsuit.setMoreData1(moreData1);
					/** 应用id */
					tBrEnterpriseLawsuit.setAppId(APPID_TYC);
					tBrEnterpriseLawsuit.setCreateTime(new Date());
					tBrEnterpriseLawsuit.setEid(id);
					tBrEnterpriseLawsuitService.add(tBrEnterpriseLawsuit);
				}
			} else {
				System.out.println("******已有法律诉讼数据");
			}
		}
	}

	private void getPunish(Long id, Document docInfo) {
		// 行政处罚
		Elements punishs = docInfo.select("#_container_punish tbody tr");
		if (CollectionUtils.isNotEmpty(punishs)) {
			TBrEnterprisePunishQuery tBrEnterprisePunishQuery = new TBrEnterprisePunishQuery();
			tBrEnterprisePunishQuery.setEid(id);
			long queryCount = tBrEnterprisePunishService.queryCount(tBrEnterprisePunishQuery);
			if (queryCount == 0) {
				for (Element punish : punishs) {
					Elements tds = punish.select("td");
					String punishDate = null;
					String punishId = null;
					String punishType = null;
					String punishOffice = null;
					if (tds.size() == 5) {
						punishDate = tds.eq(0).select("span").text();
						punishId = tds.eq(1).select("span").text();
						punishType = tds.eq(2).select("span").text();
						punishOffice = tds.eq(3).select("div").text();
					}
					if (tds.size() == 3) {
						punishType = tds.eq(0).select("span").text();
						punishOffice = tds.eq(1).select("span").text();
					}

					TBrEnterprisePunish tBrEnterprisePunish = new TBrEnterprisePunish();
					/** 决定日期 */
					tBrEnterprisePunish.setPunishDate(punishDate);
					/** 决定书文号 */
					tBrEnterprisePunish.setPunishId(punishId);
					/** 类型 */
					tBrEnterprisePunish.setPunishType(punishType);
					/** 决定机关 */
					tBrEnterprisePunish.setPunishOffice(punishOffice);
					tBrEnterprisePunish.setCreateTime(new Date());
					tBrEnterprisePunish.setAppId(APPID_TYC);
					tBrEnterprisePunish.setEid(id);
					tBrEnterprisePunishService.add(tBrEnterprisePunish);
				}

			} else {
				System.out.println("******已有行政处罚数据");
			}

		}
	}

	private void getLawsuitsMore(Long id, Document docInfo)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		// 法律诉讼
		Elements page = docInfo.select("#_container_lawsuit .company_pager");
		if (CollectionUtils.isNotEmpty(page)) {
			// 分页抓取

		} else {
			// 单页抓取
			Elements lawsuits = docInfo.select("#_container_lawsuit tbody tr");
			if (CollectionUtils.isNotEmpty(lawsuits)) {
				TBrEnterpriseLawsuitQuery tBrEnterpriseLawsuitQuery = new TBrEnterpriseLawsuitQuery();
				tBrEnterpriseLawsuitQuery.setEid(id);
				long queryCount = tBrEnterpriseLawsuitService.queryCount(tBrEnterpriseLawsuitQuery);
				int size = lawsuits.size();
				if (queryCount >= size) {
					log.info("***无法律诉讼更新数据***");
					return;
				} else {
					List<TBrEnterpriseLawsuit> oldList = tBrEnterpriseLawsuitService.queryList();
					String oldCaseWrit = null;
					for (Element lawsuit : lawsuits) {
						Boolean match = false;
						String caseWrit = lawsuit.select("td").eq(1).select("a").text();
						for (TBrEnterpriseLawsuit tBrEnterpriseLawsuit : oldList) {
							oldCaseWrit = tBrEnterpriseLawsuit.getCaseWrit();
							if (StringUtils.isNoneBlank(caseWrit) && StringUtils.isNoneBlank(oldCaseWrit)
									&& caseWrit.contains(oldCaseWrit)) {
								log.info("***已经有该法律诉讼数据***");
								match = true;
								break;
							}
						}
						if (!match) {
							String caseDate = lawsuit.select("td").eq(0).select("span").text();
							String moreData1 = lawsuit.select("td").eq(1).select("a").attr("href");
							String caseType = lawsuit.select("td").eq(2).select("span").text();
							String caseId = lawsuit.select("td").eq(4).select("span").text();
							TBrEnterpriseLawsuit tBrEnterpriseLawsuit = new TBrEnterpriseLawsuit();
							/** 日期 */
							tBrEnterpriseLawsuit.setCaseDate(caseDate);
							/** 裁判文书 */
							tBrEnterpriseLawsuit.setCaseWrit(caseWrit);
							/** 案件类型 */
							tBrEnterpriseLawsuit.setCaseType(caseType);
							/** 案件号 */
							tBrEnterpriseLawsuit.setCaseId(caseId);
							/** 更多数据1 */
							tBrEnterpriseLawsuit.setMoreData1(moreData1);
							/** 应用id */
							tBrEnterpriseLawsuit.setAppId(APPID_TYC);
							tBrEnterpriseLawsuit.setCreateTime(new Date());
							tBrEnterpriseLawsuit.setEid(id);
							tBrEnterpriseLawsuitService.add(tBrEnterpriseLawsuit);
							log.info("***新增法律诉讼数据***");
						}

					}
				}
			}
		}

	}

	private void updateStatus(Long id) {
		TBrEnterprise tBrEnterprise = new TBrEnterprise();
		tBrEnterprise.setId(id);
		tBrEnterprise.setCreateBy(4l);
		updateByIdSelective(tBrEnterprise);
	}

	private Site site = Site.me().setDomain("www.tianyancha.com").setSleepTime(300).setUserAgent(
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31")
			// 【重要】：以下信息可以模拟登陆，信息全部来自于浏览器
			.addCookie("Hm_lpvt_e92c8d65d92d534b0fc290df538b4758", "1509758097")
			.addCookie("Hm_lvt_e92c8d65d92d534b0fc290df538b4758", "1508738490,1508982995,1509700252,1509758095")
			.addCookie("OA", "HuYx+NCtVRzx+nf9EXwoY8yiDETGct3sUaThPQXCoTXdQvFqDdfCfxW804C4uf6i")//
			.addCookie("RTYCID", "ea18316aa1b449c4830c52cf48e60ff9")
			.addCookie("TYCID", "e2f2c7a0b31b11e79cd44b77fbc8ef1e").addCookie("_csrf", "v2OvD7IKS/oHToKBvWlmVw==")//
			.addCookie("_csrf_bk", "d3998fa5c5c424f91bc06eec4f752758")//
			.addCookie("aliyungf_tc", "AQAAAJkt3AJGbQgA+hHQPP69LsrHV142")//
			.addCookie("auth_token",
					"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODI1NDEzMzM2NyIsImlhdCI6MTUwOTcwMDM1MCwiZXhwIjoxNTI1MjUyMzUwfQ.Q-eKAzDTbP2MXiuAD7HM0xwS22CEY--_awmt23JQfXlxROxIa54uqFnbaao6ZNVSKbdBuJCjb-l5XpfOVYtOHw")
			.addCookie("bannerFlag", "true").addCookie("csrfToken", "OfvOqaw6c4sR0yJyggl14Xoq")//
			.addCookie("ssuid", "136256036")
			.addCookie("tyc-user-info",
					"%257B%2522token%2522%253A%2522eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODI1NDEzMzM2NyIsImlhdCI6MTUwOTcwMDM1MCwiZXhwIjoxNTI1MjUyMzUwfQ.Q-eKAzDTbP2MXiuAD7HM0xwS22CEY--_awmt23JQfXlxROxIa54uqFnbaao6ZNVSKbdBuJCjb-l5XpfOVYtOHw%2522%252C%2522integrity%2522%253A%25220%2525%2522%252C%2522state%2522%253A%25220%2522%252C%2522vnum%2522%253A%25220%2522%252C%2522onum%2522%253A%25220%2522%252C%2522mobile%2522%253A%252218254133367%2522%257D")
			.addCookie("uccid", "868866fdf6ec3c40fb6943dc1dccff80")//
	;

	@Override
	public List<TBrEnterpriseCountVo> queryEnterpriseCount(String limitTime, String endTime) {
		return tBrEnterpriseDao.selectEnterpriseCount(limitTime, endTime);
	}

	@Override
	public List<TBrEnterpriseVo> queryEnterpriseListByProductId(Long id) {
		TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
		tBrEnterpriseQuery.setJoinFlg(true);
		tBrEnterpriseQuery.setProductId(id);
		return queryList(tBrEnterpriseQuery);
	}

}