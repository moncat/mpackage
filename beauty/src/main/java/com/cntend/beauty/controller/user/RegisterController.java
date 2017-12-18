package com.cntend.beauty.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.constant.HttpStatusCode;
import com.co.example.entity.user.TUser;
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
	
	//发送验证码
	@ResponseBody
	@RequestMapping(value = "/vcode", method = { RequestMethod.POST })
    public Map<String,Object> vcode(Model model,HttpSession session, HttpServletRequest request,String phoneNum) throws Exception{
		HashMap<String, Object> map = Maps.newHashMap();
		TUserQuery tUserQuery = new TUserQuery();
		tUserQuery.setLoginName(phoneNum);
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
	@RequestMapping(value = "/nextStep", method = { RequestMethod.POST })
    public Map<String,Object> nextStep(Model model,HttpSession session, HttpServletRequest request,String phoneNum,String vcode) throws Exception{
		HashMap<String, Object> map = Maps.newHashMap();
		Boolean verifyVCode = tUserActiveService.verifyVCode(phoneNum,vcode);
		session.setAttribute("verifyVCode",verifyVCode);
		session.setAttribute("phoneNum",phoneNum);
		if(verifyVCode){
			map.put("code", HttpStatusCode.CODE_SUCCESS);
		}else{
			map.put("code", HttpStatusCode.CODE_ERROR);
			map.put("desc", "验证码不正确或已过期！");
		}
        return map;
	}
	
	//注册
	@ResponseBody
	@RequestMapping(value = "/pwd", method = { RequestMethod.POST })
    public Map<String,Object> pwd(Model model,HttpSession session, HttpServletRequest request,String password1,String password2) throws Exception{
		HashMap<String, Object> map = tUserService.updatePwd(session, password1, password2);
        return map;
    }
	
	
}
