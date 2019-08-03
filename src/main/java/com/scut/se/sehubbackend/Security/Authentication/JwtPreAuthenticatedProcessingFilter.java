package com.scut.se.sehubbackend.Security.Authentication;

import com.scut.se.sehubbackend.Config.WebConfig;
import com.scut.se.sehubbackend.Security.JWT.JwtManager;
import org.jose4j.jwt.MalformedClaimException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class JwtPreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {

    private String authorityKey;
    final JwtManager jwtManager;

    @Autowired
    public JwtPreAuthenticatedProcessingFilter(ProviderManager providerManager, WebConfig webConfig, JwtManager jwtManager){
        setAuthenticationManager(providerManager);
        authorityKey=webConfig.getAuthorityKey();
        this.jwtManager = jwtManager;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        try {
            //TODO 改用Cookie而不是Header来携带认证信息
            return jwtManager.decode(getJWT(request));
        } catch (MalformedClaimException e) {
            return null;
        }
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "";
    }

    private String getJWT(HttpServletRequest request){
        return request.getHeader(authorityKey);
    }

}
