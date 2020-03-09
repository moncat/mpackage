package com.co.example.controller.login;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.common.utils.IdentifyUtil;
import com.co.example.constant.SessionConstant;

/**
 * 验证码
 * 
 * @author ZYL
 * @date 2016年12月22日 下午3:02:31
 */
@Controller
public class IdentifyCodeController {

	//图片验证
	@RequestMapping("/identifyCode")
	public void getCode(HttpSession session, HttpServletResponse resp) throws IOException {
		IdentifyUtil.createCode(session, resp, SessionConstant.SESSION_IDENTITY_CODE);
	}


}