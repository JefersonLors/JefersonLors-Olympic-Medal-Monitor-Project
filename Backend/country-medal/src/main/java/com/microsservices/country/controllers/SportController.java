package com.microsservices.country.controllers;

import com.microsservices.country.controllers.clients.TokenValidator;
import com.microsservices.country.dtos.UserHasRoleDto;
import org.springframework.web.bind.annotation.RestController;

import com.microsservices.country.dtos.SportDto;
import com.microsservices.country.enums.Role;
import com.microsservices.country.service.SportService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController()
@RequestMapping("/sport")
public class SportController {
    @Autowired
    SportService service;

    @Autowired
    private TokenValidator tokenValidator;

    @GetMapping("/{id}")
    @Operation(summary = "Busca esporte por id")
    public ResponseEntity<SportDto> getSportById(@RequestHeader("Authorization") String requestHeader, @PathVariable Long id) {
        UserHasRoleDto data = new UserHasRoleDto(requestHeader, List.of(Role.ROLE_ADMIN.name(), Role.ROLE_USER.name()));
        if(this.tokenValidator.userHasRole(data).getBody().booleanValue())
            return service.getSportById(id);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    
    @GetMapping()
    @Operation(summary = "Busca todos os sports")
    public ResponseEntity<List<SportDto>> getSports(@RequestHeader("Authorization") String requestHeader) {
        UserHasRoleDto data = new UserHasRoleDto(requestHeader, List.of(Role.ROLE_ADMIN.name(), Role.ROLE_USER.name()));
        if(this.tokenValidator.userHasRole(data).getBody().booleanValue())
            return service.getSports();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    
}
