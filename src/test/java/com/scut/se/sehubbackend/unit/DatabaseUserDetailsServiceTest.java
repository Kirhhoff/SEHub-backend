package com.scut.se.sehubbackend.unit;


import com.scut.se.sehubbackend.dao.member.MemberRepository;
import com.scut.se.sehubbackend.domain.member.Authority;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.security.AuthorityUtil;
import com.scut.se.sehubbackend.security.Role;
import com.scut.se.sehubbackend.security.authentication.DatabaseUserDetailsService;
import com.scut.se.sehubbackend.utils.Member2UserDetailsAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseUserDetailsServiceTest {

    DatabaseUserDetailsService databaseUserDetailsService;
    Member2UserDetailsAdapter mockUserDetailsUtil;
    @Mock MemberRepository mockMemberRepository;
    @Spy AuthorityUtil authorityUtil;

    Long mockId =123L;
    Long nonExistedId=777L;
    Member mockMember=new Member();
    String mockPassword ="And, I, am, Iron Man!";
    List<Authority> authorityList=new ArrayList<>();
    Collection<GrantedAuthority> grantedAuthorities=new ArrayList<>();

    /**
     * <p>测试：存在的的id</p>
     * <p>对于返回的UserDetails：</p>
     * <ul>
     *     <li>username：与mock的id相同</li>
     *     <li>password：与mock的password相同</li>
     *     <li>grantedAuthorities：由原先的Authority数据对象转换而来</li>
     * </ul>
     */
    @Test
    public void testExistedMember(){
        UserDetails existedUserDetails=databaseUserDetailsService.loadUserByUsername(String.valueOf(mockId));

        assertEquals(String.valueOf(mockId),existedUserDetails.getUsername());
        assertEquals(grantedAuthorities,existedUserDetails.getAuthorities());
        assertEquals(mockPassword,existedUserDetails.getPassword());
    }

    /**
     * 测试：不存在的id抛出异常
     */
    @Test(expected = UsernameNotFoundException.class)
    public void testNonExistedMember(){
        databaseUserDetailsService.loadUserByUsername(String.valueOf(nonExistedId));
    }


    @Before
    public void before(){
        //因为存在二级依赖，必须手动解决第二层的依赖关系（文档中警告Mockito不是一个依赖注入框架含义即在此..）
        injectMockDependency();

        //设置authorityList及与其响应的grantedAuthorities,这也算是一种验证吧
        configureAuthorityList();
        configureGrantedAuthorities();

        //mock一个Member
        mockMember.setStudentNumber(mockId);
        mockMember.setPassword(mockPassword);
        mockMember.setAuthorityList(authorityList);

        //mock MemberRepository
        when(mockMemberRepository.findById(mockId)).thenReturn(ofNullable(mockMember));
        when(mockMemberRepository.findById(nonExistedId)).thenReturn(ofNullable(null));
    }

    private void configureAuthorityList(){
        Authority pureAuthority=new Authority();
        Authority roleAuthority=new Authority();

        pureAuthority.setAuthorityName("PROCESS_APPLICATION");
        roleAuthority.setAuthorityName(new Role("MINISTER").getAuthority());

        authorityList.add(pureAuthority);
        authorityList.add(roleAuthority);
    }

    private void configureGrantedAuthorities(){
        GrantedAuthority pureAuthority= new SimpleGrantedAuthority("PROCESS_APPLICATION") ;
        GrantedAuthority roleAuthority=new SimpleGrantedAuthority(new Role("MINISTER").getAuthority());

        grantedAuthorities.add(pureAuthority);
        grantedAuthorities.add(roleAuthority);
    }

    private void injectMockDependency(){
        mockUserDetailsUtil=new Member2UserDetailsAdapter(mockMemberRepository,authorityUtil);
        databaseUserDetailsService=new DatabaseUserDetailsService(mockUserDetailsUtil);
    }
}
