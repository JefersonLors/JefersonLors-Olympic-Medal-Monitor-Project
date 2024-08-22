package com.microsservices.country.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microsservices.country.dtos.CountryMedalInSport_PostDto;
import com.microsservices.country.dtos.MedalDto;
import com.microsservices.country.service.MedalService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/medal")
public class MedalsController {
    @Autowired
    MedalService service;

    @PostMapping()
    @Operation(summary = "Cadastra um medalha para um país em uma modalidade", 
    description = "Recebe os ids de medalha, país e esport e faz um insert no banco de dados")
    public ResponseEntity<Void> postMedals(@RequestBody CountryMedalInSport_PostDto entity) {
        return service.postMedals(entity);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca medalha por id")
    public ResponseEntity<MedalDto> getMedal(@PathVariable Long id) {
        return service.getMedal(id);
    }
    
    @GetMapping("/encrypted/{id}")
    @Operation(summary = "Busca medalha por id criptografado")
    public ResponseEntity<MedalDto> getEncryptedMedal(@PathVariable String id) {
        return service.getEncryptedMedal(id);
    }

    @GetMapping()
    @Operation(summary = "Busca todas as medalhas")
    public ResponseEntity<List<MedalDto>> getMedals() {
        return service.getMedals();
    }
    
    
}
