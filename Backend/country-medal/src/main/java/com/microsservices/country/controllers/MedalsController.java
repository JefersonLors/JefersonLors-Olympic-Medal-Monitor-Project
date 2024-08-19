package com.microsservices.country.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microsservices.country.dtos.CountryMedalInSport_PostDto;
import com.microsservices.country.dtos.MedalDto;
import com.microsservices.country.service.MedalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/medal")
public class MedalsController {
    @Autowired
    MedalService service;

    @PostMapping()
    public ResponseEntity<Void> postMedals(@RequestBody CountryMedalInSport_PostDto entity) {
        return service.postMedals(entity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedalDto> getMedal(@PathVariable Long id) {
        return service.getMedal(id);
    }
    
    @GetMapping("/encrypted/{id}")
    public ResponseEntity<MedalDto> getEncryptedMedal(@PathVariable String id) {
        return service.getEncryptedMedal(id);
    }
    
}
