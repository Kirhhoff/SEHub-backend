package com.scut.se.sehubbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Value("${default.password}")
    String defaultPassword;

    @Bean public String defaultPassword(){ return defaultPassword; }
}
