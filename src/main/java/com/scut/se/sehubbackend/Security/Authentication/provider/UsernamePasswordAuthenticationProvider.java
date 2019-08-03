package com.scut.se.sehubbackend.Security.Authentication.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    final UserDetailsService userDetailsService;

    public UsernamePasswordAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username=(String)authentication.getPrincipal();
        String password=(String)authentication.getCredentials();
        UserDetails userDetailsInDatabase=userDetailsService.loadUserByUsername(username);

        if (password.equals(userDetailsInDatabase.getPassword())){
            return new UsernamePasswordAuthenticationToken(
                    userDetailsInDatabase,
                    null,
                    userDetailsInDatabase.getAuthorities()
            );
        }else
            throw new AuthenticationServiceException("Password match failed!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
