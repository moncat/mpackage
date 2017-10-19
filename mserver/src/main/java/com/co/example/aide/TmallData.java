package com.co.example.aide;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.co.example.common.utils.HttpUtils;
import com.co.example.entity.comment.TBrProductCommentStatistics;

/**
 * 
 * @author zyl
 *
 */
public class TmallData {
	
		public static void main(String[] args) {
			String str = httpRequest("35828877486");
			String priceFromJson = getPriceFromJson(str,"34942850198");
			System.out.println(priceFromJson);
		}
		
		
		//评论详情，待确定获取方式  TODO
		//https://rate.tmall.com/list_detail_rate.htm?itemId=37272655425&sellerId=1127877954&order=3&currentPage=1&append=0&content=1&needFold=0&callback=jsonp2134
		public static String getCommentDetails(String id){
			String url="https://dsr-rate.tmall.com/list_dsr_info.htm?itemId="+id+"&callback=jsonp200";
			String data = HttpUtils.getData(url);
			return null;
		}
		
		
		//https://dsr-rate.tmall.com/list_dsr_info.htm?itemId=43502413713&callback=jsonp200
		//返回值 jsonp200({"dsr":{"gradeAvg":4.9,"itemId":0,"peopleNum":0,"periodSoldQuantity":0,"rateTotal":40507,"sellerId":0,"spuId":0,"totalSoldQuantity":0}})
		public static TBrProductCommentStatistics getCommentSummary(String itemId,String sellerId){
			String url="https://dsr-rate.tmall.com/list_dsr_info.htm?itemId="+itemId+"&callback=jsonp200";
			String jsonStr = HttpUtils.getData(url);
			jsonStr = jsonStr.replace("jsonp200", "").replace("(", "").replace(")", "");
			JSONObject parseObject = JSON.parseObject(jsonStr);
			JSONObject jsonObject = parseObject.getJSONObject("dsr");
			//总评论数
			String tmallNumAll = jsonObject.getString("rateTotal");
			
			TBrProductCommentStatistics tBrProductCommentStatistics = new TBrProductCommentStatistics();
			tBrProductCommentStatistics.setTmallNumAll(tmallNumAll);
			
			String url2 = "https://rate.tmall.com/list_detail_rate.htm?itemId="+itemId+"&sellerId="+sellerId+"&order=3&currentPage=1&append=0&content=1&needFold=0&callback=jsonp2134";
			String jsonStr2 = HttpUtils.getData(url2);
			jsonStr2 = jsonStr2.replace("jsonp2134(", "").replace(")", "");
			JSONObject parseObject2 = JSON.parseObject(jsonStr2);
			JSONObject jsonObject2 = parseObject2.getJSONObject("rateDetail");
			JSONObject jsonObject3 = jsonObject2.getJSONObject("rateCount");
			String tmallNumImg = jsonObject3.getString("picNum");
			String tmallNumMore = jsonObject3.getString("used");
			tBrProductCommentStatistics.setTmallNumImg(tmallNumImg);
			tBrProductCommentStatistics.setTmallNumMore(tmallNumMore);
			return tBrProductCommentStatistics;
		}
		
		
		/**
		 * 获得价格
		 * @param jsonStr
		 * @param skuId
		 * @return
		 */
		public static String getPriceFromJson(String jsonStr,String skuId){
			System.out.println(2);
			String str = jsonStr.substring(jsonStr.indexOf("\"price\""));
			str = str.substring(str.indexOf(":"),str.indexOf(","));
			str = str.replace(":", "").replace("\"", "");
			
//			jsonStr = "{\""+jsonStr+"}";
//			JSONObject parseObject = JSON.parseObject(jsonStr);
//			JSONObject jsonObject = parseObject.getJSONObject("Model");
//			JSONObject jsonObject2 = jsonObject.getJSONObject("itemPriceResultDO");
//			JSONObject jsonObject3 = jsonObject2.getJSONObject("priceInfo");
//			JSONObject jsonObject4 = jsonObject3.getJSONObject("def");
//			String string = jsonObject4.getString("price");
			return str;
		}
		
		
		/**
		 * 获得销量
		 * @param jsonStr
		 */
		public static String getsellCountFromJson(String jsonStr){
			jsonStr = "{\""+jsonStr+"}";
			JSONObject parseObject = JSON.parseObject(jsonStr);
			JSONObject jsonObject = parseObject.getJSONObject("Model");
			JSONObject jsonObject2 = jsonObject.getJSONObject("sellCountDO");
			String string = jsonObject2.getString("sellCount");
			return string;
		}
		
	
		public static String httpRequest(String itemId) {
			String url = "https://mdskip.taobao.com/core/initItemDetail.htm?"
					+ "tryBeforeBuy=false&isApparel=false&isPurchaseMallPage=false"
					+ "&isForbidBuyItem=false"
					+ "&household=false&offlineShop=false&isRegionLevel=true"
					+ "&isAreaSell=true&queryMemberRight=true&itemId="+itemId;
//					+ "&addressLevel=3&isUseInventoryCenter=true&sellerPreview=false"
//					+ "&cartEnable=true&service3C=false"
//					+ "&tmallBuySupport=true&showShopProm=false&isSecKill=false";
			
			HttpClientBuilder builder = HttpClients.custom();
			builder.setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)");
			CloseableHttpClient httpClient = builder.build();
			final HttpGet httpGet = new HttpGet(url);
			httpGet.addHeader("Referer", "https://detail.tmall.com/item.htm?"
					+ "&id="+itemId
					);
			CloseableHttpResponse response = null;
			String result = null;
			try {
				response = httpClient.execute(httpGet);
				final HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity);
					EntityUtils.consume(entity);
				}
				result = result.substring(10, result.length()-1);
				response.close();
				httpClient.close();
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return result;
		}
}
