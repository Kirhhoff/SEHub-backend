package com.scut.se.sehubbackend.utils;

import com.scut.se.sehubbackend.dao.member.MemberRepository;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.security.AuthorityUtil;
import com.scut.se.sehubbackend.security.UserDetailsAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class Member2UserDetailsAdapter implements UserDetailsAdapter<Member> {

    final MemberRepository memberRepository;
    final AuthorityUtil authorityUtil;
    private Member currentMember;

    public Member2UserDetailsAdapter(MemberRepository memberRepository, AuthorityUtil authorityUtil) {
        this.memberRepository = memberRepository;
        this.authorityUtil = authorityUtil;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Member from(UserDetails userDetails) {
        return userDetails == null
                ? null
                : memberRepository.findById(Long.valueOf(userDetails.getUsername()))
                .orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails to(Member member) {
        return member == null
                ? null
                : new UserDetails() {
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
