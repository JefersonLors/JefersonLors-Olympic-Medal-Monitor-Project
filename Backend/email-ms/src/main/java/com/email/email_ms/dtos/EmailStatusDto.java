package com.email.email_ms.dtos;

import com.email.email_ms.entities.EmailStatus;

public record EmailStatusDto(long id, String status) {
    public EmailStatusDto(EmailStatus emailStatus){
        this(emailStatus.getId(), emailStatus.getStatus());
    }
}
