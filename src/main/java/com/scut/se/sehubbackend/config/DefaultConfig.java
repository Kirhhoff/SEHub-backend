package com.scut.se.sehubbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * <p>关于默认值的配置类</p>
 */
@Configuration
public class DefaultConfig {

    @Value("${default.password}") String defaultPassword;

    @Bean public String defaultPassword(){ return defaultPassword; }
}
