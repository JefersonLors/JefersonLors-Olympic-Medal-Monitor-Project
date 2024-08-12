package com.email.email_ms.entities;

import com.email.email_ms.dtos.EmailDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity(name="emails")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String mailFrom;

    String mailTo;

    String mailSubject;

    String mailText;

    LocalDateTime dth_send;

    @ManyToOne
    EmailStatus status;

    public Email(EmailDto emailDto){
        this.id = emailDto.id();
        this.mailFrom = emailDto.mailFrom();
        this.mailTo = emailDto.mailTo();
        this.mailSubject = emailDto.mailSubject();
        this.mailText = emailDto.mailText();
        this.dth_send = emailDto.mailDthSend();
        this.status = new EmailStatus(emailDto.mailStatus());
    }
}
