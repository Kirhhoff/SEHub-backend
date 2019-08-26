package com.scut.se.sehubbackend.integration;

import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.security.jwt.JwtManager;
import com.scut.se.sehubbackend.utils.Member2UserDetailsAdapter;
import com.scut.se.sehubbackend.utils.MemberContextHelper;
import org.jose4j.lang.JoseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * 对Cookie登陆的集成测试<br/>
 * 测试是：模拟一个用户的登陆，结果包括<br/>
 * <ul>
 *     <li>登陆后返回个人信息</li>
 *     <li>响应中带有一个Cookie用于维持会话</li>
 * </ul>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class CookieLoginTest {

    @Autowired WebApplicationContext context;
    private MockMvc mockMvc;

    /**
     * 模拟数据库查询，只包含一个Mock的UserDetails
     */
    @MockBean UserDetailsService mockUserDetailsService;

    /**
     * 为了方便测试，Mock Jwt编码，使得返回的Cookie直接是裸的未加密学号
     * 侧重于Cookie生成，而不是加密，加密将独立单元测试
     */
    @MockBean JwtManager mockJwtManager;
    @MockBean Member2UserDetailsAdapter mockUserDetailsAdapter;
    @MockBean MemberContextHelper memberContextHelper;
    @Mock UserDetails mockUserDetails;
    String mockUsername="201730683314";
    String mockPassword="123456789";
    List<GrantedAuthority> mockAuthorities= new ArrayList<>();
    Member mockMember;



    /**
     * 用存在的用户进行登陆测试，应当返回Cookie和200状态(成功登陆后返回个人信息)
     * @throws Exception
     */
    @Test
    public void testCookieWithExistedUser() throws Exception {
        //模拟登陆并返回结果
        MvcResult result= performLoginWithMockUser();

        //测试Cookie和返回信息
        assertCookieAndResponseBody(result);
    }

    @Before
    public void before() throws JoseException {
        mockMember= Member.builder()
                .studentNumber(Long.valueOf(mockUsername))
                .authorityList(new ArrayList<>())
                .build();

        //Mock JwtManager
        when(mockJwtManager.encode(any(UserDetails.class))).thenReturn(mockUsername);

        //Mock UserDetails
        doReturn(mockUsername).when(mockUserDetails).getUsername();
        doReturn(mockPassword).when(mockUserDetails).getPassword();
        doReturn(mockAuthorities).when(mockUserDetails).getAuthorities();
        doReturn(true).when(mockUserDetails).isEnabled();
        doReturn(true).when(mockUserDetails).isAccountNonExpired();
        doReturn(true).when(mockUserDetails).isAccountNonLocked();
        doReturn(true).when(mockUserDetails).isCredentialsNonExpired();

        //Mock UserDetailService
        when(mockUserDetailsService.loadUserByUsername(mockUsername)).thenReturn(mockUserDetails);

        //Mock UserDetailsAdapter
        when(mockUserDetailsAdapter.from(mockUserDetails)).thenReturn(mockMember);
        when(mockUserDetailsAdapter.to(mockMember)).thenReturn(mockUserDetails);

        //Mock Context
        doReturn(mockMember).when(memberContextHelper).getCurrentPrincipal();

        //配置MockMvc
        mockMvc= MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        //assert MockBean的成功注入
        assertEquals(mockJwtManager,context.getBean(JwtManager.class));
        assertEquals(mockUserDetailsService,context.getBean(UserDetailsService.class));
    }


    private void assertCookieAndResponseBody(MvcResult mvcResult) throws UnsupportedEncodingException {
        MockHttpServletResponse response=mvcResult.getResponse();

        //成功后返回个人信息
        assertEquals(200,response.getStatus());
        //成功后应当携带Cookie
        assertEquals(mockUsername,response.getCookie("token").getValue());
        //成功后应携带当前用户信息
//        JsonParser jsonParser=new JacksonJsonParser();
//        Long responseStudentNumber=(Long)jsonParser.parseMap(response.getContentAsString()).get("studentNumber");
//
//        assertEquals(
//                201730683314L,
//                responseStudentNumber.longValue()
//        );
    }

    private MvcResult performLoginWithMockUser() throws Exception {
        return mockMvc.perform(
                post("/login")
                        .param("username",mockUsername)
                        .param("password",mockPassword)
        ).andReturn();
    }

}
