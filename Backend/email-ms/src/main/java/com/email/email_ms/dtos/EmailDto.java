package com.email.email_ms.dtos;

import com.email.email_ms.entities.Email;

import java.time.LocalDateTime;

public record EmailDto(long id, String mailFrom, String mailTo, String mailSubject,
                       String mailText, LocalDateTime mailDthSend, EmailStatusDto mailStatus) {
    public EmailDto(Email email){
        this(email.getId(), email.getMailFrom(), email.getMailTo(), email.getMailSubject(),
                email.getMailText(), email.getDth_send(), new EmailStatusDto(email.getStatus()));
    }
}
