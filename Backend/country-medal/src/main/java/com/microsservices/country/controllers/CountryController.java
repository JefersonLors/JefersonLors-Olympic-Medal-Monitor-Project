package com.microsservices.country.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microsservices.country.dtos.CountryMedalDto;
import com.microsservices.country.dtos.CountryMedalInSportsDto;
import com.microsservices.country.service.CountryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/Country")
public class CountryController {
    @Autowired
    CountryService service;

    @GetMapping()
    public ResponseEntity<List<CountryMedalDto>> getCountrys() {
        return service.getCountrys();
    }

    @GetMapping("/{name}")
    public ResponseEntity<CountryMedalInSportsDto> getCountry(@PathVariable String name) {
        return service.getCountry(name);
    }
    
    
}
