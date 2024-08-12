package com.email.email_ms.services;

import com.email.email_ms.dtos.EmailDto;
import com.email.email_ms.entities.Email;
import com.email.email_ms.entities.EmailStatus;
import com.email.email_ms.entities.EmailStatusEnum;
import com.email.email_ms.repositories.EmailRepository;
import com.email.email_ms.repositories.EmailStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmailService {
    @Autowired
    EmailRepository emailRepository;

    @Autowired
    EmailStatusRepository emailStatusRepository;

    @Autowired
    JavaMailSender javaMailSender;

    public EmailDto sendEmail(EmailDto emailDto){
        SimpleMailMessage message =  new SimpleMailMessage();
        message.setFrom(emailDto.mailFrom());
        message.setTo(emailDto.mailTo());
        message.setText(emailDto.mailText());
        message.setSubject(emailDto.mailSubject());

        javaMailSender.send(message);

        Email email = new Email(emailDto);
        email.setDth_send(LocalDateTime.now());
        email.setStatus(new EmailStatus(EmailStatusEnum.SENT.getCodigo(),
                        EmailStatusEnum.SENT.getName()));

        return new EmailDto(emailRepository.save(email));
    }
}
