package com.co.example;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.common.utils.proxy.IPMessage;
import com.co.example.common.utils.proxy.IPUtils;
import com.co.example.entity.enterprise.TBrEnterpriseBase;
import com.co.example.entity.enterprise.aide.TBrEnterpriseBaseQuery;
import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.aide.TBrEnterpriseQuery;
import com.co.example.service.enterprise.TBrEnterpriseBaseService;
import com.co.example.service.product.TBrEnterpriseService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest11 {

	@Autowired
	TBrEnterpriseService tBrEnterpriseService;
	@Autowired
	TBrEnterpriseBaseService tBrEnterpriseBaseService;

	String enterpriseName;
	String url = "https://www.tianyancha.com/search?key=";
	// String url = "https://www.baidu.com";
	Long id = 0l;

	Proxy[] proxyArr = null;

	private Site site = Site.me().setDomain("www.tianyancha.com").setSleepTime(300).setUserAgent(
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31")
			// 【重要】：以下信息可以模拟登陆，信息全部来自于浏览器
			.addCookie("Hm_lpvt_e92c8d65d92d534b0fc290df538b4758", "1505894157")
			.addCookie("Hm_lvt_e92c8d65d92d534b0fc290df538b4758", "1505719793,1505782838,1505888340,1505963941")
			.addCookie("OA", "QBhiD//J2Tpz1iHeDuPmolzIgcNtSFHinLflem9g9jbNRgQIl6JpG7AE+Dnjk/jAxIegrhZshWQaeoB6Dnp4GA==")//
			.addCookie("RTYCID", "091f7ec714a64d3aabdfe92d9b14899f")
			.addCookie("TYCID", "2d87aa509c4d11e7b5e5093d3ffce845")
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

	
	
//	vt_e92c8d65d92d534b0fc290df538b4758	1505972197	.tianyancha.com	/	Session	50				
//	Hm_lvt_e92c8d65d92d534b0fc290df538b4758	1505719793,1505782838,1505888340,1505963941	.tianyancha.com	/	2018-09-21T05:36:36.000Z	82				
//	OA	QBhiD//J2Tpz1iHeDuPmolzIgcNtSFHinLflem9g9jbNRgQIl6JpG7AE+Dnjk/jAxIegrhZshWQaeoB6Dnp4GA==	.tianyancha.com	/	Session	90		✓		
//	RTYCID	091f7ec714a64d3aabdfe92d9b14899f	.tianyancha.com	/	2017-09-21T07:17:16.415Z	38				
//	TYCID	2d87aa509c4d11e7b5e5093d3ffce845	.tianyancha.com	/	2019-09-18T08:40:48.855Z	37	✓			
//	_csrf	Li7rcmsPqRbp5ksqrFTI4w==	.tianyancha.com	/	Session	29		✓		
//	_csrf_bk	eb79aeefd7fa56756b329d5f3dd08382	.tianyancha.com	/	Session	40		✓		
//	_utm	79f87d71d7c84829bc9468dac71f2142	www.tianyancha.com	/	Session	36				
//	aliyungf_tc	AQAAADU0EgxiXAQAon3QPDgCD8c3uoFF	www.tianyancha.com	/	Session	43	✓			
//	auth_token	eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODI1NDEzMzM2NyIsImlhdCI6MTUwNTcyNDIwMSwiZXhwIjoxNTIxMjc2MjAxfQ.-DVeH9asRjaomClQ_u-gsXEUvGpSgz5E1WxGXIiwjp1Q5pmmxPcvsGKGvA_AH0IvS6EIR03OfW9AvaiXUcXoog			
//	bannerFlag	true	www.tianyancha.com	/	Session	14				
//	csrfToken	z2WK_DROUULRS3h0tisawe4O	www.tianyancha.com	/	Session	33		✓		
//	ssuid	8637891240	.tianyancha.com	/	2038-01-01T08:40:49.000Z	15				
//	token	7df2ea7fd70b4b268d7c303d7b00ef8f	www.tianyancha.com	/	Session	37				
//	tyc-user-info	%257B%2522token%2522%253A%2522eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODI1NDEzMzM2NyIsImlhdCI6MTUwNTcyNDIwMSwiZXhwIjoxNTIxMjc2MjAxfQ.-DVeH9asRjaomClQ_u-gsXEUvGpSgz5E1WxGXIiwjp1Q5pmmxPcvsGKGvA_AH0IvS6EIR03OfW9AvaiXUcXoog%2522%252C%2522integrity%2522%253A%25220%2525%2522%252C%2522state%2522%253A%25220%2522%252C%2522vnum%2522%253A%25220%2522%252C%2522onum%2522%253A%25220%2522%252C%2522mobile%2522%253A%252218254133367%2522%257D
//	uccid	aa1d8bd62fe4bf51de2d17cb9846c331	.tianyancha.com	/	2019-09-18T08:40:48.855Z	37	✓			

	

	
	public void test2() {
		TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
		tBrEnterpriseQuery.setCreateBy(0l);
		List<TBrEnterprise> enterprises = tBrEnterpriseService.queryList(tBrEnterpriseQuery);
		int size = enterprises.size();
		TBrEnterprise tBrEnterprise = null;
		log.info("***需要获取的企业数据数量***" + size);
		// List<String> ips = getIps();
				// int iPsize = ips.size();
				// proxyArr=new Proxy[iPsize];
				// String str = null;
				// for (int i =0 ; i<iPsize ; i++ ) {
				// str = ips.get(i);
				// String[] split = str.split(":");
				// proxyArr[i]= new Proxy(split[0],Integer.parseInt(split[1]));
				// }
		if (CollectionUtils.isNotEmpty(enterprises)) {
			int index = size;
			for (int i = 0; i < size; i++) {
				tBrEnterprise = enterprises.get(i);
				enterpriseName = tBrEnterprise.getEnterpriseName();
				id = tBrEnterprise.getId();
				String newurl = url + enterpriseName;
				
//				proxyArr = getProxy("");
//				HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
//				httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(proxyArr));
				Spider spider = Spider.create(new DataTest33());
//				spider.setDownloader(httpClientDownloader);
				spider.addUrl(newurl).run();
				System.out.println("******" + i+"*****"+index--);
//				try {
//					Thread.sleep(123);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
		}
		log.info("全部抓取完毕");
		
	}
	
	@Test
	public void test1() {

		TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
		tBrEnterpriseQuery.setCreateBy(3l);
		List<TBrEnterprise> enterprises = tBrEnterpriseService.queryList(tBrEnterpriseQuery);
		int size = enterprises.size();
		TBrEnterprise tBrEnterprise = null;
		log.info("***需要获取的企业数据数量***" + size);

		TBrEnterpriseBaseQuery tBrEnterpriseBaseQuery  = null;
		if (CollectionUtils.isNotEmpty(enterprises)) {
			int index = size;
			for (int i = 0; i < size; i++) {
				tBrEnterprise = enterprises.get(i);
				id = tBrEnterprise.getId();
				tBrEnterpriseBaseQuery = new TBrEnterpriseBaseQuery();
				tBrEnterpriseBaseQuery.setEid(id);
				TBrEnterpriseBase base = tBrEnterpriseBaseService.queryOne(tBrEnterpriseBaseQuery);
				String url = base.getMoreData1();
				tBrEnterpriseService.addMoreEnterpriseInfo(url, id);
				System.out.println("******" + i+"*****"+index--);
			}
		}
		log.info("全部抓取完毕");

	}

	private Proxy[] getProxy(String url) {
		List<IPMessage> ipsOld = null;
		List<IPMessage> ipsNew = null;
		Proxy[] result = null;
		if(proxyArr == null){
			ipsNew = IPUtils.getIpsFromAliyunMarket();
			 IPUtils.IPIsable(ipsNew);
			 result = list2Array(ipsNew);
		} else{
			ipsOld = array2List(proxyArr);
			IPUtils.IPIsable(ipsOld);
			if(ipsOld.size()>10){
				result = list2Array(ipsOld);
			}else{			
				ipsNew = IPUtils.getIpsFromAliyunMarket();
				IPUtils.IPIsable(ipsNew);
				ipsOld.addAll(ipsNew);
				result = list2Array(ipsOld);
			}
		}
		return result;
	}

	
	
	List<IPMessage> array2List(Proxy[] proxys){
		IPMessage ipMessage = null;
		List<IPMessage> ipList= Lists.newArrayList();
		Proxy proxy  = null;
		for (int i = 0; i < proxys.length; i++) {
			ipMessage = new IPMessage();
			proxy = proxys[i];
			ipMessage.setIPAddress(proxy.getHost());
			ipMessage.setIPPort(proxy.getPort()+"");
			ipList.add(ipMessage);
		}
		return ipList;
	}
	
	Proxy[] list2Array(List<IPMessage> IPMessages){
		int size = IPMessages.size();
		IPMessage ipMessage = null;
		Proxy[] proxys = new Proxy[size];
		for (int i = 0; i < size; i++) {
			ipMessage = IPMessages.get(i);
			proxys[i] = new Proxy(ipMessage.getIPAddress(), Integer.parseInt(ipMessage.getIPPort()));
		}
		return proxys;
	}
	
	
	
	class DataTest33 implements PageProcessor {
		@Override
		public void process(Page page) {
			Document doc = Jsoup.parse(page.getHtml().toString());
			// 调用service 保存其他表，并把该表中的 CreateBy update为1 注意在一个事务
			tBrEnterpriseService.addMoreEnterpriseInfo(doc, enterpriseName, id, proxyArr);
			System.out.println("本次抓取完成");
		}

		@Override
		public Site getSite() {
			return site;
		}

	}

	private List<String> getIps() {
		File file = new File("D:\\Workspaces2\\package\\management\\src\\test\\resources\\ip-proxy");
		try {
			@SuppressWarnings("deprecation")
			List<String> lines = FileUtils.readLines(file);
			return lines;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		// for (int i = 0; i < 10; i++) {
		// double random = Math.random();
		// random = random*10000+10000;
		// System.out.println(random);
		// }

	}

	
	
	
}