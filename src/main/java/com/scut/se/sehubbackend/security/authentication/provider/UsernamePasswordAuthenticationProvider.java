package com.scut.se.sehubbackend.security.authentication.provider;

import com.scut.se.sehubbackend.security.UserDetailsAdapter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    final UserDetailsService userDetailsService;
    final UserDetailsAdapter userDetailsAdapter;

    public UsernamePasswordAuthenticationProvider(UserDetailsService userDetailsService, UserDetailsAdapter userDetailsAdapter) {
        this.userDetailsService = userDetailsService;
        this.userDetailsAdapter = userDetailsAdapter;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username=(String)authentication.getPrincipal();
        String password=(String)authentication.getCredentials();
        UserDetails userDetailsInDatabase=userDetailsService.loadUserByUsername(username);

        if (password.equals(userDetailsInDatabase.getPassword())){
            Authentication successAuthentication= new UsernamePasswordAuthenticationToken(
                    userDetailsAdapter.from(userDetailsInDatabase),
                    null,
                    userDetailsInDatabase.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(successAuthentication);
            return successAuthentication;
        }else
            throw new AuthenticationServiceException("Password match failed!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
