package com.scut.se.sehubbackend.email;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
public class SendEmailCreatedEvent extends ApplicationEvent {

    private String authority;

    public SendEmailCreatedEvent(Object source, String authority) {
        super(source);
        this.authority = authority;
    }
}
