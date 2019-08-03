package com.scut.se.sehubbackend.unit;


import com.scut.se.sehubbackend.Domain.user.UserAuthentication;
import com.scut.se.sehubbackend.SeHubBackendApplication;
import com.scut.se.sehubbackend.Security.JWT.JwtManager;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeHubBackendApplication.class)
public class JwtManagerTest {

    @Autowired
    JwtManager jwtManager;

    @Mock
    UserAuthentication user;

    @Before
    public void before(){
        when(user.getStudentNO()).thenReturn("201730683314");
    }

    @Test
    public void testEncodeAndDecode() throws JoseException, MalformedClaimException, InvalidJwtException {
        String jwt=jwtManager.encode(user);//对user编码
//        UserAuthentication edUser=jwtManager.decode(jwt);//再解码
//        Assert.assertEquals(user.getStudentNO(),edUser.getStudentNO());//两者的studentNO应该一样
    }
}
