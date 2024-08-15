package com.email.email_ms.controllers;

import com.email.email_ms.dtos.GetEmailDto;
import com.email.email_ms.dtos.PostEmailDto;
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
    public ResponseEntity<GetEmailDto> send(@RequestBody PostEmailDto emailDto){
        GetEmailDto getEmailDto = emailService.sendEmail(emailDto);

        if( emailDto == null )
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(getEmailDto);
    }
}
