package com.email.email_ms.controllers;

import com.email.email_ms.dtos.GetEmailDto;
import com.email.email_ms.dtos.PostEmailDto;
import com.email.email_ms.services.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("email")
public class EmailController {
    @Autowired
    EmailService emailService;

    @PostMapping("/send")
    @Operation(summary="Realiza envio de e-mail", description="Realiza envio de e-mail com base nas informações recebidas.")
    public ResponseEntity<GetEmailDto> send(@RequestBody PostEmailDto emailDto){
        GetEmailDto getEmailDto = emailService.sendEmail(emailDto);
        return ResponseEntity.ok(getEmailDto);
    }
}
