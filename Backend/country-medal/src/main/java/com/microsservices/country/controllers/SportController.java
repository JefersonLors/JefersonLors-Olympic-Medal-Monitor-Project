package com.microsservices.country.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.microsservices.country.dtos.SportDto;
import com.microsservices.country.enums.Role;
import com.microsservices.country.service.RoleValidationService;
import com.microsservices.country.service.SportService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.ArrayList;
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
    private RoleValidationService roleValidationService;

    @GetMapping("/{id}")
    @Operation(summary = "Busca esporte por id")
    public ResponseEntity<SportDto> getSportById(@RequestHeader("Authorization") String requestHeader, @PathVariable Long id) {
        if(this.roleValidationService.currentUserHasRole(requestHeader, new ArrayList<>(List.of(Role.ROLE_USER))))
            return service.getSportById(id);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    
    @GetMapping()
    @Operation(summary = "Busca todos os sports")
    public ResponseEntity<List<SportDto>> getSports(@RequestHeader("Authorization") String requestHeader) {
        if(this.roleValidationService.currentUserHasRole(requestHeader, new ArrayList<>(List.of(Role.ROLE_USER, Role.ROLE_ADMIN))))
            return service.getSports();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    
}
