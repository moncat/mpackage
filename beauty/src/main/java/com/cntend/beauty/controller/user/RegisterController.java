package com.cntend.beauty.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.constant.Constant;
import com.co.example.constant.HttpStatusCode;
import com.co.example.constant.WechatConstant;
import com.co.example.entity.user.aide.TUserQuery;
import com.co.example.service.user.TUserActiveService;
import com.co.example.service.user.TUserService;
import com.co.example.utils.ClientUtil;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("register")
public class RegisterController {
	
	@Resource
	TUserService tUserService;
	
	@Resource
	TUserActiveService tUserActiveService;
	
	
	@RequestMapping(value = "/agreement")
	public String agreement(){
		return "register/agreement";
	}
	
	
	@RequestMapping(value = "/init")
	public String register(){
		return "register/register";
	}
	
	
	//发送验证码
	@ResponseBody
	@RequestMapping(value = "/vcode", method = { RequestMethod.POST })
    public Map<String,Object> vcode(Model model,HttpSession session, HttpServletRequest request,String phoneNum) throws Exception{
		HashMap<String, Object> map = Maps.newHashMap();
		TUserQuery tUserQuery = new TUserQuery();
		tUserQuery.setLoginName(phoneNum);
		tUserQuery.setDelFlg(Constant.NO);
		long count = tUserService.queryCount(tUserQuery);
		if(count>0){
			map.put("desc", "该手机号已注册！请登录");
			map.put("code", HttpStatusCode.CODE_ERROR);
			return map;
		}
		String ip = ClientUtil.getIp(request);
		tUserActiveService.sendVerifyCodeByPhone(phoneNum,ip);
		map.put("code", HttpStatusCode.CODE_SUCCESS);
        return map;
    }
	
	//查询验证码是否正确
	@ResponseBody
	@RequestMapping(value = "/add", method = { RequestMethod.POST })
    public Map<String,Object> add(Model model,HttpSession session, HttpServletRequest request,@RequestParam String phoneNum,@RequestParam String vcode,@RequestParam String password1,@RequestParam String password2) throws Exception{
		HashMap<String, Object> map = Maps.newHashMap();
		Boolean verifyVCode = tUserActiveService.verifyVCode(phoneNum,vcode);
		if(!verifyVCode){
			map.put("code", HttpStatusCode.CODE_ERROR);
			map.put("desc", "验证码不正确或已过期！");
			return map;
		}
		if(!StringUtils.equals(password1, password2)){
			map.put("code", HttpStatusCode.CODE_ERROR);
			map.put("desc", "两次密码不匹配！");
			return map;
		}
		String ip = ClientUtil.getIp(request);
		String openId = (String)session.getAttribute(WechatConstant.OPEN_ID);
		tUserService.addUser(vcode ,ip,phoneNum, password1,openId);
		map.put("code", HttpStatusCode.CODE_SUCCESS);
        return map;
	}
	
	
}
