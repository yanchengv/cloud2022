package com.balawo.oauth2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yan
 * @date 2022-09-21
 */
public class MyClientDetailsService implements ClientDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
//        clients.inMemory()
//                //配置client_id
//                .withClient("adminc")
//                //配置client-secret
//                .secret(passwordEncoder.encode("123123"))
//                //配置访问token的有效期
//                .accessTokenValiditySeconds(3600)
//                .redirectUris("http://www.baidu.com")
//                //配置申请的权限范围
//                .scopes("all")
//                //配置grant_type 表示授权模式 授权码模式获取code: localhost:9003/oauth/authorize?response_type=code&client_id=adminc&redirect_uri=http://www.baidu.com&scope=all
//                //.authorizedGrantTypes("authorization_code");
//                .authorizedGrantTypes("password");

        Logger logger = LoggerFactory.getLogger(MyClientDetailsService.class);
        logger.warn("loadClientByClientId=====clientId:"+clientId);
        BaseClientDetails bd = new BaseClientDetails();
        bd.setClientId("adminc");
        bd.setClientSecret(passwordEncoder.encode("123123"));
        bd.setAccessTokenValiditySeconds(3600);
        Set<String> authType = new HashSet<>();
        authType.add("password");
        authType.add("refresh_token");
        bd.setAuthorizedGrantTypes(authType);
        bd.setScope(Arrays.asList("all",","));
        return bd;
    }
}
