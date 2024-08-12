package com.email.email_ms.controllers;

import com.email.email_ms.dtos.EmailDto;
import com.email.email_ms.services.EmailService;
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
    public ResponseEntity<EmailDto> send(@RequestBody EmailDto emailDto){
        emailDto = emailService.sendEmail(emailDto);

        if( emailDto == null )
            return new ResponseEntity<EmailDto>(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(emailDto);
    }
}
