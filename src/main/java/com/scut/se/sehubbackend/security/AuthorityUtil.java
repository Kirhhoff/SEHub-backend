package com.scut.se.sehubbackend.security;

import com.scut.se.sehubbackend.domain.memberN.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 负责将Authority数据对象转化为Spring的GrantedAuthority
 */
@Component
public class AuthorityUtil {

    public GrantedAuthority toGrantedAuthority(Authority authority){
        return new SimpleGrantedAuthority(authority.getAuthorityName());
    }

    public Collection<? extends GrantedAuthority> toGrantedAuthority(Collection<Authority> authorities){
        Collection<GrantedAuthority> grantedAuthorities=new ArrayList<>();
        for (Authority authority:authorities)
            grantedAuthorities.add(toGrantedAuthority(authority));
        return grantedAuthorities;
    }
}