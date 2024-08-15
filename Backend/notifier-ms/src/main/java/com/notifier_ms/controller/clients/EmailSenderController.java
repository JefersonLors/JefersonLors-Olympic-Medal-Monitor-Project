package com.notifier_ms.controller.clients;

import com.notifier_ms.dto.EmailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("email-ms")
public interface EmailSenderController {
    @RequestMapping(method= RequestMethod.POST, value="/email/send")
    ResponseEntity<EmailDto> send(@RequestBody EmailDto emailDto);
}
