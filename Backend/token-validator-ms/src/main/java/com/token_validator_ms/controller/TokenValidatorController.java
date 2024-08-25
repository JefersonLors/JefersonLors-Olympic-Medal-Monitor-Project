package com.token_validator_ms.controller;

import com.token_validator_ms.dto.TokenDto;
import com.token_validator_ms.dto.UserHasRoleDto;
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
        List<String> userRoles = this.jwTokenService.extractRolesFromToken(tokenDto.value());
        return new ResponseEntity<>(userRoles, HttpStatus.OK);
    }
    @PostMapping("/hasRole")
    @Operation(summary="Recebe um token e uma list de role, e retorna se pelo menos uma delas contida no token.", description = "Recebe um token e uma list de role, e retorna se pelo menos uma delas contida no token")
    public ResponseEntity<Boolean> userHasRole(@RequestBody UserHasRoleDto userHasRoleDto){
        Boolean result = jwTokenService.userHasAtLeastOneRole(userHasRoleDto);
        return ResponseEntity.ok(result);
    }
}
