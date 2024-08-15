package com.email.email_ms.dtos;

import com.email.email_ms.entities.Email;

import java.time.LocalDateTime;

public record GetEmailDto(long id, String mailFrom, String mailTo, String mailSubject,
                          String mailText, LocalDateTime mailDthSend, String mailStatus) {
    public GetEmailDto(Email email){
        this(email.getId(), email.getMailFrom(), email.getMailTo(), email.getMailSubject(),
                email.getMailText(), email.getDth_send(), email.getStatus().getStatus());
    }
}
