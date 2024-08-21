package com.token_validator_ms.controller;

import com.token_validator_ms.dto.TokenDto;
import com.token_validator_ms.service.JWTokenService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tokenValidator")
public class TokenValidatorController {
    @Autowired
    private JWTokenService jwTokenService;

    @PostMapping("/roles")
    @Operation(summary = "Retorna as roles contidas num token.", description = "Recebe um Token JWT e retorna as roles contida nas clains.")
    public ResponseEntity<List<String>> extractRolesFromToken(@RequestBody TokenDto tokenDto){
        List<String> userRoles;
        try {
            userRoles = this.jwTokenService.extractRolesFromToken(tokenDto);
        } catch (SignatureException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(new ArrayList<>(List.of(e.getMessage())));
        }

        return new ResponseEntity<>(userRoles, HttpStatus.OK);
    }

}
