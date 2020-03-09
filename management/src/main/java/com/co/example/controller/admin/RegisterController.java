package com.co.example.controller.admin;

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
import com.co.example.common.utils.MD5;
import com.co.example.common.utils.ValidateUtil;
import com.co.example.constant.HttpStatusCode;
import com.co.example.constant.SessionConstant;
import com.co.example.entity.admin.TAdmin;
import com.co.example.entity.admin.aide.TAdminQuery;
import com.co.example.service.admin.TAdminActiveService;
import com.co.example.service.admin.TAdminService;
import com.co.example.utils.BaseDataUtil;
import com.co.example.utils.ClientUtil;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("register")
public class RegisterController {
	
	@Resource
	TAdminActiveService tAdminActiveService;
	
	@Resource
	TAdminService tAdminService;
	
	
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
		if(StringUtils.isBlank(phoneNum)){
			map.put("desc", "手机号不能为空！");
			return map;
		}
		if(!ValidateUtil.isMobile(phoneNum)){
			map.put("desc", "手机号不正确！");
			return map;
		}
		TAdminQuery tAdminQuery = new TAdminQuery();
		tAdminQuery.setMobilePhone(phoneNum);
		tAdminQuery.setDelFlg(Constant.NO);
		long count = tAdminService.queryCount(tAdminQuery);
		if(count>0){
			map.put("desc", "该手机号已注册！请登录");
			map.put("code", HttpStatusCode.CODE_ERROR);
			return map;
		}
		
		String ip = ClientUtil.getIp(request);
		tAdminActiveService.sendVerifyCodeByPhone(phoneNum,ip);
		map.put("code", HttpStatusCode.CODE_SUCCESS);
        return map;
    }
	
	/**
	 * @param model
	 * @param session
	 * @param phoneNum
	 * @param vcode
	 * @param password1
	 * @param password2
	 * @return
	 * @throws Exception
	 */
	//查询验证码是否正确
	@ResponseBody
	@RequestMapping(value = "/add", method = { RequestMethod.POST })
    public Map<String,Object> add(Model model,HttpSession session,
    		HttpServletRequest request,
    		@RequestParam String phoneNum,
    		@RequestParam String imgCode, //图片验证码
    		@RequestParam String vcode,  //短信验证码
    		@RequestParam String loginName,
    		@RequestParam String password1,@RequestParam String password2
    		) throws Exception{
		
		//验证图形验证码
		HashMap<String, Object> map = Maps.newHashMap();
		map.put("code", HttpStatusCode.CODE_ERROR);
		if(StringUtils.isBlank(imgCode)){
			map.put("desc", "图形验证码不能为空！");
			return map;
		}
		String sessonCode = (String)session.getAttribute(SessionConstant.SESSION_IDENTITY_CODE);
		String upperCase = imgCode.toUpperCase();
		if(!sessonCode.equals(upperCase)){
			map.put("desc", "图形验证码不正确或已过期，请点击更新！");
			return map;
		}
		//验证短信验证码
		if(StringUtils.isBlank(phoneNum)){
			map.put("desc", "手机号不能为空！");
			return map;
		}
		if(!ValidateUtil.isMobile(phoneNum)){
			map.put("desc", "手机号不正确！");
			return map;
		}
		if(StringUtils.isBlank(vcode)){
			map.put("desc", "短信验证码不能为空！");
			return map;
		}
		Boolean verifyVCode = tAdminActiveService.verifyVCode(phoneNum,vcode);
		if(!verifyVCode){
			map.put("desc", "短信验证码不正确或已过期！");
			return map;
		}
		//用户密码
		if(StringUtils.isBlank(loginName)){
			map.put("desc", "用户名不能为空！");
			return map;
		}
		if(StringUtils.isBlank(password1) || StringUtils.isBlank(password2)){
			map.put("desc", "密码不能为空！");
			return map;
		}
		if(!StringUtils.equals(password1, password2)){
			map.put("desc", "两次密码不匹配！");
			return map;
		}
		String ip = ClientUtil.getIp(request);
		TAdmin tAdmin = new TAdmin();
		tAdmin.setLoginName(loginName);
		tAdmin.setDisplayName(loginName);//暂时先使用登录名称作为展示名称
		tAdmin.setPassword(MD5.encodeStr(password1));
		tAdmin.setMobilePhone(phoneNum);
		tAdmin.setLastIp(ip);
		tAdmin.setPageVersion("2");//注册后默认使用v2版本
		BaseDataUtil.setDefaultData(tAdmin);
		tAdminService.add(tAdmin);
		tAdminActiveService.updateVCodeUsed(phoneNum, vcode);
		map.put("code", HttpStatusCode.CODE_SUCCESS);
        return map;
	}
	
	
}
