package com.balawo.oauth.config;


import com.balawo.oauth.oauth2.MyClientDetailsService;
import com.balawo.oauth.oauth2.MyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author yan
 * @date 2022-09-13
 * OAuth2.0认证中心的服务器配置类
 * 参考 https://segmentfault.com/a/1190000041115315
 */
@Configuration
@EnableAuthorizationServer //提供/oauth/authorize,/oauth/token,/oauth/check_token,/oauth/confirm_access,/oauth/error
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    Logger logger = LoggerFactory.getLogger(AuthorizationServerConfig.class);

    @Autowired
    TokenStore tokenStore;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisConnectionFactory connectionFactory;
    @Autowired
    MyUserDetailsService userDetailsService;

    /**
     * 令牌访问端点的安全策略
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //表示支持 client_id和client_secret做登录认证
        security.allowFormAuthenticationForClients();
        security.tokenKeyAccess("permitAll()");//开启oauth/token_key验证端口认证权限访问
        security.checkTokenAccess("permitAll()");//oauth/check_token验证端口认证权限访问;
    }


    /**
     * 令牌访问端点的配置：endpoints
     * 1.使用我们自己的授权管理器(AuthenticationManager)和自定义的用户详情服务(UserDetailsService)
     * 配置了密码模式所需要的AuthenticationManager
     * 配置了令牌管理服务，AuthorizationServerTokenServices
     * 配置/oauth/token申请令牌的uri只允许POST提交。
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //配置密码模式 authenticationManager
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService) //自定义的用户详情服务
                //令牌管理服务，无论哪种模式都需要
                .tokenServices(tokenServices())
                //只允许POST提交访问令牌，uri: /oauth/token
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);

    }

    /**
     * 客户端信息
     *
     * @return
     */
    @Bean
    public ClientDetailsService clientDetailsService() {
        MyClientDetailsService detailsService = new MyClientDetailsService(redisTemplate, connectionFactory);
        return detailsService;
    }

    /**
     * 系统第三方客户端配置:
     * 所谓的客户端就是有哪些应用会访问我们的系统，
     * 我们的认证服务器会决定给哪些第三方应用client去发送令牌。
     * 请求地址：http://localhost:9002/oauth/token。body中携带参数 grant_type=password&client_id=client_id1&client_secret=client_secret1&scope=all&username=u1&password=p1 就可以访问到token
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());

    }


    /**
     * 令牌服务的配置
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        //支持令牌的刷新
        services.setSupportRefreshToken(true);
        //令牌服务
        services.setTokenStore(tokenStore);
        //access_token的过期时间
        services.setAccessTokenValiditySeconds(60 * 60);
        //refresh_token的过期时间
        services.setRefreshTokenValiditySeconds(60 * 60 * 24 * 3);
        return services;
    }

}
