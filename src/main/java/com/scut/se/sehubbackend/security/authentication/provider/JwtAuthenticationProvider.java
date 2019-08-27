package com.scut.se.sehubbackend.security.authentication.provider;

import com.scut.se.sehubbackend.utils.UserDetailsAdapter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * <p>对jwt请求进行实际认证的provider</p>
 * <p>成功鉴权后，principal将直接放授权后的Member</p>
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final UserDetailsAdapter userDetailsAdapter;

    public JwtAuthenticationProvider(UserDetailsService userDetailsService, UserDetailsAdapter userDetailsAdapter) {
        this.userDetailsService = userDetailsService;
        this.userDetailsAdapter = userDetailsAdapter;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username= (String) authentication.getPrincipal();
        UserDetails userDetails=userDetailsService.loadUserByUsername(username);

        if(userDetails==null)
            new AuthenticationServiceException("Fail to find the user");
        return new UsernamePasswordAuthenticationToken(//成功验证并生成授权
                userDetailsAdapter.from(userDetails),
                null,
                userDetails.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
