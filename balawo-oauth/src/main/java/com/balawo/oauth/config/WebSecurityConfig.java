package com.balawo.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author yan
 * @date 2022-09-13
 * spring security 基础配置
 */
@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 密码编码器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder()  {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置认证管理器，oauth2需要
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     *
     * @param auth
     * @throws Exception
     * 方便测试，直接将用户信息存储在内存中,
     * 用户名u1，密码p1，角色admin
     * 用户名u2，密码p2，角色user
     * 请求地址：http://localhost:9002/oauth/token。body中携带参数 grant_type=password&client_id=client_id1&client_secret=client_secret1&scope=all&username=u1&password=p1 就可以访问到token
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("u1")
                .password(new BCryptPasswordEncoder().encode("p1"))
                .roles("admin")
                .and()
                .withUser("u2")
                .password(new BCryptPasswordEncoder().encode("p2"))
                .roles("user");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();// 解决不允许显示在iframe的问题
        http.headers().frameOptions().disable().cacheControl();
        http.requestMatchers()
                .antMatchers("/oauth/**","/auth/login","/authentication/base","/logout")
                .and()
                .authorizeRequests()
                .antMatchers("/login*","/auth/login","/auth/code/captcha","/auth/logout")
                .permitAll()//不验证，直接放行
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()//允许表单登录
                .loginPage("/auth/login")//登录页面地址
                .loginProcessingUrl("/authentication/base");//登录处理的url

//                .antMatchers("/**").permitAll(); //允许匿名访问所有接口 主要是 oauth 接口
    }



}
