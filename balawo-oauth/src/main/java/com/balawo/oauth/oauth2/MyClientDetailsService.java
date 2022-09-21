package com.balawo.oauth.oauth2;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yan
 * @date 2022-09-16
 */
public class MyClientDetailsService implements ClientDetailsService {
    RedisTemplate redisTemplate;
    RedisConnectionFactory connectionFactory;

    public MyClientDetailsService() {
    }

    public MyClientDetailsService(RedisTemplate redisTemplate, RedisConnectionFactory connectionFactory) {
        this.redisTemplate = redisTemplate;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        BaseClientDetails clientDetails = new BaseClientDetails();
        //客户端ID
        clientDetails.setClientId("client_id1");
        //客户端秘钥
        clientDetails.setClientSecret(new BCryptPasswordEncoder().encode("client_secret1"));
        //发出去的权限有哪些?之前前端请求携带了scope,此配置的scope用来指定前端发送scope的值必须在配置的里面或者不携带scope;默认为此处配置的scope
        clientDetails.setScope(Arrays.asList("all",","));
        //针对当前应用客户端：client_id1,所能支持的授权模式是哪些?总共5种(授权码模式：authorization_code;密码模式：password;客户端模式：client_credentials;简化模式：implicit;令牌刷新：refresh_token)。
//        clientDetails.setAuthorizedGrantTypes(Arrays.asList("authorization_code,client_credentials,refresh_token,password",","));
        Set<String> authType = new HashSet<>();
        authType.add("password");
        authType.add("refresh_token");
        clientDetails.setAuthorizedGrantTypes(authType);
        //资源ID 唯一 比如订单服务作为一个资源，可以设置多个
//        clientDetails.setResourceIds(Arrays.asList("admin"));

        return clientDetails;
    }
}
