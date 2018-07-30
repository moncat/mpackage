package com.co.example.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.co.example.common.constant.Constant;
import com.co.example.entity.system.TRole;
import com.co.example.entity.user.aide.TUserVo;


public class CustomUserDetails extends TUserVo implements UserDetails {

	private static final long serialVersionUID = 1L;

	public CustomUserDetails(TUserVo user) {
		if (user != null){
			this.setId(user.getId());
			this.setLoginName(user.getLoginName());
			this.setDisplayName(user.getDisplayName());
			this.setPassword(user.getPassword());
			this.setEmail(user.getEmail());
			this.setMobilePhone(user.getMobilePhone());
			this.setVisitCount(user.getVisitCount());
			this.setLastTime(user.getLastTime());
			this.setLastIp(user.getLastIp());
			this.setCreateTime(user.getCreateTime());
			this.setUpdateTime(user.getUpdateTime());
			this.setCreateBy(user.getCreateBy());
			this.setUpdateBy(user.getUpdateBy());
			this.setItemOrder(user.getItemOrder());
			this.setIsActive(user.getIsActive());
			this.setDelFlg(user.getDelFlg());
			this.setRoles(user.getRoles());
			
			this.setSex(user.getSex());
			this.setPersonName(user.getPersonName());
			this.setHeadImage(user.getHeadImage());
			this.setBirthday(user.getBirthday());
			this.setAge(user.getAge());
			this.setQq(user.getQq());
			this.setWx(user.getWx());
			this.setQqOpenId(user.getQqOpenId());
			this.setWxOpenId(user.getWxOpenId());
		}
	}

	
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		List<TRole> userRoles = this.getRoles();
		if (userRoles != null){
			for (TRole role : userRoles){
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
				authorities.add(authority);
			}
		}
		return authorities;
	}
	
	
	
	

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public String getUsername() {
		return super.getLoginName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
//		Byte isActive = super.getIsActive();
//		if(Constant.STATUS_ACTIVE == isActive){
//			return true;
//		}
//		return false;
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		Byte delFlg = super.getDelFlg();
		if(Constant.NO == delFlg){
			return true;
		}
		return false;
	}
}