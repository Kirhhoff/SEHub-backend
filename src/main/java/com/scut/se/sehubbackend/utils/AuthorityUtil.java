package com.scut.se.sehubbackend.utils;

import com.scut.se.sehubbackend.domain.member.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>将Authority数据对象转化为Spring的GrantedAuthority的工具类</p>
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
