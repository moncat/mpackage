package com.co.example.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.co.example.constant.SessionConstant;

import lombok.Getter;

@Getter
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
    private static final long serialVersionUID = 6975601077710753878L;
   
    private String identifyCode;
   

    public CustomWebAuthenticationDetails(HttpServletRequest request) {
    	super(request);
    	HttpSession session = request.getSession();
        String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(StringUtils.isBlank(username)){
			throw new BadCredentialsException("用户名不能为空！");
		}
		session.setAttribute("username", username);
		if(StringUtils.isBlank(password)){
			throw new BadCredentialsException("密码不能为空！");
		}
		String inputCode = request.getParameter(SessionConstant.SESSION_IDENTITY_CODE);
		String genCode = (String)session.getAttribute(SessionConstant.SESSION_IDENTITY_CODE);
		if(StringUtils.isBlank(inputCode)){
			throw new BadCredentialsException("验证码不能为空！");
		}
		if(!genCode.equalsIgnoreCase(inputCode)){
			throw new BadCredentialsException("验证码不正确！");
		}
        
    }

    

}