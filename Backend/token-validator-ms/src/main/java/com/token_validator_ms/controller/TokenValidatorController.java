package com.token_validator_ms.controller;

import com.token_validator_ms.dto.TokenDto;
import com.token_validator_ms.service.JWTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tokenValidator")
public class TokenValidatorController {
    @Autowired
    private JWTokenService jwTokenService;

    @PostMapping("/roles")
    public ResponseEntity<List<String>> extractRolesFromToken(@RequestBody TokenDto tokenDto){
        List<String> userRoles = this.jwTokenService.extractRolesFromToken(tokenDto);

        return new ResponseEntity<>(userRoles, HttpStatus.OK);
    }

}
