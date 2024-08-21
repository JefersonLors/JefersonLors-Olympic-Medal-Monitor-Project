package com.microsservices.country.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.microsservices.country.dtos.SportDto;
import com.microsservices.country.service.SportService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController("/sport")
public class SportController {
    @Autowired
    SportService service;

    @GetMapping("/{id}")
    @Operation(summary = "Busca esporte por id")
    public ResponseEntity<SportDto> getSportById(@PathVariable Long id) {
        return service.getSportById(id);
    }
    
}
