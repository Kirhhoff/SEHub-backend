package com.scut.se.sehubbackend.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class WebAuthenticationManager extends ProviderManager {

    @Autowired
    public WebAuthenticationManager(List<AuthenticationProvider> providers) {
        super(providers);
    }

}
