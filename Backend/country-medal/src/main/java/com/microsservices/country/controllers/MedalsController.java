package com.microsservices.country.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microsservices.country.dtos.CountryMedalInSport_PostDto;
import com.microsservices.country.dtos.MedalDto;
import com.microsservices.country.enums.Role;
import com.microsservices.country.service.MedalService;
import com.microsservices.country.service.RoleValidationService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/medal")
public class MedalsController {
    @Autowired
    MedalService service;
    @Autowired
    private RoleValidationService roleValidationService;

    @PostMapping()
    @Operation(summary = "Cadastra um medalha para um país em uma modalidade", 
    description = "Recebe os ids de medalha, país e esport e faz um insert no banco de dados")
    public ResponseEntity<Void> postMedals(@RequestHeader("Authorization") String requestHeader, @RequestBody CountryMedalInSport_PostDto entity) {
        if(this.roleValidationService.currentUserHasRole(requestHeader, new ArrayList<>(List.of(Role.ROLE_ADMIN))))
            return service.postMedals(entity);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca medalha por id")
    public ResponseEntity<MedalDto> getMedal(@RequestHeader("Authorization") String requestHeader, @PathVariable Long id) {
        if(this.roleValidationService.currentUserHasRole(requestHeader, new ArrayList<>(List.of(Role.ROLE_USER, Role.ROLE_ADMIN))))
            return service.getMedal(id);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    
    @GetMapping("/encrypted/{id}")
    @Operation(summary = "Busca medalha por id criptografado")
    public ResponseEntity<MedalDto> getEncryptedMedal(@RequestHeader("Authorization") String requestHeader, @PathVariable String id) {
        if(this.roleValidationService.currentUserHasRole(requestHeader, new ArrayList<>(List.of(Role.ROLE_USER, Role.ROLE_ADMIN))))
            return service.getEncryptedMedal(id);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping()
    @Operation(summary = "Busca todas as medalhas")
    public ResponseEntity<List<MedalDto>> getMedals(@RequestHeader("Authorization") String requestHeader) {
        if(this.roleValidationService.currentUserHasRole(requestHeader, new ArrayList<>(List.of(Role.ROLE_USER, Role.ROLE_ADMIN))))
            return service.getMedals();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();   
    }
    
    
}
