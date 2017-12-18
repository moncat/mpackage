package com.cntend.beauty.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.MD5;
import com.co.example.common.utils.ValidateUtil;
import com.co.example.constant.HttpStatusCode;
import com.co.example.constant.SessionConstant;
import com.co.example.entity.user.TUser;
import com.co.example.entity.user.aide.TUserQuery;
import com.co.example.entity.user.aide.UserSession;
import com.co.example.service.user.TUserActiveService;
import com.co.example.service.user.TUserService;
import com.co.example.utils.ClientUtil;
import com.co.example.utils.PathUtil;
import com.co.example.utils.SessionUtil;
import com.google.common.collect.Maps;




@Controller
public class LoginController {
	
	@Resource
	private TUserService tUserService;

	@Resource
	TUserActiveService tUserActiveService;
	/**
	 * 登录页面
	 * @return
	 */
	@RequestMapping(value="/loginInit",method = { RequestMethod.GET})
	public String login(HttpSession session,Model model){
		return "login/loginInit";
	}


	
	@ResponseBody
	@RequestMapping(value = "/login")
	public Map<String,Object> login(HttpServletRequest request,HttpServletResponse response,HttpSession session,
			String name,String password){
		Map<String,Object> mapResult = new HashMap<String,Object>();
		UserSession oldUserSession  = SessionUtil.getUserSession(session);
		if(!ValidateUtil.isMobile(name)){
			mapResult.put("code", HttpStatusCode.CODE_ERROR);
			mapResult.put("desc", "手机号格式不正确!");
			return mapResult;
		}
		if (StringUtils.isBlank(name)) {
			mapResult.put("code", HttpStatusCode.CODE_ERROR);
			mapResult.put("desc", "手机号不能为空!");
			return mapResult;
		}
		if (StringUtils.isBlank(password)) {
			mapResult.put("code", HttpStatusCode.CODE_ERROR);
			mapResult.put("desc", "密码不能为空!");
			return mapResult;
		}
		TUser user = tUserService.queryByLoginName(name);
		//密码加密
		String passwordMd5 = MD5.encodeStr(password);
		if (user == null || !user.getPassword().equals(passwordMd5)) {
			mapResult.put("code", HttpStatusCode.CODE_ERROR);
			mapResult.put("desc", "账号或密码错误!");
			return mapResult;
		} else if (user.getDelFlg() == Constant.YES) {
			mapResult.put("code", HttpStatusCode.CODE_ERROR);
			mapResult.put("desc", "该用户已被锁定!");
			return mapResult;
		} else {
			//用户登录
			tUserService.updateLogin(oldUserSession,user,ClientUtil.getIp(request));
			mapResult.put("code", HttpStatusCode.CODE_SUCCESS);
			//登录跳转
			String url = (String)session.getAttribute(SessionConstant.SESSION_BACK_URL);
			if(StringUtils.isBlank(url)){
				url = PathUtil.getBasePath(request);
			}
			mapResult.put("url", url);
			session.removeAttribute(SessionConstant.SESSION_BACK_URL);
			session.setAttribute(SessionConstant.SESSION_USER, oldUserSession);
		}
		return mapResult;
	}
	
	//找回密码 发送验证码
	@ResponseBody
	@RequestMapping(value = "/vcode", method = { RequestMethod.POST })
    public Map<String,Object> vcode(Model model,HttpSession session, HttpServletRequest request,String phoneNum) throws Exception{
		HashMap<String, Object> map = Maps.newHashMap();
		TUserQuery tUserQuery = new TUserQuery();
		tUserQuery.setLoginName(phoneNum);
		long count = tUserService.queryCount(tUserQuery);
		if(count<0){
			map.put("desc", "该手机号未注册，请注册！");
			map.put("code", HttpStatusCode.CODE_ERROR);
			return map;
		}
		String ip = ClientUtil.getIp(request);
		tUserActiveService.sendVerifyCodeByPhone(phoneNum,ip);
		map.put("code", HttpStatusCode.CODE_SUCCESS);
        return map;
    }
	
	//下一步
	@ResponseBody
	@RequestMapping(value = "/nextStep", method = { RequestMethod.POST })
    public Map<String,Object> nextStep(Model model,HttpSession session, HttpServletRequest request,String phoneNum,String vcode) throws Exception{
		HashMap<String, Object> map = Maps.newHashMap();
		Boolean verifyVCode = tUserActiveService.verifyVCode(phoneNum,vcode);
		session.setAttribute("verifyVCode",verifyVCode);
		if(verifyVCode){
			map.put("code", HttpStatusCode.CODE_SUCCESS);
		}else{
			map.put("code", HttpStatusCode.CODE_ERROR);
			map.put("desc", "验证码不正确或已过期！");
		}
        return map;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/findPwd",method = { RequestMethod.GET})
	public HashMap<String, Object> findPwd(HttpServletRequest request,HttpServletResponse response,HttpSession session,String password1,String password2){
		HashMap<String, Object> map = tUserService.updatePwd(session, password1, password2);
		return map;
	}
	
	
	
	//登出
	@RequestMapping(value="/logout",method = { RequestMethod.GET})
	public String logout(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		session.setAttribute(SessionConstant.SESSION_USER, null);
		return "redirect:/login";
	}
	
	
	@RequestMapping(value="/findPwdInit",method = { RequestMethod.GET})
	public String findPwd(){
		return "findPwdInit";
	}
		
}
