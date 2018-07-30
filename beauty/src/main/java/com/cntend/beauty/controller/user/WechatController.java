package com.cntend.beauty.controller.user;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.co.example.common.utils.HttpClientUtils;
import com.co.example.common.utils.HttpUtils;
import com.co.example.constant.WechatConstant;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("wechat")
public class WechatController {
		
		/**
		 * 
		 * 获取微信openId:访问就执行，先到session获取openId 如何没有，则  先判断是否是微信浏览器，如果是则获取微信openId，如果否则openId为空，放到session
		 * 见WechatInterceptor拦截器
		 * 
		 * 去登录：先到数据库检查微信openId，如果有则直接登录，如果没有则跳转到登录页面
		 * 
		 * 去注册：如果注册成功，用当前openId查询一条数据   如果没数据则新增
		 * 
		 * 登录成功：用当前openId查询一条数据   如果没数据则更新，如果有则不动，不解除绑定
		 * 
		 * 暂时不考虑，微信解绑，更换手机号问题
		 * 
		 * @param code
		 * @param state
		 */
		@RequestMapping(value = "/callback", method = { RequestMethod.GET, RequestMethod.POST })
		public String callback(HttpSession session , String code,String state) {
			String openId = "0";
			log.info("wechat回调返回code="+code);
			//判断是否报错，或是否非微信浏览器
			if(StringUtils.isNotBlank(code)){
				String openid_url = WechatConstant.OPENID_URL.replace("APPID", WechatConstant.APPID)
						.replace("SECRET", WechatConstant.SECRET).replace("CODE", code);
				try {
					String str = HttpUtils.postData(openid_url);
					if(StringUtils.isNotEmpty(str) && str.contains("openid")){
						JSONObject object = JSON.parseObject(str);
						openId = object.getString("openid");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			log.info("wechat放到session中的openId="+openId);
			session.setAttribute(WechatConstant.OPEN_ID, openId);
			return "redirect:/login";
		}
		
		@ResponseBody
		@RequestMapping(value = "/isWechat", method = { RequestMethod.GET, RequestMethod.POST })
		public HashMap<String, Object> isWechat(HttpSession session ,Boolean isWechat,HttpServletResponse response) throws Exception {
			String openId = (String)session.getAttribute(WechatConstant.OPEN_ID);
			HashMap<String, Object> map = Maps.newHashMap();
			if(isWechat){
				log.info("是否为微信浏览器="+isWechat);
				session.setAttribute(WechatConstant.IS_WECHAT, true);
				if(StringUtils.isBlank(openId)){
					log.info("是微信浏览器，但openId为空");
					String code_url = WechatConstant.CODE_URL.replace("APPID", WechatConstant.APPID)
							.replace("REDIRECT_URI", WechatConstant.REDIRECT_URI).replace("SCOPE", WechatConstant.SCOPE);
					map.put("code", 200);
					map.put("url", code_url);
				}
			}else{
				log.info("是否为微信浏览器="+isWechat);
				session.setAttribute(WechatConstant.IS_WECHAT, false);
				map.put("code", 400);
				session.setAttribute(WechatConstant.OPEN_ID, "0");
			}
			return map;
		}
		
}
