package com.scut.se.sehubbackend.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    final AbstractPreAuthenticatedProcessingFilter abstractPreAuthenticatedProcessingFilter;
    final UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;

    public SecurityConfig(AbstractPreAuthenticatedProcessingFilter abstractPreAuthenticatedProcessingFilter, UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter) {
        this.abstractPreAuthenticatedProcessingFilter = abstractPreAuthenticatedProcessingFilter;
        this.usernamePasswordAuthenticationFilter = usernamePasswordAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/**").authenticated()
                .and()
                .addFilterAt(abstractPreAuthenticatedProcessingFilter, AbstractPreAuthenticatedProcessingFilter.class)
                .addFilterAt(usernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();
    }

}
