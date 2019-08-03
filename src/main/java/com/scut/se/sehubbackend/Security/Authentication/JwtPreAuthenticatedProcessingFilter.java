package com.scut.se.sehubbackend.Security.Authentication;

import com.scut.se.sehubbackend.Security.Jwt.JwtManager;
import org.jose4j.jwt.MalformedClaimException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public class JwtPreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {

    final JwtManager jwtManager;

    @Autowired
    public JwtPreAuthenticatedProcessingFilter(ProviderManager providerManager, JwtManager jwtManager){
        setAuthenticationManager(providerManager);
        this.jwtManager = jwtManager;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        try {
            return jwtManager.decode(getJwt(request));
        } catch (MalformedClaimException e) {
            return null;
        }
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "";
    }

    private String getJwt(HttpServletRequest request){
        Cookie[] cookies=request.getCookies();
        if (cookies!=null){
            for(Cookie cookie: cookies)
                if (cookie.getName().equals("token"))
                    return cookie.getValue();
        }
        return null;
    }

}
