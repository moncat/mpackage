package com.co.example.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("authenticationProvider")
public class MyAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	UserDetailsService userDetailsService;
    		
	@Autowired
    PasswordEncoder passwordEncoder;
	
    
    @SuppressWarnings("deprecation")
	@Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // [1] token 中的用户名和密码都是用户输入的，不是数据库里的
    	log.info("自定义验证提供者");
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String name = token.getName();
        Boolean wxFlg = false;
        if(name.startsWith("wx_")){
        	wxFlg = true;
        	name = name.replace("wx_", "");
        }
        
        
        // [2] 使用用户名从数据库读取用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
        
        // [3] 检查用户信息
        if(userDetails == null) {
            throw new UsernameNotFoundException("用户不存在");
        } else if (!userDetails.isEnabled()){
            throw new DisabledException("用户已被禁用");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("账号已过期");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("账号已被锁定");
        } else if (!userDetails.isCredentialsNonExpired()) {
            throw new LockedException("凭证已过期");
        }
        // [4] 根据不同的情况比对密码
        String encryptedPassword = userDetails.getPassword();   // 数据库用户的密码，一般都是加密过的
        String inputPassword = (String) token.getCredentials(); // 用户输入的密码
        if (wxFlg) {
            // 通过 Wechat自动登录    密码已经加密
        	log.info("通过 Wechat自动登录");
        } else {
            // 根据加密算法加密用户输入的密码，然后和数据库中保存的密码进行比较
        	log.info("MD5验证");
            if(!passwordEncoder.isPasswordValid(encryptedPassword,inputPassword,null)) {
                throw new BadCredentialsException("用户名或密码无效");
            }
        }
        // [5] 成功登陆，把用户信息提交给 Spring Security
        // 把 userDetails 作为 principal 的好处是可以放自定义的 UserDetails，这样可以存储更多有用的信息，而不只是 username，
        // 默认只有 username，这里的密码使用数据库中保存的密码，而不是用户输入的明文密码，否则就暴露了密码的明文
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
   
}