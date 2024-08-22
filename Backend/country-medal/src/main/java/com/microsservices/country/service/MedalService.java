
package com.microsservices.country.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.microsservices.country.repositorys.interfaces.CountryMedalInSportsRepository;
import com.microsservices.country.repositorys.interfaces.CountryRespository;
import com.microsservices.country.repositorys.interfaces.MedalRepository;
import com.microsservices.country.repositorys.interfaces.SportRepository;
import com.microsservices.country.service.criptografia.CriptografiaAES;
import com.microsservices.country.service.criptografia.Encoder_Decoder;

import jakarta.transaction.Transactional;

import com.microsservices.country.dtos.CountryMedalInSport_PostDto;
import com.microsservices.country.dtos.MedalDto;
import com.microsservices.country.enums.MedalType;
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
            if
            (
                csmRepository.existsMedalForCountryAndSport(country.getId(), sport.getId())
                ||
                csmRepository.isThereSuchAMedalForAnyCountryInThisSport(sport.getId(), medal.getId())
            )
                throw new IllegalArgumentException("the country "+ country.getName() +" already has a medal for the sport");
            CountryMedalInSports retorno = saveEntitys(country, medal, sport);
            return buildReturn(retorno);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<MedalDto> getMedal(Long id){
        try {
            if(id == 1L){
                return ResponseEntity.ok().body(new MedalDto(id.toString(), MedalType.OURO));
            }else if(id == 2L){
                return ResponseEntity.ok().body(new MedalDto(id.toString(), MedalType.PRATA));
            }else if(id == 3L){
                return ResponseEntity.ok().body(new MedalDto(id.toString(), MedalType.BRONZE));
            }else{
                throw new IllegalArgumentException("id inválido");
            }
        }catch (NumberFormatException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<List<MedalDto>> getMedals(){
        List<Medal> m = medalRepository.findAll();
        List<MedalDto> medalDtos = m.stream()
            .map(medal -> new MedalDto(medal)) 
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(medalDtos);
    }

    public ResponseEntity<MedalDto> getEncryptedMedal(String id){
        CriptografiaAES criptografiaAES = new CriptografiaAES();
        try {
            int idDecrypt = Integer
            .parseInt(criptografiaAES
            .decrypt(Encoder_Decoder
            .deconderURL(id)));
            switch (idDecrypt) {
                case 1:
                    return ResponseEntity.ok().body(new MedalDto(id, MedalType.OURO).encryptId());
                case 2:
                    return ResponseEntity.ok().body(new MedalDto(id, MedalType.PRATA).encryptId());
                case 3:
                    return ResponseEntity.ok().body(new MedalDto(id, MedalType.BRONZE).encryptId());
                default:
                    throw new IllegalArgumentException("id inválido");
            }
        }catch (NumberFormatException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
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
        if(!sport.isPresent())
            throw new IllegalArgumentException("sport not find: " + s.getName());
        return sport.get();
    }

    private Medal findMedal(Medal m){
        Optional<Medal> medal = medalRepository.findById(m.getId());
        if(!medal.isPresent())
            throw new IllegalArgumentException("Tipo de medalha desconhecido: " + m.getType());
        return medal.get();
    }
    
}