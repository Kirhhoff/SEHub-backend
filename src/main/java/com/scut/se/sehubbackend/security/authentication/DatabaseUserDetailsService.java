package com.scut.se.sehubbackend.security.authentication;

import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dao.member.MemberRepository;
import com.scut.se.sehubbackend.security.AuthorityUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

/**
 * <p>功能：根据用户名从数据库中提取用户</p>
 * <p>定位：从数据库层面到应用层面数据对象的转化器</p>
 */
@Component
public class DatabaseUserDetailsService implements UserDetailsService {

    final MemberRepository memberRepository;
    final AuthorityUtil authorityUtil;

    public DatabaseUserDetailsService(MemberRepository memberRepository, AuthorityUtil authorityUtil) {
        this.memberRepository = memberRepository;
        this.authorityUtil = authorityUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> memberOptional=memberRepository.findById(Long.valueOf(username));
        if (memberOptional.isPresent())
            return toUserDetails(memberOptional.get());
        else
            throw new UsernameNotFoundException("Can not find the studentNO");
    }

    /**
     * 将数据库中层面的用户转化为Spring Security需要的认证形式
     * @param member 数据库中的用户
     * @return Spring Security需要的用户
     */
    private UserDetails toUserDetails(Member member){
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return authorityUtil.toGrantedAuthority(member.getAuthorityList());
            }

            @Override
            public String getPassword() {
                return member.getPassword();
            }

            @Override
            public String getUsername() {
                return String.valueOf(member.getStudentNumber());
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
