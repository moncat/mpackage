package com.co.example.service.product.impl;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
import com.co.example.service.enterprise.TBrEnterpriseBaseService;
import com.co.example.service.enterprise.TBrEnterpriseLawsuitService;
import com.co.example.service.enterprise.TBrEnterpriseManagerService;
import com.co.example.service.enterprise.TBrEnterprisePunishService;
import com.co.example.service.enterprise.TBrEnterpriseRegisterService;
import com.co.example.service.enterprise.TBrEnterpriseShareholderService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrProductService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;


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
    
    
    String enterpriseName;
    
    Long id;
    
    String url;
    
     
    @Override
	public void addMoreEnterpriseInfo(String url,Long id) {
    	this.id = id;
    	Spider spider = Spider.create(new inClass());
		spider.addUrl(url).run();
	}
    
	@Override
	public void addMoreEnterpriseInfo(Document doc,String enterpriseName,Long id,Proxy[] proxyArr) {
		this.enterpriseName = enterpriseName;
		this.id = id;
		//   保存其他表，并把该表中的 CreateBy update为1  注意在一个事务
		Elements eList = doc.select(".search_result_single");
		Element one = null;
		String enterpriseText  = null;
		
		if(CollectionUtils.isNotEmpty(eList)){
			one = eList.get(0);
			enterpriseText = one.select(".search_right_item a").eq(0).select("span").text();
//			if(StringUtils.equals(enterpriseName, enterpriseText)){
			if(StringUtils.contains(enterpriseText, enterpriseName) || StringUtils.contains(enterpriseName,enterpriseText)){
				String url = one.select(".search_right_item a").eq(0).attr("href");
				this.url = url;
				
//				HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
//			    httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(proxyArr));
				Spider spider = Spider.create(new inClass());
//				spider.setDownloader(httpClientDownloader);
				spider.addUrl(url).run();
				
			}else{
//				TBrEnterprise tBrEnterprise = new TBrEnterprise();
//				tBrEnterprise.setId(id);
//				tBrEnterprise.setCreateBy(2l);
//				updateByIdSelective(tBrEnterprise);
				return;
			}
		}else{
			System.out.println("**************************机器人检测**************************");
			System.out.println("**************************机器人检测**************************");
			System.out.println("**************************机器人检测**************************"+new Date());
			System.out.println("**************************机器人检测**************************");
			System.out.println("**************************机器人检测**************************");
			System.out.println("**************************机器人检测**************************");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void addInfos2Table(Long id, String url, Document docInfo) {
		Elements baseInfo = docInfo.select("#company_web_top .company_header_width .f14 div.in-block");
		if(CollectionUtils.isEmpty(baseInfo)){
			System.out.println("**************************机器人检测*******");
			System.out.println("**************************机器人检测*******");
			System.out.println("**************************机器人检测*******"+new Date());
			System.out.println("**************************机器人检测*******");
			System.out.println("**************************机器人检测*******");
			System.out.println("**************************机器人检测*******");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ;
		}
//		String  telePhone = baseInfo.eq(0).select("span").eq(1).text();
//		String email = baseInfo.eq(1).select("span").eq(1).text();
//		String website = baseInfo.eq(2).select("span").eq(1).text();
//		String intro = null;
//		Elements detailObj = docInfo.select("#company_base_info_detail");
//		if(detailObj != null){
//			intro = detailObj.text();
//			if(detailObj.toString().indexOf("script")>0){
//				intro = detailObj.toString().replace("<script type=\"text/html\" id=\"company_base_info_detail\">", "").replace("</script>", "").trim();
//			}
//		}
//		TBrEnterpriseBase tBrEnterpriseBase = new TBrEnterpriseBase();
//		tBrEnterpriseBase.setTelePhone(telePhone);
//		tBrEnterpriseBase.setEmail(email);
//		tBrEnterpriseBase.setWebsite(website);
//		tBrEnterpriseBase.setIntro(intro);
//		tBrEnterpriseBase.setMoreData1(url);
//		tBrEnterpriseBase.setAppId(APPID_TYC);
//		tBrEnterpriseBase.setCreateTime(new Date());
//		tBrEnterpriseBase.setEid(id);
//		tBrEnterpriseBaseService.add(tBrEnterpriseBase);
//		
//		Elements rTable = docInfo.select(".baseInfo_model2017 table tbody");
//		String legalPerson = rTable.select("td").eq(0).select(".human-top a").text();
//		Elements values = rTable.select("td").eq(1).select("div.baseinfo-module-content-value");
//		
//		String registerCapital = values.eq(0).text();
//		String registerDate = values.eq(1).text();
//		String enterpriseStatus = values.eq(2).text();
//		
//		Elements rTable2 = docInfo.select("#_container_baseInfo tbody").eq(1);
//		String registerId = rTable2.select("tr").eq(0).select("td").eq(1).text();
//		String orgId = rTable2.select("tr").eq(0).select("td").eq(3).text();
//		String creditId = rTable2.select("tr").eq(1).select("td").eq(1).text();
//		String enterpriseType = rTable2.select("tr").eq(1).select("td").eq(3).text();
//		String revenueId = rTable2.select("tr").eq(2).select("td").eq(1).text();
//		String industryType = rTable2.select("tr").eq(2).select("td").eq(3).text();
//		String businessTerm = rTable2.select("tr").eq(3).select("td").eq(1).text();
//		String approveDate = rTable2.select("tr").eq(3).select("td").eq(3).text();
//		String registerAuthority = rTable2.select("tr").eq(4).select("td").eq(1).text();
//		String registerAddress = rTable2.select("tr").eq(4).select("td").eq(3).text();
//		String nameEn = rTable2.select("tr").eq(5).select("td").eq(1).text();
//		String businessScope = rTable2.select("tr").eq(6).select("td").eq(1).select(".js-full-container").text();
//		
//		//注册信息
//		TBrEnterpriseRegister tBrEnterpriseRegister = new TBrEnterpriseRegister();
//		/** 法人 */
//		tBrEnterpriseRegister.setLegalPerson(legalPerson);
//		/** 注册资本 */
//		tBrEnterpriseRegister.setRegisterCapital(registerCapital);
//		/** 注册日期 */
//		try {
//			tBrEnterpriseRegister.setRegisterDate(DateUtils.parseDate(registerDate, "yyyy-MM-dd"));
//		} catch (ParseException e) {
//			System.out.println("registerDate******err*******"+registerDate);
//			tBrEnterpriseRegister.setRegisterDate(null);
//			e.printStackTrace();
//		}
//		/** 企业状态 */
//		tBrEnterpriseRegister.setEnterpriseStatus(enterpriseStatus);
//		/** 工商注册号 */
//		tBrEnterpriseRegister.setRegisterId(registerId);
//		/** 组织机构代码 */
//		tBrEnterpriseRegister.setOrgId(orgId);
//		/** 统一信用代码 */
//		tBrEnterpriseRegister.setCreditId(creditId);
//		/** 企业类型 */
//		tBrEnterpriseRegister.setEnterpriseType(enterpriseType);
//		/** 纳税人识别号 */
//		tBrEnterpriseRegister.setRevenueId(revenueId);
//		/** 行业类型 */
//		tBrEnterpriseRegister.setIndustryType(industryType);
//		/** 营业期限 */
//		tBrEnterpriseRegister.setBusinessTerm(businessTerm);
//		/** 核准日期 */
//		try {
//			tBrEnterpriseRegister.setApproveDate(DateUtils.parseDate(approveDate, "yyyy-MM-dd"));
//		} catch (ParseException e) {
//			tBrEnterpriseRegister.setApproveDate(null);
//			e.printStackTrace();
//		}
//		/** 登记机关 */
//		tBrEnterpriseRegister.setRegisterAuthority(registerAuthority);
//		/** 注册地址 */
//		tBrEnterpriseRegister.setRegisterAddress(registerAddress);
//		/** 英文名称 */
//		tBrEnterpriseRegister.setNameEn(nameEn);
//		/** 经营范围 */
//		tBrEnterpriseRegister.setBusinessScope(businessScope);
//		tBrEnterpriseRegister.setAppId(APPID_TYC);
//		tBrEnterpriseRegister.setCreateTime(new Date());
//		tBrEnterpriseRegister.setEid(id);
//		tBrEnterpriseRegisterService.add(tBrEnterpriseRegister);
//		
//		String name = null;
//		String post = null;
//		Elements managers = docInfo.select(".staffinfo-module-container");
//		for (Element manager : managers) {
//			name = manager.select("a").eq(0).text();
//			post = manager.select("span").text();
//			//企业高层信息
//			TBrEnterpriseManager tBrEnterpriseManager = new TBrEnterpriseManager();
//			/** 姓名 */
//			tBrEnterpriseManager.setName(name);
//			/** 职位 */
//			tBrEnterpriseManager.setPost(post);
//			/** 应用id */
//			tBrEnterpriseManager.setAppId(APPID_TYC);
//			tBrEnterpriseManager.setCreateTime(new Date());
//			tBrEnterpriseManager.setEid(id);
//			tBrEnterpriseManagerService.add(tBrEnterpriseManager);
//		}
//		
//		
//		//股东结构
//		Elements shareholders = docInfo.select("#_container_holder tbody tr");
//		for (Element shareholder : shareholders) {
//			String holdersName = shareholder.select("td").eq(0).select("a").eq(0).text();
//			Byte type = 1;
//			if(holdersName.contains("公司")){
//				type = 2;
//			}
//			String moneyPercent = shareholder.select("td").eq(1).select("span").text();
//			String moneyPlan = shareholder.select("td").eq(2).select("span").text();
//			
//			TBrEnterpriseShareholder tBrEnterpriseShareholder = new TBrEnterpriseShareholder();
//			/** 股东名称 */
//			tBrEnterpriseShareholder.setName(holdersName);
//			/** 股东类型 1,自然人  2企业法人 */
//			tBrEnterpriseShareholder.setType(type);
//			/** 出资比例 */
//			tBrEnterpriseShareholder.setMoneyPercent(moneyPercent);
//			/** 认缴出资 */
//			tBrEnterpriseShareholder.setMoneyPlan(moneyPlan);
//			/** 实际出资 */
////			tBrEnterpriseShareholder.setMoneyActual(moneyActual);
//			/** 时间 */
////			tBrEnterpriseShareholder.setOperDate(operDate);
//			/** 应用id */
//			tBrEnterpriseShareholder.setAppId(APPID_TYC);
//			tBrEnterpriseShareholder.setCreateTime(new Date());
//			tBrEnterpriseShareholder.setEid(id);
//			tBrEnterpriseShareholderService.add(tBrEnterpriseShareholder);					
//		}
		
		
//		法律诉讼
		Elements lawsuits = docInfo.select("#_container_lawsuit tbody tr");
		if(CollectionUtils.isNotEmpty(lawsuits)){
			TBrEnterpriseLawsuitQuery tBrEnterpriseLawsuitQuery = new TBrEnterpriseLawsuitQuery();
			tBrEnterpriseLawsuitQuery.setEid(id);
			long queryCount = tBrEnterpriseLawsuitService.queryCount(tBrEnterpriseLawsuitQuery);
			if(queryCount==0){
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
			}else{
				System.out.println("******已有法律诉讼数据");
			}
		}
		
//		行政处罚
		Elements punishs = docInfo.select("#_container_punish tbody tr");
		if(CollectionUtils.isNotEmpty(punishs)){
			TBrEnterprisePunishQuery tBrEnterprisePunishQuery = new TBrEnterprisePunishQuery();
			tBrEnterprisePunishQuery.setEid(id);
			long queryCount = tBrEnterprisePunishService.queryCount(tBrEnterprisePunishQuery);
			if(queryCount==0){
				for (Element punish : punishs) {
					Elements tds = punish.select("td");
					String punishDate = null;
					String punishId = null;
					String punishType = null;
					String punishOffice = null;
					if(tds.size() == 5){
						punishDate = tds.eq(0).select("span").text();
						punishId = tds.eq(1).select("span").text();
						punishType = tds.eq(2).select("span").text();
						punishOffice = tds.eq(3).select("div").text();
					} 
					if(tds.size() == 3){
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
				
			}else{
				System.out.println("******已有行政处罚数据");
			}
			
		}
		TBrEnterprise tBrEnterprise = new TBrEnterprise();
		tBrEnterprise.setId(id);
		tBrEnterprise.setCreateBy(4l);
		updateByIdSelective(tBrEnterprise);
	}
	
	class inClass implements PageProcessor{
		@Override
		public void process(Page page) {
			Document doc = Jsoup.parse(page.getHtml().toString());
			addInfos2Table(id, url, doc);
		}

		@Override
		public Site getSite() {
	        return site;
		}
		
	}
	
	
	private Site site = Site.me().setDomain("www.tianyancha.com").setSleepTime(300).setUserAgent(
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31")
			// 【重要】：以下信息可以模拟登陆，信息全部来自于浏览器
			.addCookie("Hm_lpvt_e92c8d65d92d534b0fc290df538b4758", "1505894157")
			.addCookie("Hm_lvt_e92c8d65d92d534b0fc290df538b4758", "1505719793,1505782838,1505888340,1505963941")
			.addCookie("OA", "QBhiD//J2Tpz1iHeDuPmolzIgcNtSFHinLflem9g9jbNRgQIl6JpG7AE+Dnjk/jAxIegrhZshWQaeoB6Dnp4GA==")//
			.addCookie("RTYCID", "091f7ec714a64d3aabdfe92d9b14899f")
			.addCookie("_csrf", "Li7rcmsPqRbp5ksqrFTI4w==")//
			.addCookie("_csrf_bk", "eb79aeefd7fa56756b329d5f3dd08382")//
			.addCookie("aliyungf_tc", "AQAAADU0EgxiXAQAon3QPDgCD8c3uoFF")//
			.addCookie("auth_token",
					"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODI1NDEzMzM2NyIsImlhdCI6MTUwNTcyNDIwMSwiZXhwIjoxNTIxMjc2MjAxfQ.-DVeH9asRjaomClQ_u-gsXEUvGpSgz5E1WxGXIiwjp1Q5pmmxPcvsGKGvA_AH0IvS6EIR03OfW9AvaiXUcXoog")
			.addCookie("bannerFlag", "true")
			.addCookie("csrfToken", "z2WK_DROUULRS3h0tisawe4O")//
			.addCookie("ssuid", "8637891240")
			.addCookie("tyc-user-info",
					"%257B%2522token%2522%253A%2522eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODI1NDEzMzM2NyIsImlhdCI6MTUwNTcyNDIwMSwiZXhwIjoxNTIxMjc2MjAxfQ.-DVeH9asRjaomClQ_u-gsXEUvGpSgz5E1WxGXIiwjp1Q5pmmxPcvsGKGvA_AH0IvS6EIR03OfW9AvaiXUcXoog%2522%252C%2522integrity%2522%253A%25220%2525%2522%252C%2522state%2522%253A%25220%2522%252C%2522vnum%2522%253A%25220%2522%252C%2522onum%2522%253A%25220%2522%252C%2522mobile%2522%253A%252218254133367%2522%257D")
			.addCookie("uccid", "aa1d8bd62fe4bf51de2d17cb9846c331")//
	;


	
}