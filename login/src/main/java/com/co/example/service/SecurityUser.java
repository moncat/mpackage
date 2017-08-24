package com.co.example.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.co.example.entity.system.TRole;
import com.co.example.entity.user.TUsers;
import com.co.example.entity.user.aide.TUsersVo;

public class SecurityUser extends TUsersVo implements UserDetails
{

    private static final long serialVersionUID = 1L;
    public SecurityUser(TUsers user) {
    	TUsersVo userTmp = (TUsersVo)user;
        if(user != null)
        {
            this.setUserId(user.getUserId());
            this.setUserName(user.getUserName());
            this.setEmail(user.getEmail());
            this.setUserPassword(user.getUserPassword());
            this.setCreateTime(user.getCreateTime());
            this.setRoles(userTmp.getRoles());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        List<TRole> roles = this.getRoles();
        if(roles != null)
        {
            for (TRole role : roles) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
                authorities.add(authority);
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getUserPassword();
    }

    @Override
    public String getUsername() {
        return super.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
