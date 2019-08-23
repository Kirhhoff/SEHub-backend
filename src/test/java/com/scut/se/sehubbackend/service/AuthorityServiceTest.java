package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.member.AuthorityRepository;
import com.scut.se.sehubbackend.dao.member.MemberRepository;
import com.scut.se.sehubbackend.domain.member.Authority;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.enumeration.AuthorityEnum;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.security.authorization.interfaces.AuthorizationDecisionManager;
import com.scut.se.sehubbackend.utils.MemberContextHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class AuthorityServiceTest {

    @MockBean MemberContextHelper memberContextHelper;
    @MockBean AuthorizationDecisionManager decisionManager;
    @Autowired AuthorityService authorityService;
    @Autowired AuthorityRepository authorityRepository;
    @Autowired MemberRepository memberRepository;


    /**
     * <p>测试具备变更权限时授权操作</p>
     * <p>变更后verify权限数据</p>
     */
    @Test
    @Transactional
    public void testGrant() throws InvalidIdException {
        setUpAuthorityForAuthorization(true);
        authorityService.grant(idOfTarget,authorityEnum);
        verifyGrantedData();
    }

    /**
     * <p>测试具备变更权限时删除权限操作</p>
     * <p>变更后verify权限数据</p>
     */
    @Test
    @Transactional
    public void testRemove() throws InvalidIdException {
        setUpAuthorityForAuthorization(true);
        setUpAuthorityExistingEnvironment();
        authorityService.remove(idOfTarget,authorityEnum);
        verifyRemovedData();
    }

    /**
     * 测试不具备变更权限的权限时下要求变更权限
     */
    @Test(expected = AccessDeniedException.class)
    public void testAuthorizeWithoutAuthority() throws InvalidIdException {
        setUpAuthorityForAuthorization(false);
        authorityService.grant(idOfTarget,authorityEnum);
    }

    @Before
    public void setUp() {
        target=Member.builder()
                .studentNumber(201730683314L)
                .department(null)
                .authorityList(new ArrayList<>())
                .build();
        memberRepository.save(target);
        idOfTarget=target.getStudentNumber();
    }
    private void setUpAuthorityForAuthorization(boolean hasAuthorityForAuthorization){ when(decisionManager.decide(any(),any())).thenReturn(hasAuthorityForAuthorization); }
    private void setUpAuthorityExistingEnvironment(){
        target.addAuthority(Authority.builder().authorityName(String.valueOf(authorityEnum)).build());
        memberRepository.saveAndFlush(target);
        verifyGrantedData();
    }

    private void verifyGrantedData(){
        List<Authority> authorities=authorityRepository.findAllByAuthorityOwner(target);
        Authority actualAuthority=authorities.get(0);
        target=memberRepository.findById(idOfTarget).get();

        assertEquals(target,actualAuthority.getAuthorityOwner());
        assertEquals(String.valueOf(authorityEnum),actualAuthority.getAuthorityName());
    }

    private void verifyRemovedData(){
        List<Authority> authorities=authorityRepository.findAllByAuthorityOwner(target);
        System.out.println(authorities);
        assertEquals(0,authorities.size());
    }

    private Member target;
    private Long idOfTarget;
    private AuthorityEnum authorityEnum=AuthorityEnum.Etiquette;
}