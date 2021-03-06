package com.co.example.service.product.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.co.example.aide.JdData;
import com.co.example.aide.TmallData;
import com.co.example.common.constant.Constant;
import com.co.example.common.utils.HttpUtils;
import com.co.example.common.utils.JsoupUtil;
import com.co.example.dao.product.TBrProductSpecDao;
import com.co.example.entity.comment.TBrProductCommentStatistics;
import com.co.example.entity.comment.aide.Comment;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.TBrProductImage;
import com.co.example.entity.product.TBrProductSpec;
import com.co.example.entity.product.aide.ProductConstant;
import com.co.example.entity.product.aide.TBrProductImageQuery;
import com.co.example.entity.product.aide.TBrProductSpecQuery;
import com.co.example.entity.spec.TBrSpecKey;
import com.co.example.entity.spec.TBrSpecValue;
import com.co.example.entity.spec.aide.TBrSpecValueQuery;
import com.co.example.service.comment.TBrProductCommentStatisticsService;
import com.co.example.service.product.TBrProductImageService;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.product.TBrProductSpecService;
import com.co.example.service.spec.TBrSpecKeyService;
import com.co.example.service.spec.TBrSpecValueService;
import com.co.example.utils.BaseDataUtil;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.entity.BaseEntity;
import com.github.moncat.common.service.BaseServiceImpl;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TBrProductSpecServiceImpl extends BaseServiceImpl<TBrProductSpec, Long> implements TBrProductSpecService {
	@Resource
	private TBrProductSpecDao tBrProductSpecDao;

	@Override
	protected BaseDao<TBrProductSpec, Long> getBaseDao() {
		return tBrProductSpecDao;
	}

	@Autowired
	TBrProductService tBrProductService;

	@Autowired
	TBrProductImageService tBrProductImageService;

	@Autowired
	TBrSpecKeyService tBrSpecKeyService;

	@Autowired
	TBrSpecValueService tBrSpecValueService;

	@Autowired
	TBrProductCommentStatisticsService tBrProductCommentStatisticsService;

	public static final String UTF8 = "UFT-8";
	public static final String GBK = "gbk";

	Long productId;
	String encode = "utf-8";
	String skuUrl;
	String skuId;
	String productName;
	Document skuDoc;
	TBrProduct tBrProduct;
	Byte sourceType;
	List<TBrSpecKey> tbrSpecKeyList;
	String sellerId;
	int count = 0;
	int count2 = 0;

	@Override
	public int addData(TBrProduct tBrProduct, Byte sourceType, List<TBrSpecKey> tbrSpecKeyList, WebDriver chrome) {
		this.tBrProduct = tBrProduct;
		this.productId = tBrProduct.getId();
		this.sourceType = sourceType;
		this.productName = StringEscapeUtils.unescapeHtml4(tBrProduct.getProductName().replace(" ", ""));
		this.tbrSpecKeyList = tbrSpecKeyList;
		String searchUrl = null;
		// 抓取京东数据
		if (sourceType == ProductConstant.PRODUCT_SOURCE_JD) {
			searchUrl = ProductConstant.JD_PRODUCT_SEARCH_URL + productName;
			try {
				Document doc = JsoupUtil.getDoc(searchUrl, encode);
				if (doc == null) {
					return 0;
				}
				;
				Elements ns = doc.select(".notice-search");
				if (CollectionUtils.isNotEmpty(ns)) {
					// 并未找到该产品数据
					log.info("***未查询到该商品***" + productName);
					TBrProduct product = new TBrProduct();
					product.setId(productId);
					product.setJdUrl(searchUrl);
					tBrProductService.updateByIdSelective(product);
					return 4;
				} else {
					Elements goodsList = doc.select("#J_goodsList");
					if (goodsList.size() > 0) {
						Elements good = goodsList.select("ul").eq(0).select("li").eq(0);
						this.skuId = good.attr("data-sku");
						this.skuUrl = "http://item.jd.com/" + skuId + ".html";
						this.skuDoc = JsoupUtil.getDoc(skuUrl, GBK);
						if (skuDoc == null) {
							return 0;
						}
						;

						// 获得图片
						getJDImages();
						// 获得规格参数
						getJDSpecData();
						// 获得聊天统计
						getJDCommentStatisticsData();
						// 获得价格，无销量,url
						getJDBaseData(doc);
						log.info("抓取京东完毕：" + productName + "***" + productId);
					} else {
						log.info("未抓取到京东数据!!!：" + productName);
					}
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
				return 0;
			}
			// 抓取天猫数据
		} else if (sourceType == ProductConstant.PRODUCT_SOURCE_TMALL) {
			searchUrl = ProductConstant.TMALL_PRODUCT_SEARCH_URL + productName;
			this.encode = "gbk";
			Document doc = null;
			try {
				try {
					chrome.get(searchUrl);
				} catch (Exception e1) {
					log.info("请求超时");
					return 5;
				}
				String data = chrome.getPageSource();
				doc = Jsoup.parse(data);

				String title = doc.select("title").text();
				if (StringUtils.isBlank(title) || StringUtils.equals(title, "302 Found")
						|| StringUtils.equals(title, "SecurityMatrix")) {
					Thread.sleep(100000);
					log.info("未抓取到天猫数据!!!：" + productName);
					log.info("睡眠100s");
					// count2++;
					// if(count2>2){
					// count2 = 0;
					// return 5;
					// }
					return 0;
				}
				Elements ns = doc.select(".searchTip");
				if (CollectionUtils.isNotEmpty(ns)) {
					// 并未找到该产品数据
					log.info("***未查询到该商品***" + productName);
					TBrProduct product = new TBrProduct();
					product.setId(productId);
					product.setTmallUrl(searchUrl);
					tBrProductService.updateByIdSelective(product);
					return 4;
				} else {
					Elements goodsList = doc.select("#J_ItemList");
					if (goodsList.size() > 0) {
						Elements good = goodsList.select("div.product").eq(0);
						this.skuId = good.attr("data-id");
						String tmallSkuUrl = good.select(".productImg-wrap a").attr("href");
						this.skuUrl = "https://detail.tmall.com/item.htm?id=" + skuId;
						chrome.get("https:" + tmallSkuUrl);
						// Thread.sleep(1000);
						String pageSource = chrome.getPageSource();
						this.skuDoc = Jsoup.parse(pageSource);
						if (skuDoc == null) {
							return 0;
						}
						;
						try {
							String skuStr = skuDoc.toString();
							skuStr = skuStr.substring(skuStr.indexOf("sellerId"));
							skuStr = skuStr.substring(skuStr.indexOf("=") + 1, skuStr.indexOf("&amp;"));
							this.sellerId = skuStr.replace(":", "").replace("\"", "");
						} catch (Exception e) {
							log.info("***获取sellerId失败***");
						}
						// 获得图片
						getTmallImages();
						// 获得规格参数
						getTmallSpecData();
						// 获得聊天统计
						getTmallCommentStatisticsData();
						// 获得价格,url
						int status = getTmallBaseData(chrome);
						if (status == 5) {
							log.info("***获取价格，销量，请求超时***" + productName);
							return 0;
						}

						log.info("抓取天猫完毕：" + productName + "***" + productId);
					} else {
						log.info("未抓取到天猫数据!!!2：" + productName);

						if (productName.startsWith("#")) {
							TBrProduct product = new TBrProduct();
							product.setId(productId);
							product.setTmallUrl(searchUrl);
							tBrProductService.updateByIdSelective(product);
						}

						String currentUrl = chrome.getCurrentUrl();
						if (currentUrl.indexOf("login.tmall") > -1) {
							log.info("需要重新登录***" + productName);
							Thread.sleep(25000);
							String currentUrl2 = chrome.getCurrentUrl();
							if (currentUrl2.indexOf("login.tmall") > -1) {
								log.info("*手动登录失败**");
								return 5;
							}
							return 0;
						}
						if (currentUrl.indexOf("error") > -1) {
							TBrProduct product1 = new TBrProduct();
							product1.setId(productId);
							product1.setTmallUrl(searchUrl);
							tBrProductService.updateByIdSelective(product1);
							log.info("出现错误页面***" + productName);
							return 0;
						}
						count++;
						if (count > 10) {
							log.info("***请求被拒绝" + count + "***" + productName);
							count = 0;
							return 5;
						}
						return 0;
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}

		}

		return 0;
	}

	private void getTmallImages() {
		Elements images = skuDoc.select("#J_UlThumb li img");
		saveImages(images);
	}

	private void getTmallSpecData() {
		Elements specs = skuDoc.select("#J_AttrUL li");
		Long keyId = 0l;
		String keyName = null;
		String valueName = null;

		String liText = null;
		String[] split = null;
		String keyText = null;
		// 循环抓取li的每一行
		int size = specs.size();
		for (int i = 0; i < size; i++) {
			liText = specs.eq(i).text().replace(" ", "").replace("：", "");
			split = liText.split(":");

			if (split.length == 2) {
				keyText = split[0];
				valueName = split[1];
				// 循环key列表
				for (TBrSpecKey key : tbrSpecKeyList) {
					// 当前dt包含keyName时，操作该规格参数。
					keyName = key.getKeyName();
					if (StringUtils.isNotBlank(keyText) && keyText.contains(keyName)) {
						keyId = key.getId();
						// 查到当前dt对应的dd的值。
						saveProductSpec(keyId, keyName, valueName, ProductConstant.PRODUCT_SOURCE_TMALL);
						break;
					}
				}
			}

		}

	}

	private void saveProductSpec(Long keyId, String keyName, String valueNames, Byte source) {
		TBrProductSpecQuery tBrProductSpecQuery = new TBrProductSpecQuery();
		tBrProductSpecQuery.setPid(productId);
		long queryCount = queryCount(tBrProductSpecQuery);
		if (queryCount > 0) {
			return;
		}
		Long valueId = 0l;
		TBrSpecValue one = null;
		TBrSpecValue tBrSpecValue = null;
		TBrSpecValueQuery tBrSpecValueQuery = null;
		valueNames = valueNames.replace(";", "；");
		valueNames = valueNames.replace(" ", "；");
		valueNames = valueNames.replace(" ", "；");
		String[] valueNameArr = valueNames.split("；");
		for (String valueName : valueNameArr) {
			if (StringUtils.isNoneBlank(valueName)) {
				valueName = valueName.replace(" ", "");
				// 查询该值是否存在
				tBrSpecValueQuery = new TBrSpecValueQuery();
				tBrSpecValueQuery.setValueName(valueName);
				one = tBrSpecValueService.queryOne(tBrSpecValueQuery);
				// 不存在该值，则保存
				if (one == null) {
					tBrSpecValue = new TBrSpecValue();
					tBrSpecValue.setKeyId(keyId);
					tBrSpecValue.setValueName(valueName);
					setDefaultData((BaseEntity) tBrSpecValue);
					tBrSpecValue.setSource(source);
					tBrSpecValueService.add(tBrSpecValue);
					valueId = tBrSpecValue.getId();
				} else {
					// 存在，则取出
					valueId = one.getId();
					valueName = one.getValueName();
				}
				// 保存产品规格关联表
				TBrProductSpec tBrProductSpec = new TBrProductSpec();
				tBrProductSpec.setPid(productId);
				tBrProductSpec.setSpecKeyId(keyId);
				tBrProductSpec.setSpecKeyName(keyName);
				tBrProductSpec.setSpecValueId(valueId);
				tBrProductSpec.setSpecValueName(valueName);
				tBrProductSpec.setSource(source);
				setDefaultData((BaseEntity) tBrProductSpec);
				add(tBrProductSpec);
			}
		}
	}

	private void getTmallCommentStatisticsData() {
		TBrProductCommentStatistics tBrProductCommentStatistics = new TBrProductCommentStatistics();
		tBrProductCommentStatistics.setPid(productId);
		TBrProductCommentStatistics queryOne = tBrProductCommentStatisticsService.queryOne(tBrProductCommentStatistics);
		TBrProductCommentStatistics commentSummary = TmallData.getCommentSummary(skuId, sellerId);
		if (commentSummary == null) {
			return;
		}
		if (queryOne == null) {
			commentSummary.setPid(productId);
			setDefaultData((BaseEntity) commentSummary);
			tBrProductCommentStatisticsService.add(commentSummary);
		} else {
			queryOne.setTmallNumAll(commentSummary.getTmallNumAll());
			queryOne.setTmallNumImg(commentSummary.getTmallNumImg());
			queryOne.setTmallNumMore(commentSummary.getTmallNumMore());
			tBrProductCommentStatisticsService.updateByIdSelective(queryOne);
		}

	}

	private int getTmallBaseData(WebDriver chrome) {

		try {
			String saleStr = skuDoc.select(".tm-ind-sellCount .tm-count").text();
			String priceStr = skuDoc.select("#J_StrPriceModBox .tm-price").text();
			if (priceStr.indexOf("-") > 0) {
				priceStr = priceStr.split("-")[0];
			}
			TBrProduct product = new TBrProduct();
			int saleInt = 0;
			product.setTmallPrice(transformPrice(priceStr));
			try {
				saleInt = Integer.parseInt(saleStr);
			} catch (NumberFormatException e) {
				log.info("销量格式化错误");
				product.setTmallPrice(BigDecimal.ZERO);
			}
			product.setId(productId);
			product.setTmallUrl(skuUrl);
			product.setSales(saleInt);
			tBrProductService.updateByIdSelective(product);
		} catch (Exception e1) {
			log.info("请求超时");
			return 5;
		}
		return 0;
	}

	//
	public static void main(String[] args) throws Exception {
		String url = "https://mdskip.taobao.com/core/initItemDetail.htm?"
				+ "tryBeforeBuy=false&isApparel=false&isPurchaseMallPage=false" + "&isForbidBuyItem=false"
				+ "&household=false&offlineShop=false&isRegionLevel=true"
				+ "&isAreaSell=true&queryMemberRight=true&itemId=35828877486"
				+ "&addressLevel=3&isUseInventoryCenter=true&sellerPreview=false" + "&cartEnable=true&service3C=false"
				+ "&tmallBuySupport=true&showShopProm=false&isSecKill=false";

		HttpClientBuilder builder = HttpClients.custom();
		builder.setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)");
		CloseableHttpClient httpClient = builder.build();
		final HttpGet httpGet = new HttpGet(url);
		// httpGet.addHeader("Referer",
		// "http://detail.tmall.com/item.htm?id=40533381395&skuId=68347779144&areaId=110000&cat_id=50024400&rn=763d147479ecdc17c2632a4219ce96b3&standard=1&user_id=263726286&is_b=1");
		httpGet.addHeader("Referer", "https://detail.tmall.com/item.htm?" + "&id=35828877486");
		CloseableHttpResponse response = null;
		response = httpClient.execute(httpGet);
		final HttpEntity entity = response.getEntity();
		String result = null;
		if (entity != null) {
			result = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		}

		// 商品价格的返回值，需要解析出来价格

		result = result.substring(10, result.length() - 1);
		System.out.println(result);
		response.close();
		httpClient.close();
	}

	// 获得京东图片
	private void getJDImages() throws InterruptedException {
		Elements images = skuDoc.select("#spec-list ul li img");
		saveImages(images);
	}

	// 获得京东规格参数
	private void getJDSpecData() throws InterruptedException {

		Elements dl = skuDoc.select(".Ptable .Ptable-item dl");
		Elements dt = dl.select("dt");
		Elements dd = dl.select("dd");
		String dtText = null;
		Long keyId = 0l;
		String keyName = null;
		String valueNames = null;
		// 循环抓取dl的每一行
		int size = dt.size();
		for (int i = 0; i < size; i++) {
			dtText = dt.eq(i).text();
			// 循环key列表
			for (TBrSpecKey key : tbrSpecKeyList) {
				// 当前dt包含keyName时，操作该规格参数。
				keyName = key.getKeyName();
				if (StringUtils.isNotBlank(dtText) && dtText.contains(keyName)) {
					keyId = key.getId();
					// 查到当前dt对应的dd的值。
					valueNames = dd.get(i).text();
					saveProductSpec(keyId, keyName, valueNames, ProductConstant.PRODUCT_SOURCE_JD);
					break;
				}
			}
		}
	}

	private void getJDCommentStatisticsData() throws InterruptedException {

		try {
			// 异步返回评论统计数据的action
			String data = HttpUtils
					.getData("https://club.jd.com/comment/productCommentSummaries.action?referenceIds=" + skuId, GBK);
			JSONObject jsonObj = JSON.parseObject(data);
			JSONArray jsonArray = jsonObj.getJSONArray("CommentsCount");
			JSONObject jsonObj2 = jsonArray.getJSONObject(0);

			String data2 = HttpUtils.getData(
					"http://club.jd.com/discussion/getProductPageImageCommentList.action?productId=" + skuId, GBK);
			JSONObject jsonObj3 = JSON.parseObject(data2);
			JSONObject jsonObj4 = jsonObj3.getJSONObject("imgComments");
			String jdNumImg = jsonObj4.getString("imgCommentCount");

			TBrProductCommentStatistics one = new TBrProductCommentStatistics();
			one.setPid(productId);
			TBrProductCommentStatistics queryOne = tBrProductCommentStatisticsService.queryOne(one);
			if (queryOne == null) {
				one.setJdNumAll(jsonObj2.getString("CommentCountStr"));
				one.setJdNumImg(jdNumImg);
				one.setJdNumMore(jsonObj2.getString("AfterCountStr"));
				one.setJdNumGood(jsonObj2.getString("GoodCountStr"));
				one.setJdNumMiddle(jsonObj2.getString("GeneralCountStr"));
				one.setJdNumBad(jsonObj2.getString("PoorCountStr"));
				setDefaultData((BaseEntity) one);
				tBrProductCommentStatisticsService.add(one);
			} else {
				queryOne.setJdNumAll(jsonObj2.getString("CommentCountStr"));
				queryOne.setJdNumImg(jdNumImg);
				queryOne.setJdNumMore(jsonObj2.getString("AfterCountStr"));
				queryOne.setJdNumGood(jsonObj2.getString("GoodCountStr"));
				queryOne.setJdNumMiddle(jsonObj2.getString("GeneralCountStr"));
				queryOne.setJdNumBad(jsonObj2.getString("PoorCountStr"));
				tBrProductCommentStatisticsService.updateByIdSelective(queryOne);
			}
		} catch (Exception e) {
			log.info("***获得京东评论统计信息，json解析异常***");
			// e.printStackTrace();
		}

	}

	// 获得价格，无销量
	private void getJDBaseData(Document doc) throws InterruptedException {
		Elements goodsList = doc.select("#J_goodsList");
		String priceStr = goodsList.select("ul").eq(0).select("li").eq(0).select(".p-price").select("i").text();

		TBrProduct product = new TBrProduct();
		product.setId(productId);
		product.setJdUrl(skuUrl);
		product.setJdPrice(transformPrice(priceStr));
		tBrProductService.updateByIdSelective(product);
	}

	private BigDecimal transformPrice(String priceStr) {
		BigDecimal price = null;
		if (StringUtils.isBlank(priceStr)) {
			return null;
		}
		try {
			double doubleValue = Double.valueOf(priceStr).doubleValue();
			price = BigDecimal.valueOf(doubleValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return price;
	}

	private void saveImages(Elements images) {
		// 获得图片
		TBrProductImage tBrProductImage = null;
		String imageUrl = null;

		TBrProductImageQuery tBrProductImageQuery = new TBrProductImageQuery();
		tBrProductImageQuery.setProductId(productId);
		tBrProductImageQuery.setSource(sourceType);
		long queryCount = tBrProductImageService.queryCount(tBrProductImageQuery);
		if (queryCount == 0) {
			List<TBrProductImage> tBrProductImageList = Lists.newArrayList();
			for (Element image : images) {
				tBrProductImage = new TBrProductImage();
				if (sourceType == ProductConstant.PRODUCT_SOURCE_JD) {
					imageUrl = image.attr("data-url");
					tBrProductImage.setJdUrl(imageUrl);
				} else if (sourceType == ProductConstant.PRODUCT_SOURCE_TMALL) {
					imageUrl = image.attr("src");
					tBrProductImage.setTmallUrl(imageUrl);
				}
				setDefaultData((BaseEntity) tBrProductImage);
				tBrProductImage.setName(productName);
				tBrProductImage.setProductId(productId);
				tBrProductImage.setFileType(imageUrl.substring(imageUrl.lastIndexOf(".")));
				tBrProductImage.setImageType(ProductConstant.IMAGETYPE_OTHER);
				tBrProductImage.setSource(sourceType);
				tBrProductImageList.add(tBrProductImage);
			}
			tBrProductImageService.addInBatch(tBrProductImageList);
		}
	}

	private void setDefaultData(BaseEntity be) {
		be.setCreateTime(new Date());
		be.setDelFlg(Constant.NO);
		be.setIsActive(Constant.STATUS_ACTIVE);
	}

	public static String getTmallId(String tmallUrl) {
		int index = tmallUrl.indexOf("id=");
		String id = "";
		if (index > 0) {
			id = tmallUrl.substring(tmallUrl.lastIndexOf("id=") + 3);
		}
		return id;
	}

	public static String getJdSkuId(String jdUrl) {
		int index = jdUrl.indexOf(".html");
		String JdSkuId = "";
		if (index > 0) {
			JdSkuId = jdUrl.substring(jdUrl.lastIndexOf("/") + 1, index);
		}
		return JdSkuId;
	}

	@Override
	public List<Comment> getComment(Long id) {

		// jd comment
		List<Comment> commentList = Lists.newArrayList();
		TBrProduct tBrProduct = tBrProductService.queryById(id);
		String jdUrl = tBrProduct.getJdUrl();
		String JdSkuId = getJdSkuId(jdUrl);
		if (StringUtils.isNoneBlank(JdSkuId)) {
			List<Comment> jdCommentDetails = JdData.getCommentDetails(JdSkuId);
			commentList.addAll(jdCommentDetails);
		}

		// tmall comment
		String tmallUrl = tBrProduct.getTmallUrl();
		try {
			this.skuDoc = JsoupUtil.getDoc(tmallUrl, "GBK");
			this.skuId = getTmallId(tmallUrl);
		} catch (InterruptedException e) {
			log.info("***天猫url不正确，无法获得评论***");
			// e.printStackTrace();
		}
		if (skuDoc != null && StringUtils.isNoneBlank(skuId)) {
			String skuStr = skuDoc.toString();
			skuStr = skuStr.substring(skuStr.indexOf("sellerId"));
			skuStr = skuStr.substring(skuStr.indexOf(":"), skuStr.indexOf(","));
			this.sellerId = skuStr.replace(":", "").replace("\"", "");
			List<Comment> tmallCommentDetails = TmallData.getCommentDetails(skuId, sellerId);
			commentList.addAll(tmallCommentDetails);
		}
		;

		return commentList;
	}

	@Override
	public List<Comment> getTmallComments(Long id) {
		// tmall comment
		List<Comment> commentList = Lists.newArrayList();
		TBrProduct tBrProduct = tBrProductService.queryById(id);
		String tmallUrl = tBrProduct.getTmallUrl();
		try {
			this.skuDoc = JsoupUtil.getDoc(tmallUrl, "GBK");
			this.skuId = getTmallId(tmallUrl);
		} catch (InterruptedException e) {
			log.info("***天猫url不正确，无法获得评论***");
		}
		if (skuDoc != null && StringUtils.isNoneBlank(skuId)) {
			String skuStr = skuDoc.toString();
			skuStr = skuStr.substring(skuStr.indexOf("sellerId"));
			skuStr = skuStr.substring(skuStr.indexOf(":"), skuStr.indexOf(","));
			this.sellerId = skuStr.replace(":", "").replace("\"", "");
			List<Comment> tmallCommentDetails = TmallData.getCommentDetails(skuId, sellerId);
			commentList.addAll(tmallCommentDetails);
		}
		;

		return commentList;
	}

	@Override
	public List<Comment> getJdComments(Long id) {
		// jd comment
		List<Comment> commentList = Lists.newArrayList();
		TBrProduct tBrProduct = tBrProductService.queryById(id);
		String jdUrl = tBrProduct.getJdUrl();
		String JdSkuId = getJdSkuId(jdUrl);
		if (StringUtils.isNoneBlank(JdSkuId)) {
			List<Comment> jdCommentDetails = JdData.getCommentDetails(JdSkuId);
			commentList.addAll(jdCommentDetails);
		}
		return commentList;
	}

}
