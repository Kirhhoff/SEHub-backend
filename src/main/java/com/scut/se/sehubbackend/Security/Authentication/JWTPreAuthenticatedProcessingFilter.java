package com.scut.se.sehubbackend.Security.Authentication;

import com.scut.se.sehubbackend.Config.WebConfig;
import com.scut.se.sehubbackend.Domain.user.UserAuthentication;
import com.scut.se.sehubbackend.Security.JWT.JWTManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class JWTPreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {

    private String authorityKey;
    @Autowired JWTManager jwtManager;

    @Autowired
    public JWTPreAuthenticatedProcessingFilter(ProviderManager providerManager, WebConfig webConfig){
        setAuthenticationManager(providerManager);
        authorityKey=webConfig.getAuthorityKey();
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return new UserAuthentication();
//        try {
//            return jwtManager.decode(getJWT(request));
//        } catch (MalformedClaimException e) {
//            return null;
//        }
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "";
    }

    private String getJWT(HttpServletRequest request){
        return request.getHeader(authorityKey);
    }

}
