package com.scut.se.sehubbackend.unit;


import com.scut.se.sehubbackend.security.jwt.JwtManager;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.lang.JoseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class JwtManagerTest {

    @Autowired JwtManager jwtManager;

    @Test
    public void testEncodeAndDecode() throws JoseException, MalformedClaimException {
        //编码，同时设置一个修改版的
        String jwt=jwtManager.encode(mockUserDetails);
        String jwtM=modify(jwt);

        //对两者同时解码
        String username=jwtManager.decode(jwt);
        String usernameM=jwtManager.decode(jwtM);

        //原始的应当相同，修改的应当不同
        assertEquals(mockUserDetails.getUsername(),username);
        assertNotEquals(mockUserDetails.getUsername(),usernameM);
    }

    private String modify(String originJwt){
        return originJwt.toUpperCase();
    }

    UserDetails mockUserDetails=new UserDetails() {
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return new ArrayList<>();
        }

        @Override
        public String getPassword() {
            return "";
        }

        @Override
        public String getUsername() {
            return "Luminosity";
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    };
}
