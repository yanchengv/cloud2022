package com.balawo.gw.modle;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author yan
 * @date 2022-09-09
 */
@Data
public class UserPrincipal implements UserDetails {
    /**
     * 权限标识
     */
    private List<GrantedAuthority> authorities;

//    public static UserPrincipal build() {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        return new UserPrincipal(1, "yan", 1, authorities);
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return "123";
    }

    @Override
    public String getUsername() {
        return null;
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
