package com.balawo.demo5.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

/**
 * 解析从网关发送的token，权限配置
 *
 * @author lengjixiang
 * @since 2021-11-18
 */
@Slf4j
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private static Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = httpServletRequest.getHeader("json-token");
        logger.info("TokenAuthenticationFilter=============={}",token);
        if (token != null) {
            String json = new String(Base64.getDecoder().decode(token));
            JSONObject userJson = JSON.parseObject(json);
            //客户端模式没经过用户名密码登录，所以得不到LoginUser信息
            if (!"client_credentials".equals(userJson.getString("grant_type"))) {
                String principal = userJson.getString("principal");
                log.info("principal=======: {}", principal);
                //转换为loginUser
                User loginUser = JSON.parseObject(principal, User.class);
                JSONArray authoritiesArray = userJson.getJSONArray("authorities");
                String[] authorities = authoritiesArray.toArray(new String[authoritiesArray.size()]);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, AuthorityUtils.createAuthorityList(authorities));
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
