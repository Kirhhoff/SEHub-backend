package com.scut.se.sehubbackend.unit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.doReturn;


@RunWith(PowerMockRunner.class)
public class SimpleAuthorityManagerTest {
//
//    @InjectMocks
//    SimpleAuthorityManager authorityManager=new SimpleAuthorityManager();
//
//    UserAuthentication user;//测试用的、存在的user
//
//    UserAuthentication notExistuser;//不存在的用户
//
//    Set<GrantedAuthority> grantedAuthorities= new HashSet<>();
//
//    @Mock
//    UserAuthenticationRepository userRepository;//数据库
//
//    Optional<UserAuthentication> opt ;//数据库返回的Optional User
//
//    GrantedAuthority existSimpleGrantedAuthority;//已存在的权限
//
//    GrantedAuthority notExistSimpleGrantedAuthority;//未存在的权限
//
//    @Before
//    public void before(){
//        existSimpleGrantedAuthority=new SimpleGrantedAuthority("exist");
//        notExistSimpleGrantedAuthority=new SimpleGrantedAuthority("not exist");
//        grantedAuthorities.add(existSimpleGrantedAuthority);//将存在的权限加载入list中
//        user=UserAuthentication.builder().studentNO("123").userInformation(UserInformation.builder().name("someone").build()).build();
//        user.setAuthorityRecords(grantedAuthorities);
//        notExistuser=UserAuthentication.builder().build();
//        opt=Optional.ofNullable(user);
//        doReturn(opt).when(userRepository).findById("123");
//
//    }
//
//    @Test
//    public void testAddAuthority(){
//        /**
//         * 不存在的用户应该返回false
//         */
//        assertEquals(false,authorityManager.addAuthority(notExistuser,existSimpleGrantedAuthority));
//        assertEquals(true,authorityManager.addAuthority(user,existSimpleGrantedAuthority));
//
//        /**
//         * 如果添加已有的权限，权限的总数不会变，且权限还是原来的权限
//         */
//        authorityManager.addAuthority(user,existSimpleGrantedAuthority);
//        assertEquals(1,user.getAuthorityRecords().size());
//        assertEquals(true,user.getAuthorityRecords().contains(UserAuthorityRecord.builder().owner(user).authority("exist")));
//
//        /**
//         * 如果添加未有的权限，权限的总数会加一
//         */
//        authorityManager.addAuthority(user,notExistSimpleGrantedAuthority);//会报错
//        assertEquals(2,user.getAuthorityRecords().size());
//        assertEquals(true,user.getAuthorityRecords().contains(UserAuthorityRecord.builder().owner(user).authority("exist")));
//        assertEquals(true,user.getAuthorityRecords().contains(UserAuthorityRecord.builder().owner(user).authority("not exist")));
//
//    }
//
//    @Test
//    public void testRemoveAuthority(){
//        /**
//         * 不存在的用户应该返回false
//         */
//        assertEquals(false,authorityManager.removeAuthority(notExistuser,existSimpleGrantedAuthority));
//        assertEquals(true,authorityManager.removeAuthority(user,existSimpleGrantedAuthority));
//
//        /**
//         * 如果移除未有的权限，权限的总数不会变，且权限还是原来的权限
//         */
//        authorityManager.addAuthority(user,existSimpleGrantedAuthority);
//        authorityManager.removeAuthority(user,notExistSimpleGrantedAuthority);
//        assertEquals(1,user.getAuthorityRecords().size());
//        assertEquals(true,user.getAuthorityRecords().contains(UserAuthorityRecord.builder().owner(user).authority("exist")));
//
//        /**
//         * 如果移除已有的权限，权限的总数会减1
//         */
//        authorityManager.removeAuthority(user,existSimpleGrantedAuthority);
//        assertEquals(0,user.getAuthorityRecords().size());
//
//    }
}
