package com.notifier_ms.dto;


import java.time.LocalDateTime;

public record PostEmailDto(String mailFrom, String mailTo, String mailSubject, String mailText) {
}
