package com.balawo.oauth.oauth2;

import com.balawo.oauth.model.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.Collection;


/**
 * @author yan
 * @date 2022-09-13
 * 用户名 u1，密码p1，角色user
 * 请求地址：http://localhost:9002/oauth/token。body中携带参数 grant_type=password&client_id=client_id1&client_secret=client_secret1&scope=all&username=u1&password=p1 就可以访问到token
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.warn("==================MyUserDetailsService/UserDetails...----username="+username);
        //查询登录用户信息
//        LoginUser loginUser = new LoginUser();
//        loginUser.setMobile("u1"); //loginUser的mobile复制给UserDetails的username
//        loginUser.setLoginPassword(new BCryptPasswordEncoder().encode("p1"));
////        Collection<GrantedAuthority> authorities = new ArrayList<>();
////        SimpleGrantedAuthority s = new SimpleGrantedAuthority("admin");
////        authorities.add(s);
//        loginUser.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
//        return new LoginUser(username,new BCryptPasswordEncoder().encode("p1"),"110",AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        return new User("u1",new BCryptPasswordEncoder().encode("p1"),
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,update_auth,read_auth"));

    }


}
