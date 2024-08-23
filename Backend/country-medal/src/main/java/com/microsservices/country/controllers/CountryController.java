package com.microsservices.country.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microsservices.country.dtos.CountryDto;
import com.microsservices.country.dtos.CountryMedalDto;
import com.microsservices.country.dtos.CountryMedalInSportsDto;
import com.microsservices.country.enums.Role;
import com.microsservices.country.service.CountryService;
import com.microsservices.country.service.RoleValidationService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;



@RestController
@RequestMapping("/country")
public class CountryController {
    @Autowired
    CountryService service;

    @Autowired
    private RoleValidationService roleValidationService;

    @GetMapping()
    @Operation(summary="Retorna todos os países.", 
    description="Retorna os países e a quantidade de cada tipo de medalha.")
    public ResponseEntity<List<CountryMedalDto>> getCountrys(@RequestHeader("Authorization") String requestHeader) {
        if(this.roleValidationService.currentUserHasRole(requestHeader, new ArrayList<>(List.of(Role.ROLE_USER))))
            return service.getCountrys();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/ByName/{name}")
    @Operation(summary = "Busca o país por nome.", 
    description = "Retorna o país e as medalhas ganhas em cada esporte.")
    public ResponseEntity<CountryMedalInSportsDto> getCountry(@RequestHeader("Authorization") String requestHeader, @PathVariable String name) {
        if(this.roleValidationService.currentUserHasRole(requestHeader, new ArrayList<>(List.of(Role.ROLE_USER))))
            return service.getCountry(name);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/ByEncryptedId/{id}")
    @Operation(summary = "Busca o país atravez do id criptografado.", 
    description = "Recebe o id do país criptografado com o aulgoritimo de criptografia AES e retorna o país e as medalhas ganhas em cada esporte.")
    public ResponseEntity<CountryDto> getCountryByEncryptedId(@RequestHeader("Authorization") String requestHeader, @PathVariable String id) {
        if(this.roleValidationService.currentUserHasRole(requestHeader, new ArrayList<>(List.of(Role.ROLE_USER))))
            return service.getCountryByEncryptedId(id);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    
    @GetMapping("/ById/{id}")
    @Operation(summary = "Busca o país por id" , description = "Retorna a entidade país")
    public ResponseEntity<CountryDto> getCountryById(@RequestHeader("Authorization") String requestHeader, @PathVariable Long id) {
        if(this.roleValidationService.currentUserHasRole(requestHeader, new ArrayList<>(List.of(Role.ROLE_USER))))
            return service.getCountryById(id);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        
    }
    
    @GetMapping("/WithMedalsById/{id}")
    @Operation(summary = "Busca o país por id" , description = "Retorna a entidade país com todas as suas medalhas")
    public ResponseEntity<CountryMedalInSportsDto> getCountryWithMedalsById(@RequestHeader("Authorization") String requestHeader, @PathVariable Long id) {
        if(this.roleValidationService.currentUserHasRole(requestHeader, new ArrayList<>(List.of(Role.ROLE_USER))))
            return service.getCountryWithMedalsById(id);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        
    }
}


