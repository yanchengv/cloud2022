package com.balawo.oauth.oauth2;

import com.balawo.oauth.model.LoginUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author yan
 * @date 2022-09-13
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //查询登录用户信息
        LoginUser loginUser = new LoginUser();
        loginUser.setMobile("110");
        loginUser.setRealName("张三");
        return loginUser;
    }

}
