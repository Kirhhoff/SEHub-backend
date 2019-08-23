package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.email.SendMailException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class EmailServiceTest {

    @Autowired
    EmailService emailService;

    @Test
    public void testSendEmail() throws SendMailException {
        emailService.sendEmail("scut_sub@outlook.com", "新增EmailService");
        Assert.assertTrue(true);
    }
}
