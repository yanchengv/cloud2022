package com.balawo.gw.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author yan
 * @date 2022-09-13
 */

@Data
public class LoginUser implements UserDetails {

    private static final long serialVersionUID = 1L;
    private String loginName;
    private String loginPassword;
    private String mobile;
    private  Collection<GrantedAuthority> authorities;

    public LoginUser(String loginName, String loginPassword, String mobile, Collection<GrantedAuthority> authorities) {
        this.loginName = loginName;
        this.loginPassword = loginPassword;
        this.mobile = mobile;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return loginPassword;
    }

    @Override
    public String getUsername() {
        return mobile;
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
