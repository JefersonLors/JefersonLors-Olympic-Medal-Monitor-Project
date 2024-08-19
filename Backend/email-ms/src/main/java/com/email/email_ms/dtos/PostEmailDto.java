package com.email.email_ms.dtos;

import com.email.email_ms.entities.Email;

import java.time.LocalDateTime;

public record PostEmailDto(String mailTo, String mailSubject, String mailText) {

}
