package com.co.example.service.taobao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.co.example.common.constant.Constant;
import com.co.example.common.utils.NumberUtil;
import com.co.example.dao.taobao.TBrTaobaoGoodsDao;
import com.co.example.entity.label.TBrLabel;
import com.co.example.entity.label.aide.TBrLabelQuery;
import com.co.example.entity.product.aide.ProductConstant;
import com.co.example.entity.taobao.TBrTaobaoGoods;
import com.co.example.entity.taobao.TBrTaobaoSort;
import com.co.example.entity.taobao.aide.TBrTaobaoGoodsQuery;
import com.co.example.entity.taobao.aide.TBrTaobaoSortQuery;
import com.co.example.service.label.TBrLabelService;
import com.co.example.service.taobao.TBrTaobaoGoodsService;
import com.co.example.service.taobao.TBrTaobaoSortService;
import com.co.example.simulateLogin.BrowserFactory;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TBrTaobaoGoodsServiceImpl extends BaseServiceImpl<TBrTaobaoGoods, Long> implements TBrTaobaoGoodsService {
	@Resource
	private TBrTaobaoGoodsDao tBrTaobaoGoodsDao;

	@Override
	protected BaseDao<TBrTaobaoGoods, Long> getBaseDao() {
		return tBrTaobaoGoodsDao;
	}

	@Resource
	TBrTaobaoSortService tBrTaobaoSortService;

	@Resource
	TBrLabelService tBrLabelService;

	//TODO 启动驱动
	WebDriver chrome ;
//	WebDriver chrome = BrowserFactory.getChrome();

	// 美妆主页
	String sortUrl = "https://mei.taobao.com/?spm=a21bo.2017.201867-main.13.3c9011d95MIOYL";
	public static final String GBK = "gbk";
	Document doc0 = null;
	Document doc = null;
	Byte b0 = 0;
	Byte b1 = 1;
	Byte b2 = 2;
	TBrTaobaoGoods tBrTaobaoGoods = null;
	String price = "";
	String sales = "";
	String productName = "";
	String producingArea = "";
	String taobaoUrl = "";

	// *************tmall****************

	String tmallSortUrl = "https://www.tmall.com";

	@Override
	public void grapTmallSort() {
		chrome = BrowserFactory.getChrome();
		chrome.get(tmallSortUrl);
		try {
			Thread.sleep(10000);
			log.info("抓取天猫分类睡眠10秒");
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String data = chrome.getPageSource();
		doc = Jsoup.parse(data);
		Elements es = doc.select(".pannel-con.j_CategoryMenuPannel").eq(3);
		Elements es2 = es.select(".hot-word");
		TBrTaobaoSort tbts = null;
		for (Element e : es2) {
			tbts = new TBrTaobaoSort();
			tbts.setCreateTime(new Date());
			tbts.setDelFlg(Constant.NO);
			tbts.setIsActive(Constant.STATUS_ACTIVE);
			tbts.setLevel(2);
			tbts.setSortName(e.text());
			tbts.setMoreData1(e.attr("href"));
			tBrTaobaoSortService.add(tbts);
		}
	}

	@Override
	public void grapTmallGoods() {
		List<TBrTaobaoSort> tbtsList = tBrTaobaoSortService.queryList();
		List<String> sortNameList = new ArrayList<String>();
		for (int i = 0; i < tbtsList.size(); i++) {
			String sort = tbtsList.get(i).getSortName();
			sortNameList.add(sort);
		}

		TBrLabelQuery tBrLabelQuery = new TBrLabelQuery();
		tBrLabelQuery.setDelFlg(b0);
		List<TBrLabel> tblList = tBrLabelService.queryList(tBrLabelQuery);
		for (int i = 0; i < tblList.size(); i++) {
			TBrLabel tbl = tblList.get(i);
			String labelName = tbl.getName();
			Long labelId = tbl.getId();
			if (sortNameList.contains(labelName)) {
				log.info("sort表已经包含该名称，不必再补充：" + labelName);
				tBrLabelQuery = new TBrLabelQuery();
				tBrLabelQuery.setDelFlg(b2);
				tBrLabelQuery.setId(labelId);
				tBrLabelService.updateByIdSelective(tBrLabelQuery);
				continue;
			}
			String url = ProductConstant.TMALL_PRODUCT_SEARCH_URL + labelName;
			try {
				log.info("抓取数据：" + i + "--" + labelId + "--" + labelName);
				if (StringUtil.isNotBlank(url)) {
					chrome = BrowserFactory.getChrome();
					log.info("url===" + url);
					chrome.get(url);
					String data = chrome.getPageSource();
					doc0 = Jsoup.parse(data);
					String url2 = doc0.select(".fSort").eq(3).attr("href");
					url2 = "https://list.tmall.com/search_product.htm" + url2;
					log.info("url2===" + url2);
					chrome.get(url2);
					String data2 = chrome.getPageSource();
					doc = Jsoup.parse(data2);
					if (doc.text().contains("扫码登录更安全")) {
						log.info("出现登录验证，睡眠30s");
						Thread.sleep(30000);
					}
					String title = doc.select("title").text();
					if (StringUtils.isBlank(title) || StringUtils.equals(title, "302 Found")
							|| title.contains("security")) {
						chrome.close();
						log.info("出现安全验证，睡眠30s");
						Thread.sleep(30000);
						continue;
					}
					Elements goodsList1 = doc.select("#J_ItemList");
					Elements goodsList = goodsList1.select("div.product");
					int size = goodsList.size();
					log.info("goodsList：" + size);
					if (size > 0) {
						if (size >= 20) {
							size = 20;
						}
						for (int j = 0; j < size; j++) {
							Element element = goodsList.get(j);
							price = element.select(".productPrice").text().replace("¥", "");
							productName = element.select(".productTitle a").text();
							int salesTmp = 0;
							sales = element.select(".productStatus").text();
							sales = sales.split("笔")[0];
							// 月成交 7.2万笔 评价 51万
							log.info("sales：" + sales);
							String salesStrTmp = NumberUtil.getNumberFromString(sales);
							if (sales.contains("万")) {
								salesTmp = (int) (Float.parseFloat(salesStrTmp) * 10000);
							} else {
								salesTmp = Integer.parseInt(salesStrTmp);
							}
							taobaoUrl = element.select(".productTitle a").attr("href");
							tBrTaobaoGoods = new TBrTaobaoGoods();
							tBrTaobaoGoods.setCreateTime(new Date());
							tBrTaobaoGoods.setDelFlg(Constant.NO);
							tBrTaobaoGoods.setTaobaoUrl(taobaoUrl);
							tBrTaobaoGoods.setProductName(productName);
							tBrTaobaoGoods.setTaobaoPrice(BigDecimal.valueOf(Double.parseDouble(price)));
							tBrTaobaoGoods.setProductSortId(labelId + "");
							tBrTaobaoGoods.setProductSortName(labelName);
							tBrTaobaoGoods.setSales(salesTmp);
							tBrTaobaoGoods.setSource(b1);
							tBrTaobaoGoodsDao.insert(tBrTaobaoGoods);
							tBrLabelQuery = new TBrLabelQuery();
							tBrLabelQuery.setDelFlg(b2);
							tBrLabelQuery.setId(labelId);
							tBrLabelService.updateByIdSelective(tBrLabelQuery);
						}
					} else {
						log.info("goodsList为0");
					}
				} else {
					log.info("抓取数据：" + i + "--" + labelId + "--" + labelName + "--url为空");
				}
			} catch (Exception e) {
				chrome.close();
				log.info("抓取数据：" + i + "--" + labelId + "--" + labelName + "--Error");
				e.printStackTrace();
				break;
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				chrome.close();
				e.printStackTrace();
			}
			chrome.close();
		}

	}

	// **************taobao***************
	@Override
	public void grapTaobaoSort() {
		try {
			chrome = BrowserFactory.getChrome();
			chrome.get(sortUrl);
			String data = chrome.getPageSource();
			doc = Jsoup.parse(data);
			// Elements es = doc.select(".J_old_data");
			Elements es = doc.select(".J_ext_data");
			TBrTaobaoSort tbts = null;
			for (Element e : es) {
				String str = e.text();
				JSONArray jsonArray = JSON.parseArray(str);
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jo = jsonArray.getJSONObject(i);
					String sortName = jo.getString("cat_name");
					String moreData1 = jo.getString("cat_href");
					tbts = new TBrTaobaoSort();
					tbts.setCreateTime(new Date());
					tbts.setDelFlg(Constant.NO);
					tbts.setIsActive(Constant.STATUS_ACTIVE);
					tbts.setLevel(2);
					tbts.setSortName(sortName);
					tbts.setMoreData1(moreData1);
					tBrTaobaoSortService.add(tbts);
				}
				log.info("抓取分类：" + str);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void grapTaobaoGoods() {
		TBrTaobaoSortQuery tBrTaobaoSortQuery = new TBrTaobaoSortQuery();
		tBrTaobaoSortQuery.setDelFlg(b0);
		List<TBrTaobaoSort> tbtsList = tBrTaobaoSortService.queryList(tBrTaobaoSortQuery);
		for (int i = 0; i < tbtsList.size(); i++) {
			TBrTaobaoSort tbts = tbtsList.get(i);
			String url = tbts.getMoreData1();
			String sortName = tbts.getSortName();
			Long sortId = tbts.getId();
			try {
				log.info("抓取数据：" + i + "--" + sortId + "--" + sortName);
				if (StringUtil.isNotBlank(url)) {
					url = "https:" + url + "&sort=sale-desc";
					chrome = BrowserFactory.getChrome();
					chrome.get(url);
					String data = chrome.getPageSource();
					doc = Jsoup.parse(data);
					if (doc.text().contains("扫码登录更安全")) {
						log.info("出现登录验证，睡眠30s");
						Thread.sleep(30000);
					}
					String title = doc.select("title").text();
					if (StringUtils.isBlank(title) || StringUtils.equals(title, "302 Found")
							|| title.contains("security")) {
						chrome.close();
						log.info("出现安全验证，睡眠30s");
						Thread.sleep(30000);
						continue;
					}
					Elements goodsElements = doc.select("#listsrp-itemlist");
					Elements goodsList = goodsElements.select(".item.J_MouserOnverReq");
					int size = goodsList.size();
					log.info("goodsList：" + size);
					if (size > 0) {
						if (size >= 20) {
							size = 20;
						}
						for (int j = 0; j < size; j++) {
							Element element = goodsList.get(j);
							price = element.select(".price.g_price.g_price-highlight").text().replace("¥", "");
							sales = element.select(".deal-cnt").text().replace("人收货", "");
							productName = element.select(".J_ItemPic.img").attr("alt");
							producingArea = element.select(".location").text();
							taobaoUrl = element.select(".pic-link.J_ClickStat.J_ItemPicA").attr("href");
							tBrTaobaoGoods = new TBrTaobaoGoods();
							tBrTaobaoGoods.setCreateTime(new Date());
							tBrTaobaoGoods.setDelFlg(Constant.NO);
							tBrTaobaoGoods.setIsActive(Constant.STATUS_ACTIVE);
							tBrTaobaoGoods.setProducingArea(producingArea);
							tBrTaobaoGoods.setTaobaoUrl(taobaoUrl);
							tBrTaobaoGoods.setProductName(productName);
							tBrTaobaoGoods.setTaobaoPrice(BigDecimal.valueOf(Double.parseDouble(price)));
							tBrTaobaoGoods.setProductSortId(sortId + "");
							tBrTaobaoGoods.setProductSortName(sortName);
							tBrTaobaoGoods.setSales(Integer.parseInt(sales));
							tBrTaobaoGoods.setSource(b1);
							tBrTaobaoGoodsDao.insert(tBrTaobaoGoods);
							tBrTaobaoSortQuery = new TBrTaobaoSortQuery();
							tBrTaobaoSortQuery.setDelFlg(b2);
							tBrTaobaoSortQuery.setId(sortId);
							tBrTaobaoSortService.updateByIdSelective(tBrTaobaoSortQuery);
						}
					} else {
						log.info("goodsList为0");
					}
				} else {
					log.info("抓取数据：" + i + "--" + sortId + "--" + sortName + "--url为空");
				}
			} catch (Exception e) {
				chrome.close();
				log.info("抓取数据：" + i + "--" + sortId + "--" + sortName + "--Error");
				e.printStackTrace();
				break;
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				chrome.close();
				e.printStackTrace();
			}
			// 清理cookies
			chrome.manage().deleteAllCookies();
			chrome.close();
		}
	}

	int salesTmp = 0;

	@Override
	public void grapTmallGoodsSells() {

		TBrTaobaoGoodsQuery tBrTaobaoGoodsQuery = new TBrTaobaoGoodsQuery();
		tBrTaobaoGoodsQuery.setDelFlg(b0);
		List<TBrTaobaoGoods> tbtgList = queryList(tBrTaobaoGoodsQuery);

		for (int i = 0; i < tbtgList.size(); i++) {
			TBrTaobaoGoods tbtg = tbtgList.get(i);
			String url = tbtg.getTaobaoUrl();
			if (!url.contains("https:")) {
				url = "https:" + url;
			}
			try {
				log.info("抓取数据：" + i + "--" + tbtg.getProductName());
				if (StringUtil.isNotBlank(url)) {
					// chrome = BrowserFactory.getChrome();
					log.info("url===" + url);
					chrome.get(url);
					// Thread.sleep(1000);
					String data = chrome.getPageSource();
					doc = Jsoup.parse(data);
					if (doc.text().contains("手机扫码，安全登录")
							|| doc.text().contains("login.taobao.com/member/login.jhtml")) {
						log.info("出现登录验证，睡眠30s");
						Thread.sleep(30000);
					}
					String title = doc.select("title").text();
					if (StringUtils.isBlank(title) || StringUtils.equals(title, "302 Found")
							|| title.contains("security")) {
						// chrome.close();
						log.info("出现安全验证，睡眠30s");
						Thread.sleep(30000);
						continue;
					}

					Boolean flg = true;
					tBrTaobaoGoods = new TBrTaobaoGoods();
					Elements soldout = doc.select(".sold-out-tit");
					if (soldout != null && soldout.text().equals("此商品已下架")) {
						tBrTaobaoGoods.setMoreData1("已下架");
						log.info("已下架");
						flg = false;
					}	
					
					if(flg){
						Elements errorPage = doc.select(".errorPage .errorDetail");
						if (errorPage != null && errorPage.text().contains("很抱歉，您查看的商品找不到了！")) {
							tBrTaobaoGoods.setMoreData1("找不到");
							log.info("找不到");
							flg = false;
						} 							
					}
					
					if(flg){
						Elements J_LinkBuy = doc.select("#J_LinkBuy");
						if (J_LinkBuy != null && J_LinkBuy.text().contains("定金")) {
							tBrTaobaoGoods.setMoreData1("预售");
							log.info("预售");
							flg = false;
						} 							
					}
										
					
					if(flg){
						Elements es = doc.select(".tm-ind-item.tm-ind-sellCount .tm-count");
						sales = es.text();
						// 2.0万+
						log.info("sales：" + sales);
						if (sales == null || sales.equals("")) {
							// chrome.close();
							continue;
						}
						String salesStrTmp = NumberUtil.getNumberFromString(sales);
						if (sales.contains("万")) {
							salesTmp = (int) (Float.parseFloat(salesStrTmp) * 10000);
						} else {
							salesTmp = Integer.parseInt(salesStrTmp);
						}
						tBrTaobaoGoods.setMoreData1(salesTmp + "");
					}
					

					tBrTaobaoGoods.setId(tbtg.getId());
					tBrTaobaoGoods.setDelFlg(b1);
					updateByIdSelective(tBrTaobaoGoods);
				} else {
					log.info("抓取数据：" + i + "--" + tbtg.getProductName() + "--url为空");
				}
			} catch (Exception e) {
				chrome.close();
				log.info("抓取数据：" + i + "--" + tbtg.getProductName() + "--Error");
				e.printStackTrace();
				break;
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// chrome.close();
				e.printStackTrace();
			}
			// chrome.close();
		}

	}

}