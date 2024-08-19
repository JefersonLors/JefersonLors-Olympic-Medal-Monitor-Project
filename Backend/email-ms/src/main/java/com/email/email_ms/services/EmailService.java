package com.email.email_ms.services;

import com.email.email_ms.dtos.GetEmailDto;
import com.email.email_ms.dtos.PostEmailDto;
import com.email.email_ms.entities.Email;
import com.email.email_ms.entities.EmailStatus;
import com.email.email_ms.entities.EmailStatusEnum;
import com.email.email_ms.repositories.EmailRepository;
import com.email.email_ms.repositories.EmailStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {
    @Autowired
    EmailRepository emailRepository;

    @Autowired
    EmailStatusRepository emailStatusRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    public GetEmailDto sendEmail(PostEmailDto emailDto){
        SimpleMailMessage message =  new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(emailDto.mailTo());
        message.setText(emailDto.mailText());
        message.setSubject(emailDto.mailSubject());

        javaMailSender.send(message);

        Email email = new Email(emailDto);
        email.setDth_send(LocalDateTime.now());
        email.setMailFrom(mailFrom);
        email.setStatus(new EmailStatus(EmailStatusEnum.SENT.getCodigo(),
                        EmailStatusEnum.SENT.getName()));

        return new GetEmailDto(emailRepository.save(email));
    }
}
