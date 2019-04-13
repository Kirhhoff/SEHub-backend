package com.scut.se.sehubbackend.Security.Authentication.provider;

import com.scut.se.sehubbackend.Domain.user.UserAuthentication;
import com.scut.se.sehubbackend.Domain.user.UserAuthorityRecord;
import com.scut.se.sehubbackend.Security.JWT.JWTManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JWTAuthenticationProvider implements AuthenticationProvider {

    @Autowired JWTManager jwtManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserAuthentication user=(UserAuthentication) authentication.getPrincipal();
        if(user==null)
            new AuthenticationServiceException("Fail to find the user");
        return new UsernamePasswordAuthenticationToken(//成功验证并生成授权
                user.getStudentNO(),
                null,
                UserAuthorityRecord.toGrantedAuthorities(user.getAuthorities())
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return !authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}