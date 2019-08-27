package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.member.AuthorityRepository;
import com.scut.se.sehubbackend.domain.member.Authority;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.enumeration.AuthorityEnum;
import com.scut.se.sehubbackend.enumeration.AuthorityOperation;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.utils.ContextHelper;
import com.scut.se.sehubbackend.security.authorization.AuthorizationDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorityService {
    private final MemberService memberService;
    private final AuthorizationDecisionManager decisionManager;
    private final ContextHelper<Member> contextHelper;
    private final AuthorityRepository authorityRepository;

    public AuthorityService(MemberService memberService, AuthorizationDecisionManager decisionManager, ContextHelper<Member> contextHelper, AuthorityRepository authorityRepository) {
        this.memberService = memberService;
        this.decisionManager = decisionManager;
        this.contextHelper = contextHelper;
        this.authorityRepository = authorityRepository;
    }

    /**
     * 授予某个id某项权限
     */
    public void grant(Long id, AuthorityEnum authority) throws InvalidIdException {
        authorityAlter(id,authority,AuthorityOperation.Grant);
    }

    /**
     * 剥夺某个id某项权限
     */
    public void remove(Long id, AuthorityEnum authority) throws InvalidIdException {
        authorityAlter(id,authority,AuthorityOperation.Remove);
    }

    /**
     * <p>对权限的实际操作方法</p>
     * @param id 要操作的用户id，即学号
     * @param authority 要操作的权限名称
     * @param operation 授予还是剥夺
     * @throws InvalidIdException 目标id不存在
     */
    private void authorityAlter(Long id, AuthorityEnum authority, AuthorityOperation operation) throws InvalidIdException {

        //获取操作者和目标
        Member currentMember=contextHelper.getCurrentPrincipal();
        Member target=memberService.findById(id);

        //决策是否有权执行，无权限时报异常
        if(decisionManager.decide(currentMember,target,authority)){
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

    /**
     * 获取所有具备某项权限的用户的email列表
     * @param authorityName 作为条件的权限名
     * @return 具备权限用户的email列表
     */
    public List<String> findAllEmailsWhoseOwnerHasAuthority(String authorityName){
        List<String> emails=new ArrayList<>();
        List<Authority> authorities=authorityRepository.findAllByAuthorityName(authorityName);
        for (Authority authority:authorities){
            //检测非空
            String email=authority.getAuthorityOwner().getEmail();
            if (email!=null)
                emails.add(email);
        }
        return emails;
    }
}
