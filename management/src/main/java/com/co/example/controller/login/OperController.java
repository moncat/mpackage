package com.co.example.controller.login;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.internal.Dates;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.utils.DateUtil;
import com.co.example.common.utils.MD5;
import com.co.example.common.utils.ValidateUtil;
import com.co.example.constant.HttpStatusCode;
import com.co.example.constant.SessionConstant;
import com.co.example.entity.admin.TAdmin;
import com.co.example.entity.admin.TAdminActive;
import com.co.example.entity.admin.aide.TAdminQuery;
import com.co.example.service.admin.TAdminActiveService;
import com.co.example.service.admin.TAdminLoginService;
import com.co.example.service.admin.TAdminService;
import com.co.example.utils.ClientUtil;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("oper")
public class OperController {

	@Resource
	private TAdminService tAdminService;
	@Resource
	private TAdminLoginService tAdminLoginService;
	@Resource
	private TAdminActiveService tAdminActiveService;

	@RequestMapping(value = "/retrieve1", method = { RequestMethod.GET })
	public String retrieve1(Model model, HttpSession session) {
		return "login/retrieve1";
	}

	@RequestMapping(value = "/retrieve2", method = { RequestMethod.GET })
	public String retrieve2(Model model, HttpSession session) {
		Date vDate = (Date) session.getAttribute("vDate");
		if (vDate != null) {
			Date date = new Date();
			if (date.getTime() - vDate.getTime() < 60 * 1000) {
				return "login/retrieve2";
			}
		}
		return "redirect:/login";
	}

