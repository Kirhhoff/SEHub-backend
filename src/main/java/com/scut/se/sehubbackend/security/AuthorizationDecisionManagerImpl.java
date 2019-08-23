package com.scut.se.sehubbackend.security;

import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.enumeration.AuthorityEnum;
import com.scut.se.sehubbackend.security.authorization.interfaces.AuthorizationDecisionManager;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationDecisionManagerImpl implements AuthorizationDecisionManager {
    @Override
    public Boolean decide(Member target, AuthorityEnum authority) {
        return true;
    }
}
