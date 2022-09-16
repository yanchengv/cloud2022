package com.balawo.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
 * 安全配置的执行顺序应优先于资源服务器
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(2)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 密码编码器
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置认证管理器，oauth2需要
     *
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();// 解决不允许显示在iframe的问题
        http.headers().frameOptions().disable().cacheControl();
        http.requestMatchers()
                .antMatchers("/oauth/**", "/authentication/base", "/logout")
                .and()
                .authorizeRequests()//开启配置
                .antMatchers("/login*", "/auth/login", "/auth/code/captcha", "/auth/logout")
                .permitAll()//不验证，直接放行
                .anyRequest()//其他请求
                .authenticated()//验证   表示其他请求只需要登录就能访问
                .and()
                .formLogin()//允许表单登录
                .loginPage("/auth/login")//登录页面地址
                .loginProcessingUrl("/authentication/base");//登录处理的url

//                .antMatchers("/**").permitAll(); //允许匿名访问所有接口 主要是 oauth 接口
    }


}
