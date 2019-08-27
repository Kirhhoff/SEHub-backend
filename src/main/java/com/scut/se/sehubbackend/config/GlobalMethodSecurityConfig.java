package com.scut.se.sehubbackend.config;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * <p>按照SpringBoot的规范开启方法权限保护，使用的是PrePost方法保护</p>
 * <p>额外定义一个类是因为SpringBoot要求这个注解必须放在一个extends了GlobalMethodSecurityConfiguration类的类上</p>
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GlobalMethodSecurityConfig extends GlobalMethodSecurityConfiguration {
}
