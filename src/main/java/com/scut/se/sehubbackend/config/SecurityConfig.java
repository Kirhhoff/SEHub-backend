package com.scut.se.sehubbackend.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * <p>关于全局安全性的配置</p>
 * <p>配置内容主要包括：</p>
 * <ul>
 *     <li>所有url必须登陆后才能访问</li>
 *     <li>添加两个负责安全的Filter，分别是识别token的PreFilter和识别用户名密码的UsernamePasswordFilter</li>
 *     <li>关闭csrf保护</li>
 * </ul>
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AbstractPreAuthenticatedProcessingFilter abstractPreAuthenticatedProcessingFilter;
    private final UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;

    public SecurityConfig(AbstractPreAuthenticatedProcessingFilter abstractPreAuthenticatedProcessingFilter, UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter) {
        this.abstractPreAuthenticatedProcessingFilter = abstractPreAuthenticatedProcessingFilter;
        this.usernamePasswordAuthenticationFilter = usernamePasswordAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests().antMatchers("/**").authenticated()//所有url都必须被登陆后才可以访问
                .and()
                .addFilterAt(abstractPreAuthenticatedProcessingFilter, AbstractPreAuthenticatedProcessingFilter.class)
                .addFilterAt(usernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();
    }

}
