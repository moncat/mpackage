package com.co.example.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.co.example.common.constant.Constant;
import com.co.example.entity.user.TUser;
import com.co.example.entity.user.aide.TUserVo;
import com.co.example.service.user.TUserService;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    
	@Autowired 
    private TUserService tUserService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws AuthenticationException {
    	
    	if (StringUtils.isBlank(userName)) {
        	throw new BadCredentialsException("手机号不能为空！！");
        }
        TUser user = tUserService.queryByLoginName(userName);
        
        if (user == null) {
            throw new UsernameNotFoundException("该手机号不存在！");
        }
        if(user.getIsActive() == Constant.STATUS_NOT_ACTIVE){
        	throw new BadCredentialsException("该账户已被锁定，请联系管理员！");
        }
        
        CustomUserDetails customUserDetails = new CustomUserDetails((TUserVo) user);
        return customUserDetails; 

    }
    

}
