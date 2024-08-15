package com.notifier_ms.dto;


import java.time.LocalDateTime;

public record EmailDto(long id, String mailFrom, String mailTo, String mailSubject,
                       String mailText, LocalDateTime mailDthSend, EmailStatusDto mailStatus) {
}
