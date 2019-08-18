package com.scut.se.sehubbackend.security.authentication;

import com.scut.se.sehubbackend.security.UserDetailsAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * <p>功能：根据用户名从数据库中提取用户</p>
 * <p>定位：从数据库层面到应用层面数据对象的转化器</p>
 */
@Component
public class DatabaseUserDetailsService implements UserDetailsService {

    final UserDetailsAdapter userDetailsAdapter;

    @Autowired
    public DatabaseUserDetailsService(UserDetailsAdapter userDetailsAdapter) {
        this.userDetailsAdapter = userDetailsAdapter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails usernameOnlyUserDetails=toUserDetails(username);
        UserDetails fullUserDetails= userDetailsAdapter.to(userDetailsAdapter.from(usernameOnlyUserDetails));

        if (fullUserDetails!=null)
            return fullUserDetails;
        else
            throw new UsernameNotFoundException("Can not find the studentNO");
    }

    private UserDetails toUserDetails(String username){
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };
    }
}