	// 保存新密码
	@ResponseBody
	@RequestMapping(value = "/saveAdminPwd", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> saveAdminPwd(Model model, HttpSession session, TAdmin admin, String verifycode,
			Integer step) {
		// 手机号邮箱都应支持注册，但目前只支持邮箱
		Map<String, Object> mapResult = new HashMap<String, Object>();
		TAdminActive tAdminActive = null;
		if (step == 1) {
			mapResult.put("step", 2);
			final int CODE_ERROR_IDENTITY = 401;
			if (StringUtils.isBlank(verifycode) || verifycode.length() != 6) {
				mapResult.put("code", CODE_ERROR_IDENTITY);
				mapResult.put("desc", "验证码错误!");
				return mapResult;
			} else {
				tAdminActive = tAdminActiveService.queryLastByEmail(admin.getLoginName());
				if (tAdminActive == null) {
					mapResult.put("code", CODE_ERROR_IDENTITY);
					mapResult.put("desc", "验证码错误!");
					return mapResult;
				} else {
					if (tAdminActive.getVcode().equals(verifycode)) {
						// 判断是否过期 目前三十分钟过期
						if (System.currentTimeMillis() - tAdminActive.getCreateTime().getTime() > 30 * 60 * 1000) {
							// 验证码过期
							mapResult.put("code", CODE_ERROR_IDENTITY);
							mapResult.put("desc", "验证码过期!");
							return mapResult;
						}
					} else {
						mapResult.put("code", CODE_ERROR_IDENTITY);
						mapResult.put("desc", "验证码错误!");
						return mapResult;
					}
				}
			}
			;
			TAdmin adminTmp = tAdminService.queryByLoginName(admin.getLoginName());
			if (adminTmp != null) {
				mapResult.put("code", HttpStatusCode.CODE_SUCCESS);
				mapResult.put("adminId", adminTmp.getId());
				return mapResult;
			} else {
				mapResult.put("code", HttpStatusCode.CODE_ERROR);
				mapResult.put("desc", "管理员名错误。");
				return mapResult;
			}
		}
		if (step == 2) {
			mapResult.put("step", 3);
			String passwordMd5 = MD5.encodeStr(admin.getPassword());
			admin.setPassword(passwordMd5);
			tAdminService.updateByIdSelective(admin);
		}
		if (step == 3) {
			mapResult.put("step", 3);
		}
		mapResult.put("code", HttpStatusCode.CODE_SUCCESS);
		return mapResult;
	}

	// 找回密码
	// 短信找回 1、验证短信 2、修改密码

	// 找回密码 发送验证码
	@ResponseBody
	@RequestMapping(value = "/vcode", method = { RequestMethod.POST })
	public Map<String, Object> vcode(Model model, HttpSession session, HttpServletRequest request, String phoneNum)
			throws Exception {
		HashMap<String, Object> map = Maps.newHashMap();
		if (StringUtils.isBlank(phoneNum)) {
			map.put("desc", "手机号不能为空！");
			return map;
		}
		if (!ValidateUtil.isMobile(phoneNum)) {
			map.put("desc", "手机号不正确！");
			return map;
		}
		TAdminQuery tAdminQuery = new TAdminQuery();
		tAdminQuery.setMobilePhone(phoneNum);
		long count = tAdminService.queryCount(tAdminQuery);
		if (count <= 0) {
			map.put("desc", "该手机号未注册，请注册！");
			map.put("code", HttpStatusCode.CODE_ERROR);
			return map;
		}
		String ip = ClientUtil.getIp(request);
		tAdminActiveService.sendVerifyCodeByPhone(phoneNum, ip);
		map.put("code", HttpStatusCode.CODE_SUCCESS);
		return map;
	}

	// 找回密码 第一步验证 短信验证码 图片验证码 ，准备进 下一步
	@ResponseBody
	@RequestMapping(value = "/nextStep", method = { RequestMethod.POST })
	public Map<String, Object> nextStep(Model model, HttpSession session, HttpServletRequest request, String phoneNum,
			@RequestParam String imgCode, // 图片验证码
			String vcode // 短信验证码
	) throws Exception {
		HashMap<String, Object> map = Maps.newHashMap();
		map.put("code", HttpStatusCode.CODE_ERROR);
		// 验证图片验证码
		if (StringUtils.isBlank(imgCode)) {
			map.put("desc", "图形验证码不能为空！");
			return map;
		}
		String sessonCode = (String) session.getAttribute(SessionConstant.SESSION_IDENTITY_CODE);
		String upperCase = imgCode.toUpperCase();
		if (!sessonCode.equals(upperCase)) {
			map.put("desc", "图形验证码不正确或已过期，请点击更新！");
			return map;
		}
		// 验证短信验证码
		if (StringUtils.isBlank(phoneNum)) {
			map.put("desc", "手机号不能为空！");
			return map;
		}
		if (!ValidateUtil.isMobile(phoneNum)) {
			map.put("desc", "手机号不正确！");
			return map;
		}
		if (StringUtils.isBlank(vcode)) {
			map.put("desc", "短信验证码不能为空！");
			return map;
		}
		Boolean verifyVCode = tAdminActiveService.verifyVCode(phoneNum, vcode);
		if (!verifyVCode) {
			map.put("desc", "短信验证码不正确或已过期！");
			return map;
		}
		tAdminActiveService.updateVCodeUsed(phoneNum, vcode);
		map.put("code", HttpStatusCode.CODE_SUCCESS);
		// 验证通过可以修改密码了
		session.setAttribute("vDate", new Date());
		session.setAttribute("phoneNum", phoneNum);
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/updatePwd", method = { RequestMethod.POST })
	public Map<String, Object> updatePwd(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			String password1, String password2) {
		String phoneNum = (String) session.getAttribute("phoneNum");
		Map<String, Object> map = Maps.newHashMap();
		Boolean flg = tAdminService.updatePwd(phoneNum, password1, password2);
		if (flg) {
			session.removeAttribute("phoneNum");
			session.removeAttribute("vDate");
			map.put("code", HttpStatusCode.CODE_SUCCESS);
		} else {
			map.put("code", HttpStatusCode.CODE_ERROR);
			map.put("desc", "两次输入的密码不一致！");
		}
		return map;
	}

}
