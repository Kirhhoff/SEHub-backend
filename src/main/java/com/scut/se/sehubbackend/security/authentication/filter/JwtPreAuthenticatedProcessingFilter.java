package com.scut.se.sehubbackend.security.authentication.filter;

import com.scut.se.sehubbackend.security.jwt.JwtManager;
import org.jose4j.jwt.MalformedClaimException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>识别token的Filter</p>
 * <p>在UsernamePasswordFilter之前工作，如果没有token的话才交给后面的Filter使用username和password</p>
 * <p>获取token的方法是尝试读取请求头中的"token"字段，然后解析得到学号，交给后续provider来认证</p>
 */
@Component
public class JwtPreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {

    private final JwtManager jwtManager;

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
//        Cookie[] cookies=request.getCookies();
//        if (cookies!=null){
//            for(Cookie cookie: cookies)
//                if (cookie.getName().equals("token"))
//                    return cookie.getValue();
//        }
//        return null;
        return request.getHeader("token");
    }

}
