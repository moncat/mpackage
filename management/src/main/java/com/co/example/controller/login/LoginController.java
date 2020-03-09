package com.co.example.controller.login;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.utils.MD5;
import com.co.example.constant.HttpStatusCode;
import com.co.example.constant.SessionConstant;
import com.co.example.entity.admin.TAdmin;
import com.co.example.entity.admin.TAdminActive;
import com.co.example.entity.admin.aide.AdminSession;
import com.co.example.entity.admin.aide.TAdminQuery;
import com.co.example.service.admin.TAdminActiveService;
import com.co.example.service.admin.TAdminLoginService;
import com.co.example.service.admin.TAdminService;
import com.co.example.utils.ClientUtil;
import com.co.example.utils.SessionUtil;
import com.google.common.collect.Maps;

@Controller
public class LoginController {

	@Resource
	private TAdminService tAdminService;
	@Resource
	private TAdminLoginService tAdminLoginService;
	@Resource
	private TAdminActiveService tAdminActiveService;

	/**
	 * 登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.GET })
	public String login(HttpSession session, Model model) {
		AdminSession adminSession = SessionUtil.getAdminSession(session);
		if (null != adminSession) {
			boolean isLogin = SessionUtil.getIsLogin(session);
			if (isLogin) {
				return "redirect:/";
			}
		}
		String username = (String) session.getAttribute("username");
		Exception e = (Exception) session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		
		Object flo =  session.getAttribute("flo");
		if(flo != null){
			model.addAttribute("errMessage", "您的账号已在其他地方，当前账号已被迫下线。");
//			session.removeAttribute("flo");
		}
		String errMessage = "";
		if (e != null) {
			errMessage = e.getMessage();
			if (StringUtils.equals("Bad credentials", errMessage)) {
				errMessage = "用户名或密码错误！";
			} else if (StringUtils.equals("User is disabled", errMessage)) { // 用户不存在或已删除
				errMessage = "用户名或密码错误！";
			}
			model.addAttribute("errMessage", errMessage);
		}
		model.addAttribute("username", username);
		return "login/login";
	}

	// @ResponseBody
	// @RequestMapping(value = "/login")
	// public Map<String,Object> login(HttpServletRequest
	// request,HttpServletResponse response,HttpSession session,
	// String name,String password,String identifyCode,boolean rememberMe){
	//
	// Map<String,Object> mapResult = new HashMap<String,Object>();
	//
	// AdminSession oldAdminSession = SessionUtil.getAdminSession(session);
	// String identifyCodeSession =
	// (String)session.getAttribute(SessionConstant.SESSION_IDENTITY_CODE);
	//
	// if(identifyCodeSession != null){
	// //开始验证登录
	// if (StringUtils.isBlank(identifyCode) ||
	// !identifyCodeSession.equals(identifyCode.toUpperCase())) {
	// final int CODE_ERROR_IDENTITY = 401;
	// mapResult.put("code",CODE_ERROR_IDENTITY);
	// mapResult.put("desc", "验证码错误或超时!");
	// return mapResult;
	// } else {
	// session.removeAttribute(SessionConstant.SESSION_IDENTITY_CODE);
	// }
	// }
	//
	// //查询管理员
	// if (StringUtils.isBlank(name)) {
	// mapResult.put("code", HttpStatusCode.CODE_ERROR);
	// mapResult.put("desc", "管理员名不能为空!");
	// return mapResult;
	// }
	//
	// if (StringUtils.isBlank(password)) {
	// mapResult.put("code", HttpStatusCode.CODE_ERROR);
	// mapResult.put("desc", "密码不能为空!");
	// return mapResult;
	// }
	//
	// TAdmin admin = tAdminService.queryByLoginName(name);
	//
	// //密码加密
	// String passwordMd5 = MD5.encodeStr(password);
	// if (admin == null || !admin.getPassword().equals(passwordMd5)) {
	// mapResult.put("code", HttpStatusCode.CODE_ERROR);
	// mapResult.put("desc", "账号或密码错误!");
	// return mapResult;
	// } else if (admin.getDelFlg() == Constant.YES) {
	// mapResult.put("code", HttpStatusCode.CODE_ERROR);
	// mapResult.put("desc", "管理员已被锁定!");
	// return mapResult;
	// } else {
	// // 清空验证码
	// session.removeAttribute(SessionConstant.SESSION_IDENTITY_CODE);
	// //管理员登录
	// tAdminService.updateLogin(oldAdminSession,admin,ClientUtil.getIp(request));
	//
	// //判断该管理员是否启用自动登录
	// if(rememberMe){
	// //添加管理员到自动登录
	// String uuid = UUID.randomUUID().toString();
	// TAdminLogin adminLogin = new TAdminLogin();
	// adminLogin.setLoginIp(admin.getLastIp());
	// adminLogin.setRememberKey(uuid);
	// adminLogin.setAdminId(admin.getId());
	// adminLogin.setLoginTime(new Date());
	// tAdminLoginService.add(adminLogin);
	// CookieUtil.addCookieDefaultAge(response, CookieConstant.COOKIE_ADMIN_KEY,
	// uuid);
	// }
	// mapResult.put("code", HttpStatusCode.CODE_SUCCESS);
	// //登录跳转
	// String url =
	// (String)session.getAttribute(SessionConstant.SESSION_BACK_URL);
	// if(StringUtils.isBlank(url)){
	// url = PathUtil.getBasePath(request);
	// }
	// mapResult.put("url", url);
	// session.removeAttribute(SessionConstant.SESSION_BACK_URL);
	// session.setAttribute(SessionConstant.SESSION_ADMIN, oldAdminSession);
	// }
	// return mapResult;
	// }

	// @RequestMapping(value="/logout",method = { RequestMethod.GET})
	// public String logout(HttpServletRequest request,HttpServletResponse
	// response,HttpSession session){
	//
	// AdminSession adminSession =
	// (AdminSession)session.getAttribute(SessionConstant.SESSION_ADMIN);
	// adminSession.setLogin(false);
	// adminSession.setAdmin(null);
	// session.setAttribute(SessionConstant.SESSION_ADMIN, adminSession);
	// CookieUtil.removeCookie(response, CookieConstant.COOKIE_ADMIN_KEY);
	// return "redirect:/login";
	// }


}
