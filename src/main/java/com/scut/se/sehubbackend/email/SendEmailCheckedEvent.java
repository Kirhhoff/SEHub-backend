package com.scut.se.sehubbackend.email;

import com.scut.se.sehubbackend.domain.member.Member;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
public class SendEmailCheckedEvent extends ApplicationEvent {

    private Member initializer;

    public SendEmailCheckedEvent(Object source, Member initializer) {
        super(source);
        this.initializer = initializer;
    }
}
