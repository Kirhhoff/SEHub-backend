package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.domain.member.Authority;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.enumeration.AuthorityEnum;
import com.scut.se.sehubbackend.enumeration.AuthorityOperation;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.security.ContextHelper;
import com.scut.se.sehubbackend.security.authorization.interfaces.AuthorizationDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {


    final MemberService memberService;
    final AuthorizationDecisionManager decisionManager;
    final ContextHelper<Member> contextHelper;

    public AuthorityService(MemberService memberService, AuthorizationDecisionManager decisionManager, ContextHelper<Member> contextHelper) {
        this.memberService = memberService;
        this.decisionManager = decisionManager;
        this.contextHelper = contextHelper;
    }

    public void grant(Long id, AuthorityEnum authority) throws InvalidIdException {
        authorityAlter(id,authority,AuthorityOperation.Grant);
    }

    public void remove(Long id, AuthorityEnum authority) throws InvalidIdException {
        authorityAlter(id,authority,AuthorityOperation.Remove);
    }

    private void authorityAlter(Long id, AuthorityEnum authority, AuthorityOperation operation) throws InvalidIdException {
        Member target=memberService.findById(id);
        if(decisionManager.decide(target,authority)){
            Authority newAuthority=Authority.builder().authorityName(String.valueOf(authority)).build();
            switch (operation){
                case Grant: target.addAuthority(newAuthority);
                    break;
                case Remove: target.removeAuthority(newAuthority);
                    break;
            }
            memberService.save(target);
        }else
            throw new AccessDeniedException("");
    }


//    public


}
