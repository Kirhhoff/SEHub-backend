package com.scut.se.sehubbackend.email;

import com.scut.se.sehubbackend.service.AuthorityService;
import com.scut.se.sehubbackend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

@Component
public class SendEmailEventListener {

    @Autowired
    AuthorityService authorityService;

    @Autowired
    EmailService emailService;


    @Async
    @EventListener
    public void handleSendEmailCreatedEvent(@NotNull SendEmailCreatedEvent event) {
        List<String> emails = authorityService.findAllEmailsWhoseOwnerHasAuthority(event.getAuthority());
        String content = "您有一张新的" + event.getAuthority() + "请注意及时登录团学系统审核哦:)";
        for (String email : emails){
            if (email != null) {
                try {
                    emailService.sendEmail(email, content);
                } catch (SendMailException e) {

                }
            }
        }
    }

    @Async
    @EventListener
    public void handleSendEmailCheckedEvent(SendEmailCheckedEvent event) {
        String content = "您发起的活动申请已被审核，请注意及时登录团学系统查看审核状态哦:)";
        String email = event.getInitializer().getEmail();
        if (email != null){
            try {
                emailService.sendEmail(email, content);
            } catch (SendMailException e) {

            }
        }
    }
}
