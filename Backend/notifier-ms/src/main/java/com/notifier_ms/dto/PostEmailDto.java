package com.notifier_ms.dto;

public record PostEmailDto(String mailFrom, String mailTo, String mailSubject, String mailText) {
}
