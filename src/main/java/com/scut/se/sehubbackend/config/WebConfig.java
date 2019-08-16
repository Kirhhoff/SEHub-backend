package com.scut.se.sehubbackend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class WebConfig {
    @Value("${request.uri.login}") String loginPage;//登陆URL
    @Value("${request.parameter.username}") String usernameParameter;//用户名参数key
    @Value("${request.parameter.password}") String passwordParameter;//密码参数key
    @Value("${request.header.authority}") String authorityKey;//认证头的key
}
