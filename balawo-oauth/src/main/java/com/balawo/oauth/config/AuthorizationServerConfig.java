package com.balawo.oauth.config;

import com.balawo.oauth.oauth2.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author yan
 * @date 2022-09-13
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    TokenStore tokenStore;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;


    /**
     * 令牌访问断点的安全策略
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        /**
         * 允许表单认证，可以通过提交表单来申请令牌
         */
        security.allowFormAuthenticationForClients();
        security.tokenKeyAccess("permitAll()");//oauth/token_key公开
        security.checkTokenAccess("permitAll()");//oauth/check_token公开;
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        /**
         * 系统端点配置：endpoints
         * 1.使用我们自己的授权管理器(AuthenticationManager)和自定义的用户详情服务(UserDetailsService)
         */
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /**
         * 系统第三方客户端配置:
         * 所谓的客户端就是有哪些应用会访问我们的系统，
         * 我们的认证服务器会决定给哪些第三方应用client去发送令牌。
         * 如果这个配置后我们之前配置文件中配置的clientId和clientSecret将不会起作用了
         */
        //目前我们的应用场景是在我们的app和我们的前端；我们不允许第三方来注册，所以用内存
        clients.inMemory()
                //客户端ID
                .withClient("client_id1")
                //客户端秘钥
                .secret(new BCryptPasswordEncoder().encode("client_secret1"))
                .accessTokenValiditySeconds(7200)//发出去的令牌,有效期是多少? 这里设置为2小时
                .authorizedGrantTypes("refresh_token","password")//针对当前应用客户端：yxm,所能支持的授权模式是哪些?总共5种(授权码模式：authorization_code;密码模式：password;客户端模式：client_credentials;简化模式：implicit;令牌刷新：refresh_token):这里只支持配置的:"refresh_token","password"。
                .scopes("all","read","write");//发出去的权限有哪些?之前前端请求携带了scope,此配置的scope用来指定前端发送scope的值必须在配置的里面或者不携带scope;默认为此处配置的scope
    }


}
