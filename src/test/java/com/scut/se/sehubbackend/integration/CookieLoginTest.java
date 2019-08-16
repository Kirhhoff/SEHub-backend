package com.scut.se.sehubbackend.integration;

import com.scut.se.sehubbackend.security.jwt.JwtManager;
import org.jose4j.lang.JoseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
 *     <li>登陆后将被重定向</li>
 *     <li>响应中带有一个Cookie用于维持会话</li>
 * </ul>
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest
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

    @Mock UserDetails mockUserDetails;
    String mockUsername="201730683314";
    String mockPassword="123456789";
    List<GrantedAuthority> mockAuthorities= new ArrayList<>();



    /**
     * 用存在的用户进行登陆测试，应当返回Cookie和302状态(成功登陆后进行转发)
     * @throws Exception
     */
    @Test
    public void testCookieWithExistedUser() throws Exception {
        //模拟登陆并返回结果
        MvcResult result= performLoginWithMockUser();

        //测试Cookie和转发
        assertCookieAndRedirect(result);
    }

    @Before
    public void before() throws JoseException {
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

        //配置MockMvc
        mockMvc= MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        //assert MockBean的成功注入
        assertEquals(mockJwtManager,context.getBean(JwtManager.class));
        assertEquals(mockUserDetailsService,context.getBean(UserDetailsService.class));
    }


    private void assertCookieAndRedirect(MvcResult mvcResult){
        MockHttpServletResponse response=mvcResult.getResponse();

        //成功后进行转发，转发到相应的Url
        assertEquals(302,response.getStatus());
        assertEquals("/",response.getRedirectedUrl());
        //成功后应当携带Cookie
        assertEquals(mockUsername,response.getCookie("token").getValue());
    }

    private MvcResult performLoginWithMockUser() throws Exception {
        return mockMvc.perform(
                post("/login")
                        .param("username",mockUsername)
                        .param("password",mockPassword)
        ).andReturn();
    }

}
