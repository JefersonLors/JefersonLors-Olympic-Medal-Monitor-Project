
package com.microsservices.country.service;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.microsservices.country.repositorys.MedalRepository;
import com.microsservices.country.repositorys.SportRepository;

import jakarta.transaction.Transactional;

import com.microsservices.country.repositorys.CountryMedalInSportsRepository;
import com.microsservices.country.repositorys.CountryRespository;

import com.microsservices.country.dtos.CountryMedalInSport_PostDto;
import com.microsservices.country.models.Country;
import com.microsservices.country.models.CountryMedalInSports;
import com.microsservices.country.models.Medal;
import com.microsservices.country.models.Sport;

@Service
public class MedalService {

    @Autowired
    MedalRepository medalRepository;
    @Autowired
    SportRepository sportRepository;
    @Autowired
    CountryRespository countryRespository;
    @Autowired
    CountryMedalInSportsRepository csmRepository;

    @Transactional
    public ResponseEntity<Void> postMedals(CountryMedalInSport_PostDto entity){
        try{
            Country country = new Country(entity.country());
            Medal medal = new Medal(entity.medal());
            Sport sport = new Sport(entity.sport());
            if(csmRepository.existsMedalForCountryAndSport(country.getId(), sport.getId()))
                throw new IllegalArgumentException("the country "+ country.getName() +" already has a medal for the sport");
            CountryMedalInSports retorno = saveEntitys(country, medal, sport);
            return buildReturn(retorno);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    private CountryMedalInSports saveEntitys(Country country, Medal medal, Sport sport) throws Exception{
        Country c = findCountry(country); 
        Medal m = findMedal(medal);
        Sport s = findSport(sport);
        return csmRepository.save(new CountryMedalInSports(s, m, c)); //VERIFICAR SE JÁ EXISTE ANTES DE SALVAR
    }

    private ResponseEntity<Void> buildReturn(CountryMedalInSports csm){
            URI location = UriComponentsBuilder
                            .fromPath("/api/Country/{name}")
                            .buildAndExpand(csm.getCountry().getName())
                            .toUri();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(location);
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    private Country findCountry(Country c) throws Exception{
        Optional<Country> country =  countryRespository.findById(c.getId());//countryRespository.findByName(c.getName());
        if(!country.isPresent())
            throw new IllegalArgumentException("country not find: " + c.getName());
        return country.get();
    }

    private Sport findSport(Sport s) throws Exception{
        Optional<Sport> sport = sportRepository.findById(s.getId());//findByName(s.getName());
        if(sport.isPresent())
            throw new IllegalArgumentException("sport not find: " + s.getName());
        return sport.get();
    }

    private Medal findMedal(Medal m){
        Optional<Medal> medal = medalRepository.findById(m.getId());
        if(medal.isPresent())
            throw new IllegalArgumentException("Tipo de medalha desconhecido: " + m.getType());
        return medal.get();
    }
    
}