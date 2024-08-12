package com.email.email_ms.consumers;

import com.email.email_ms.dtos.EmailDto;
import com.email.email_ms.entities.Email;
import com.email.email_ms.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {
    @Autowired
    EmailService emailService;

    @RabbitListener(queues="${spring.rabbitmq.queue}")
    public void listen(@Payload EmailDto emailDto){
        emailService.sendEmail(emailDto);
    }
}
