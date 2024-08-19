package com.notifier_ms.dto;


import java.time.LocalDateTime;

public record GetEmailDto(long id, String mailFrom, String mailTo, String mailSubject,
                          String mailText, LocalDateTime mailDthSend, String mailStatus) {
}
