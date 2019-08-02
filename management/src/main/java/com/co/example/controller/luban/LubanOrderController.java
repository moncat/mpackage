package com.co.example.controller.luban;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.co.example.common.utils.StringUtils;
import com.co.example.constant.HttpStatusCode;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.luban.TLubanOrder;
import com.co.example.entity.luban.aide.TLubanOrderQuery;
import com.co.example.service.luban.TLubanOrderService;
import com.co.example.utils.BaseDataUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("lubanOrder")
public class LubanOrderController extends BaseControllerHandler<TLubanOrderQuery> {

	@Autowired
	TLubanOrderService tLubanOrderService;
	TLubanOrder tLubanOrder;

	long id2, userId2, productId, shopId = 0l;
	String userName, shopName = "";
	int userType, postAmount, totalAmount, postCode = 0;

	@ResponseBody
	@RequestMapping(value = "/addDatas", method = {  RequestMethod.POST })
	public String addDatas(@RequestBody String text) {
		Map<String, Object> result =  new HashMap<String, Object>();
		try {
			if (StringUtils.isBlank(text)) {
				log.info("luban--json数据为空");
				result.put("code", HttpStatusCode.CODE_ERROR);
				result.put("msg", "json数据为空");
				return JSON.toJSONString(result);
			}
			JSONObject jsonObject1 = JSON.parseObject(text);
			JSONArray jsonArray = jsonObject1.getJSONArray("data");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject jsonObject2 = jsonObject.getJSONObject("order");
				long orderId = jsonObject2.getLongValue("order_id");
				TLubanOrderQuery tLubanOrderQuery = new TLubanOrderQuery();
				tLubanOrderQuery.setOrderId(orderId);
				Long count = tLubanOrderService.queryCount(tLubanOrderQuery);
				if (count>0) {
					log.info("luban--该订单已存在:" + orderId);
				} else {
					tLubanOrder = new TLubanOrder();
					BaseDataUtil.setDefaultData(tLubanOrder);
					id2 = jsonObject2.getLongValue("id");
					userId2 = jsonObject2.getLongValue("user_id");
					userType = getInt(jsonObject2,"user_type");	
					userName = jsonObject2.getString("user_name");
					productId = jsonObject2.getLongValue("product_id");
					shopId = jsonObject2.getLongValue("shop_id");
					shopName = jsonObject2.getString("shop_name");				
					postAmount = getInt(jsonObject2,"post_amount");				
					totalAmount = getInt(jsonObject2,"total_amount");	
					postCode = getInt(jsonObject2,"post_code");					
					String postReceiver = jsonObject2.getString("post_receiver");
					String postTelSecret = jsonObject2.getString("postTelSecret");
					String logisticsCode = jsonObject2.getString("logistics_code");
					String logisticsTime = jsonObject2.getString("logistics_time");
					String createTime2 = jsonObject2.getString("create_time");
					String productName = jsonObject2.getString("product_name");
					String postTel = jsonObject2.getString("post_tel");
					String telArea = jsonObject2.getString("tel_area");
					tLubanOrder.setId2(id2);
					tLubanOrder.setOrderId(orderId);
					tLubanOrder.setUserId(userId2);
					tLubanOrder.setUserType(userType);
					tLubanOrder.setUserName(userName);
					tLubanOrder.setProductId(productId);
					tLubanOrder.setShopId(shopId);
					tLubanOrder.setShopName(shopName);
					tLubanOrder.setPostAmount(postAmount);
					tLubanOrder.setTotalAmount(totalAmount);
					tLubanOrder.setPostCode(postCode);
					tLubanOrder.setPostReceiver(postReceiver);
					tLubanOrder.setPostTelSecret(postTelSecret);
					tLubanOrder.setLogisticsCode(logisticsCode);
					tLubanOrder.setLogisticsTime(logisticsTime);
					tLubanOrder.setCreateTime2(createTime2);
					tLubanOrder.setProductName(productName);
					tLubanOrder.setPostTel(postTel);
					tLubanOrder.setTelArea(telArea);
					tLubanOrderService.add(tLubanOrder);
					log.info("luban--已保存该订单:" + orderId);
				}

			}
		} catch (Exception e) {
			log.info("luban--解析json报错");
			e.printStackTrace();
			result.put("code", HttpStatusCode.CODE_ERROR);
			result.put("msg", "解析json报错");
			return JSON.toJSONString(result);
		}
		log.info("luban--处理完成");
		result.put("code", HttpStatusCode.CODE_SUCCESS);
		result.put("msg", "处理完成");
		return JSON.toJSONString(result);
	}
	
	private int getInt(JSONObject jsonObject,String tag){
		String tmp = jsonObject.getString(tag);
		if(StringUtil.isBlank(tmp)){
			return 0;
		}
		return jsonObject.getIntValue(tag);		
		
	}

}











