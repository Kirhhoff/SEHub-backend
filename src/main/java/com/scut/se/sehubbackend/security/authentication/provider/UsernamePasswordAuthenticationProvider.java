package com.scut.se.sehubbackend.security.authentication.provider;

import com.scut.se.sehubbackend.utils.UserDetailsAdapter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * <p>使用username和password对请求进行实际认证的provider</p>
 * <p>成功鉴权后，principal将直接放授权后的Member</p>
 */
@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final UserDetailsAdapter userDetailsAdapter;

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
            return new UsernamePasswordAuthenticationToken(
                    userDetailsAdapter.from(userDetailsInDatabase),
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
