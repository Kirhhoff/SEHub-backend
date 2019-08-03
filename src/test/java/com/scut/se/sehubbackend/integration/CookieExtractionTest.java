package com.scut.se.sehubbackend.integration;

import com.scut.se.sehubbackend.Security.Jwt.JwtManager;
import org.jose4j.jwt.MalformedClaimException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;
import java.util.ArrayList;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CookieExtractionTest {

    @Autowired WebApplicationContext webApplicationContext;
    @MockBean JwtManager mockJwtManager;
    @MockBean UserDetailsService mockUserDetailsService;
    MockMvc mockMvc;
    @Mock UserDetails mockUserDetails;


    String mockToken="Luminosity";
    Cookie mockCookie=new Cookie("token",mockToken);
    String existedApi="/api/test/empty";

    @Test
    public void testCookieExtraction() throws Exception {
        //没有cookie时访问api返回403
        mockMvc.perform(get(existedApi)).andExpect(status().isForbidden());
        //带着cookie时访问api返回200
        mockMvc.perform(get(existedApi).cookie(mockCookie)).andExpect(status().isOk());
    }

    @Before
    public void before() throws MalformedClaimException {
        mockMvc= MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        //Mock UserDetails
        doReturn(mockToken).when(mockUserDetails).getUsername();
        doReturn("").when(mockUserDetails).getPassword();
        doReturn(new ArrayList<>()).when(mockUserDetails).getAuthorities();
        doReturn(true).when(mockUserDetails).isEnabled();
        doReturn(true).when(mockUserDetails).isAccountNonExpired();
        doReturn(true).when(mockUserDetails).isAccountNonLocked();
        doReturn(true).when(mockUserDetails).isCredentialsNonExpired();

        //Mock JwtManager的解码部分
        when(mockJwtManager.decode(mockToken)).thenReturn(mockToken);

        //Mock UserDetailsService
        when(mockUserDetailsService.loadUserByUsername(mockToken)).thenReturn(mockUserDetails);
    }

}
