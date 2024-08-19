package com.notifier_ms.controller.clients;

import com.notifier_ms.dto.TokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("token-validator-ms")
public interface TokenValidator {
    @RequestMapping(method= RequestMethod.POST, value="/tokenValidator/roles")
    ResponseEntity<List<String>> extractRolesFromToken(@RequestBody TokenDto tokenDto);
}
