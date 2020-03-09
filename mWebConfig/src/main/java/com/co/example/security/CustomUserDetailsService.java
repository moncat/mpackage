package com.co.example.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.ValidateUtil;
import com.co.example.entity.admin.TAdmin;
import com.co.example.entity.admin.aide.TAdminVo;
import com.co.example.service.admin.TAdminService;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    
	@Autowired 
    private TAdminService tAdminService;
	
    @Override
    public UserDetails loadUserByUsername(String userName) throws AuthenticationException {
    	if (StringUtils.isBlank(userName)) {
        	throw new BadCredentialsException("账户不能为空！！");
        }
        TAdmin admin = tAdminService.queryByLoginName(userName);
        if (admin == null) {
            throw new UsernameNotFoundException("该账户不存在！");
        }
        if(admin.getIsActive() == Constant.STATUS_NOT_ACTIVE){
        	throw new BadCredentialsException("该账户已被锁定，请联系管理员！");
        }
        boolean flg = ValidateUtil.isMobile(userName);
        CustomUserDetails customUserDetails = new CustomUserDetails((TAdminVo) admin,flg);
        return customUserDetails; 
    }

}
