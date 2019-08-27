package com.scut.se.sehubbackend.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * <p>SpringBoot要求必须提供一个默认的ProviderManager，因此这里提供一个，仅设置构造函数</p>
 */
@Component
public class WebAuthenticationManager extends ProviderManager {

    @Autowired
    public WebAuthenticationManager(List<AuthenticationProvider> providers) {
        super(providers);
    }

}
