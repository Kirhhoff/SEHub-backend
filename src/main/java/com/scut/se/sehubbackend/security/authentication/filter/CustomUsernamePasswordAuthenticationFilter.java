package com.scut.se.sehubbackend.security.authentication.filter;

import com.scut.se.sehubbackend.security.UserDetailsAdapter;
import com.scut.se.sehubbackend.security.jwt.JwtManager;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    final JwtManager jwtManager;
    final UserDetailsAdapter userDetailsAdapter;

    @Autowired
    public CustomUsernamePasswordAuthenticationFilter(JwtManager jwtManager, AuthenticationManager authenticationManager, UserDetailsAdapter userDetailsAdapter, AuthenticationSuccessHandler authenticationSuccessHandler){
        this.jwtManager = jwtManager;
        this.userDetailsAdapter = userDetailsAdapter;
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
//        setContinueChainBeforeSuccessfulAuthentication(true);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request,response,chain,authResult);
        String token="";
        try {
            token=jwtManager.encode(userDetailsAdapter.to(authResult.getPrincipal()));
        } catch (JoseException e) {
            e.printStackTrace();
        }
//        response.addCookie(new Cookie("token",token));
        response.setHeader("token",token);
    }


}
