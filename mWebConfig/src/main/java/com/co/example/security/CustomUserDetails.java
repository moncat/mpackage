package com.co.example.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.co.example.common.constant.Constant;
import com.co.example.entity.admin.aide.TAdminVo;
import com.co.example.entity.system.TRole;

public class CustomUserDetails extends TAdminVo implements UserDetails {

	private static final long serialVersionUID = 1L;

	public CustomUserDetails(TAdminVo admin) {
		if (admin != null){
			this.setId(admin.getId());
			this.setLoginName(admin.getLoginName());
			this.setDisplayName(admin.getDisplayName());
			this.setPassword(admin.getPassword());
			this.setEmail(admin.getEmail());
			this.setMobilePhone(admin.getMobilePhone());
			this.setVisitCount(admin.getVisitCount());
			this.setLastTime(admin.getLastTime());
			this.setLastIp(admin.getLastIp());
			this.setCreateTime(admin.getCreateTime());
			this.setUpdateTime(admin.getUpdateTime());
			this.setCreateBy(admin.getCreateBy());
			this.setUpdateBy(admin.getUpdateBy());
			this.setItemOrder(admin.getItemOrder());
			this.setIsActive(admin.getIsActive());
			this.setDelFlg(admin.getDelFlg());
			this.setRoles(admin.getRoles());
			
			
		}
	}

	
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		List<TRole> adminRoles = this.getRoles();
		if (adminRoles != null){
			for (TRole role : adminRoles){
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
		Byte isActive = super.getIsActive();
		if(Constant.STATUS_ACTIVE == isActive){
			return true;
		}
		return false;
//		return true;
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