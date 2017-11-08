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
public class DataTest3GetEnterpriseInfo {

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
			.addCookie("Hm_lpvt_e92c8d65d92d534b0fc290df538b4758", "1509758097")
			.addCookie("Hm_lvt_e92c8d65d92d534b0fc290df538b4758", "1508738490,1508982995,1509700252,1509758095")
			.addCookie("OA", "HuYx+NCtVRzx+nf9EXwoY8yiDETGct3sUaThPQXCoTXdQvFqDdfCfxW804C4uf6i")//
			.addCookie("RTYCID", "ea18316aa1b449c4830c52cf48e60ff9")
			.addCookie("TYCID", "e2f2c7a0b31b11e79cd44b77fbc8ef1e")
			.addCookie("_csrf", "v2OvD7IKS/oHToKBvWlmVw==")//
			.addCookie("_csrf_bk", "d3998fa5c5c424f91bc06eec4f752758")//
			.addCookie("aliyungf_tc", "AQAAAJkt3AJGbQgA+hHQPP69LsrHV142")//
			.addCookie("auth_token",
					"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODI1NDEzMzM2NyIsImlhdCI6MTUwOTcwMDM1MCwiZXhwIjoxNTI1MjUyMzUwfQ.Q-eKAzDTbP2MXiuAD7HM0xwS22CEY--_awmt23JQfXlxROxIa54uqFnbaao6ZNVSKbdBuJCjb-l5XpfOVYtOHw")
			.addCookie("bannerFlag", "true")
			.addCookie("csrfToken", "OfvOqaw6c4sR0yJyggl14Xoq")//
			.addCookie("ssuid", "136256036")
			.addCookie("tyc-user-info",
					"%257B%2522token%2522%253A%2522eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODI1NDEzMzM2NyIsImlhdCI6MTUwOTcwMDM1MCwiZXhwIjoxNTI1MjUyMzUwfQ.Q-eKAzDTbP2MXiuAD7HM0xwS22CEY--_awmt23JQfXlxROxIa54uqFnbaao6ZNVSKbdBuJCjb-l5XpfOVYtOHw%2522%252C%2522integrity%2522%253A%25220%2525%2522%252C%2522state%2522%253A%25220%2522%252C%2522vnum%2522%253A%25220%2522%252C%2522onum%2522%253A%25220%2522%252C%2522mobile%2522%253A%252218254133367%2522%257D")
			.addCookie("uccid", "868866fdf6ec3c40fb6943dc1dccff80")//
	;


//	Hm_lpvt_e92c8d65d92d534b0fc290df538b4758	1509758097	.tianyancha.com	/	Session	50				
//	Hm_lvt_e92c8d65d92d534b0fc290df538b4758	1508738490,1508982995,1509700252,1509758095	.tianyancha.com	/	2018-11-04T01:14:56.000Z	82				
//	OA	HuYx+NCtVRzx+nf9EXwoY8yiDETGct3sUaThPQXCoTXdQvFqDdfCfxW804C4uf6i	.tianyancha.com	/	Session	66		✓		
//	RTYCID	ea18316aa1b449c4830c52cf48e60ff9	.tianyancha.com	/	2017-11-05T09:33:55.351Z	38				
//	TYCID	e2f2c7a0b31b11e79cd44b77fbc8ef1e	.tianyancha.com	/	2019-10-17T09:15:48.251Z	37	✓			
//	_csrf	v2OvD7IKS/oHToKBvWlmVw==	.tianyancha.com	/	Session	29		✓		
//	_csrf_bk	d3998fa5c5c424f91bc06eec4f752758	.tianyancha.com	/	Session	40		✓		
//	aliyungf_tc	AQAAAJkt3AJGbQgA+hHQPP69LsrHV142	www.tianyancha.com	/	Session	43	✓			
//	auth_token	eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODI1NDEzMzM2NyIsImlhdCI6MTUwOTcwMDM1MCwiZXhwIjoxNTI1MjUyMzUwfQ.Q-eKAzDTbP2MXiuAD7HM0xwS22CEY--_awmt23JQfXlxROxIa54uqFnbaao6ZNVSKbdBuJCjb-l5XpfOVYtOHw	.tianyancha.com	/	2017-11-10T09:11:24.000Z	192				
//	csrfToken	OfvOqaw6c4sR0yJyggl14Xoq	www.tianyancha.com	/	Session	33		✓		
//	ssuid	136256036	.tianyancha.com	/	2038-01-01T09:15:49.000Z	14				
//	tyc-user-info	%257B%2522token%2522%253A%2522eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODI1NDEzMzM2NyIsImlhdCI6MTUwOTcwMDM1MCwiZXhwIjoxNTI1MjUyMzUwfQ.Q-eKAzDTbP2MXiuAD7HM0xwS22CEY--_awmt23JQfXlxROxIa54uqFnbaao6ZNVSKbdBuJCjb-l5XpfOVYtOHw%2522%252C%2522integrity%2522%253A%25220%2525%2522%252C%2522state%2522%253A%25220%2522%252C%2522vnum%2522%253A%25220%2522%252C%2522onum%2522%253A%25220%2522%252C%2522mobile%2522%253A%252218254133367%2522%257D	.tianyancha.com	/	2017-11-10T09:11:24.000Z	433				
//	uccid	868866fdf6ec3c40fb6943dc1dccff80	.tianyancha.com	/	2019-10-17T09:15:48.251Z	37	✓			
	

	
	
	@Test
	public void test2() {
		TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
		tBrEnterpriseQuery.setCreateBy(0l);
		List<TBrEnterprise> enterprises = tBrEnterpriseService.queryList(tBrEnterpriseQuery);
		int size = enterprises.size();
		TBrEnterprise tBrEnterprise = null;
		log.info("***需要获取的企业数据数量***" + size);
		//从文件获取代理ip
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
//				从阿里云市场第三方代理获得代理ip 效果一般				
//				proxyArr = getProxy();
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
	
	//从企业base表找到企业详情url直接请求数据  ，可以用于法律诉讼，行政处罚等数据的增量处理
	public void test1() {
		TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
		tBrEnterpriseQuery.setCreateBy(0l);
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
	@Test
	public void testTmp() {
		tBrEnterpriseService.addMoreEnterpriseInfo("https://www.tianyancha.com/company/80223669", 2320609770061824l);
		log.info("全部抓取完毕");
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
	
	//****************************************************************************
	
	private Proxy[] getProxy() {
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

	
	//代理测试，从文件读取
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
	

	public static void main(String[] args) {
		// for (int i = 0; i < 10; i++) {
		// double random = Math.random();
		// random = random*10000+10000;
		// System.out.println(random);
		// }

	}

	
	
	
}