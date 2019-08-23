package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.email.OhMyEmail;
import com.scut.se.sehubbackend.email.SendMailException;
import org.springframework.stereotype.Service;

import static com.scut.se.sehubbackend.email.OhMyEmail.SMTP_QQ;

@Service
public class EmailService {

    private static final String FROM_EMAIL_ADDRESS = "1341471497@qq.com";
    private static final String PASSWORD = "bhpisloxtnmliehh";

    public EmailService() {
        OhMyEmail.config(SMTP_QQ(false), "1341471497@qq.com", "bhpisloxtnmliehh");
    }

    public void sendEmail(String toEmailAddress, String mailContent) throws SendMailException {
        OhMyEmail.subject("团委学生会通知") // 邮件标题
                .from("华南理工大学软件学院") // 发件人昵称
                .to(toEmailAddress) // 收件邮箱地址
                .text(mailContent) // 邮件正文内容
                .send();
    }
}
