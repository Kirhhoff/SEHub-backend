package com.scut.se.sehubbackend.unit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@WebAppConfiguration
public class InitializerOrModifierValueGeneratorTest {


    @WithMockUser(username="luminosity")
    @Test
    public void test(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals("luminosity",authentication.getName());
    }

    @Before
    public void before(){

    }

}
