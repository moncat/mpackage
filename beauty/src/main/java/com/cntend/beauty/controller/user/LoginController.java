package com.cntend.beauty.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.constant.HttpStatusCode;
import com.co.example.constant.SessionConstant;
import com.co.example.constant.WechatConstant;
import com.co.example.entity.user.TUser;
import com.co.example.entity.user.aide.TUserQuery;
import com.co.example.entity.user.aide.UserSession;
import com.co.example.service.user.TUserActiveService;
import com.co.example.service.user.TUserService;
import com.co.example.utils.ClientUtil;
import com.co.example.utils.SessionUtil;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class LoginController {
	
	@Resource
	TUserService tUserService;

	@Resource
	TUserActiveService tUserActiveService;
	
	@Resource
    AuthenticationManager authenticationManager;
	
	
	@RequestMapping(value = "/login", method = { RequestMethod.POST ,RequestMethod.GET})
	public String login(Model model ,HttpServletRequest request,HttpServletResponse response,HttpSession session,
			String name,String password){
		UserSession userSession = SessionUtil.getUserSession(session);
		if(null != userSession){
			boolean isLogin = SessionUtil.getIsLogin(session);
			if(isLogin){
				return "redirect:/";
			}
		}
		
		log.info("wechat登录开始");
		String openId = (String)session.getAttribute(WechatConstant.OPEN_ID);
		log.info("openId==========="+openId);
		if(StringUtils.isNoneBlank(openId)&& !openId.equals("0")){
			//微信自动登录
			TUserQuery tUserQuery = new TUserQuery();
			tUserQuery.setWxOpenId(openId);
			TUser one = tUserService.queryOne(tUserQuery);
			if(one!=null){
				String  redirectUrl = "/";
				log.info("数据库中含有该openId="+openId);
				String principal = "wx_"+one.getLoginName();
				String credentials = one.getPassword();
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal,credentials);
				try {
					//spring security 登录
					token.setDetails(new WebAuthenticationDetails(request));
					Authentication authenticatedUser = authenticationManager.authenticate(token);
					SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
					request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,	SecurityContextHolder.getContext());
//		            tokenBasedRememberMeServices.onLoginSuccess(request, response, token); // 使用 remember me
		            // 重定向到登陆前的页面
		            SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
		            if(savedRequest != null){
		            	redirectUrl = savedRequest.getRedirectUrl();
		            }
					//设置session 信息
					Object attribute = session.getAttribute(SessionConstant.SESSION_USER);
					UserSession oldUserSession = null;
					if(attribute == null ){
						oldUserSession = new UserSession();
					}else{
						oldUserSession = (UserSession) attribute;
					}
					tUserService.updateLogin(oldUserSession,one,ClientUtil.getIp(request),openId);
					session.setAttribute(SessionConstant.SESSION_USER, oldUserSession);	
				} catch (AuthenticationException e) {
					System.out.println("Authentication failed: " + e.getMessage());
				}
				return "redirect:"+redirectUrl;
			}
			log.info("数据库不含该openId，准备表单登录="+openId);
		}
		
		
		String username = (String)session.getAttribute("username");
		Exception e = (Exception)session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		String errMessage = "";
		if(e != null){
			errMessage = e.getMessage();
			if(StringUtils.equals("Bad credentials", errMessage)){
				errMessage = "用户名或密码错误！";
			}else if(StringUtils.equals("User is disabled", errMessage)){
				errMessage = "用户名或密码错误！";
			}
			model.addAttribute("errMessage", errMessage);
		}
		model.addAttribute("username", username);
		return "login/login";
	}
	
	
	@RequestMapping(value="/retrieve1",method = { RequestMethod.GET})
	public String retrieve1(Model model,HttpSession session){
		return "login/retrieve1";
	}
	
	//找回密码 发送验证码
	@ResponseBody
	@RequestMapping(value = "/vcode", method = { RequestMethod.POST })
    public Map<String,Object> vcode(Model model,HttpSession session, HttpServletRequest request,String phoneNum) throws Exception{
		HashMap<String, Object> map = Maps.newHashMap();
		TUserQuery tUserQuery = new TUserQuery();
		tUserQuery.setLoginName(phoneNum);
		long count = tUserService.queryCount(tUserQuery);
		if(count<=0){
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
		session.setAttribute("phoneNum",phoneNum);
		if(verifyVCode){
			map.put("code", HttpStatusCode.CODE_SUCCESS);
		}else{
			map.put("code", HttpStatusCode.CODE_ERROR);
			map.put("desc", "验证码不正确或已过期！");
		}
        return map;
	}
	
	@RequestMapping(value="/retrieve2",method = { RequestMethod.GET})
	public String retrieve2(Model model,HttpSession session){
		Boolean verifyVCode = (Boolean) session.getAttribute("verifyVCode");
		if(verifyVCode !=null && verifyVCode==true){
			return "login/retrieve2";
		}else{
			return "redirect:/login";
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/updatePwd",method = { RequestMethod.POST})
	public Map<String, Object> updatePwd(HttpServletRequest request,HttpServletResponse response,HttpSession session,String password1,String password2){
		String phoneNum = (String) session.getAttribute("phoneNum");
		Map<String, Object> map = Maps.newHashMap();
		String ip = ClientUtil.getIp(request);
		String vcode = (String) session.getAttribute("verifyVCode");
		Boolean flg = tUserService.updatePwd(ip,phoneNum, password1, password2,vcode);
		if(flg){
			session.removeAttribute("phoneNum");
			session.removeAttribute("verifyVCode");
			map.put("code", HttpStatusCode.CODE_SUCCESS);
		}else{
			map.put("code", HttpStatusCode.CODE_ERROR);
			map.put("desc", "两次输入的密码不一致！");
		}
		return map;
	}
	
	
	
	//登出
	@RequestMapping(value="/logout",method = { RequestMethod.GET})
	public String logout(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		session.setAttribute(SessionConstant.SESSION_USER, null);
		return "redirect:/login";
	}
	
	
	
		
}
