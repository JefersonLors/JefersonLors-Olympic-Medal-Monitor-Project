package com.microsservices.country.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.microsservices.country.dtos.CountryDto;
import com.microsservices.country.dtos.CountryMedalDto;
import com.microsservices.country.dtos.CountryMedalInSportsDto;
import com.microsservices.country.dtos.MedalDto;
import com.microsservices.country.dtos.MedalSportsDto;
import com.microsservices.country.dtos.SportDto;
import com.microsservices.country.models.Country;
import com.microsservices.country.models.CountryMedalInSports;
import com.microsservices.country.models.Medal;
import com.microsservices.country.repositorys.CountryMedalInSportsRepository;

@Service
public class CountryService{
    @Autowired
    private CountryMedalInSportsRepository repository;

    public ResponseEntity<CountryMedalInSportsDto> getCountry(String name){
        try{
            List<CountryMedalInSports> results = repository.findByCountryName(name);
            Map<MedalDto, List<SportDto>> medalsSportMap = new HashMap<>();
            CountryMedalInSportsDto countryMedalInSportDto = new CountryMedalInSportsDto();
            for(CountryMedalInSports result : results){
                MedalDto medal = new MedalDto(result.getMedal());
                SportDto sport = new SportDto(result.getSport()) ;
                 
                if(medal != null){
                    medalsSportMap
                        .computeIfAbsent(medal, k -> new ArrayList<>())
                        .add(sport);
                }
            }
            List<MedalSportsDto> medalDtos = new ArrayList<>();
            for(Map.Entry<MedalDto, List<SportDto>> entry : medalsSportMap.entrySet()){
                MedalSportsDto dto = new MedalSportsDto();
                dto.setMedal(entry.getKey());
                dto.setSports(entry.getValue());
                medalDtos.add(dto);
            }
            CountryDto countryDto = new CountryDto(results.get(0).getCountry()); 
            countryMedalInSportDto.setCountry(countryDto);
            countryMedalInSportDto.setMedals(medalDtos);


            return ResponseEntity.ok().body(countryMedalInSportDto);

        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<List<CountryMedalDto>> getCountrys(){
        try{
            var results = repository.findCountriesAndMedals();
            Map<CountryDto, List<Medal>> countryMedalsMap = new HashMap<>();//Map<Country, List<Medal>> countryMedalsMap = new HashMap<>();

            for(CountryMedalInSports result : results){
                Country country = result.getCountry();
                CountryDto countryDto = new CountryDto(country);
                Medal medal = result.getMedal();
    
                if (countryDto != null) {
                    countryMedalsMap
                        .computeIfAbsent(countryDto, k -> new ArrayList<>())
                        .add(medal);
                }
            }
            
            List<CountryMedalDto> dtos = new ArrayList<>();
            for (Map.Entry<CountryDto, List<Medal>> entry : countryMedalsMap.entrySet()) {
                CountryMedalDto dto = new CountryMedalDto();
                dto.setCountry(entry.getKey());
                dto.setMedals(entry.getValue());
                dtos.add(dto);
            }
        return ResponseEntity.ok().body(dtos);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}