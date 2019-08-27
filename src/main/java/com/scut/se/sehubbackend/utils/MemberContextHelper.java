package com.scut.se.sehubbackend.utils;

import com.scut.se.sehubbackend.domain.member.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @see ContextHelper
 */
@Component
public class MemberContextHelper implements ContextHelper<Member> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Member getCurrentPrincipal() {
        return (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Authentication getCurrentAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
