package com.microsservices.country.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microsservices.country.dtos.CountryDto;
import com.microsservices.country.dtos.CountryMedalDto;
import com.microsservices.country.dtos.CountryMedalInSportsDto;
import com.microsservices.country.service.CountryService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/country")
public class CountryController {
    @Autowired
    CountryService service;

    @GetMapping()
    @Operation(summary="Retorna todos os países.", 
    description="Retorna os países e a quantidade de cada tipo de medalha.")
    public ResponseEntity<List<CountryMedalDto>> getCountrys() {
        return service.getCountrys();
    }

    @GetMapping("/ByName/{name}")
    @Operation(summary = "Busca o país por nome.", 
    description = "Retorna o país e as medalhas ganhas em cada esporte.")
    public ResponseEntity<CountryMedalInSportsDto> getCountry(@PathVariable String name) {
        return service.getCountry(name);
    }

    @GetMapping("/ByEncryptedId/{id}")
    @Operation(summary = "Busca o país atravez do id criptografado.", 
    description = "Recebe o id do país criptografado com o aulgoritimo de criptografia AES e retorna o país e as medalhas ganhas em cada esporte.")
    public ResponseEntity<CountryDto> getCountryByEncryptedId(@PathVariable String id) {
        return service.getCountryByEncryptedId(id);
    }
    
    // @GetMapping("/ById/{id}")
    // @Operation(summary = "Busca o país por id" , description = "Retorna a entidade país")
    // public ResponseEntity<CountryDto> getCountryById(@PathVariable Long id) {
    //     return service.getCountryById(id);
    // }
    
    @GetMapping("/ById/{id}")
    @Operation(summary = "Busca o país por id" , description = "Retorna a entidade país")
    public ResponseEntity<CountryMedalInSportsDto> getCountryById(@PathVariable Long id) {
        return service.getCountryById(id);
    }
}
