package com.microsservices.country.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.microsservices.country.dtos.SportDto;
import com.microsservices.country.models.Sport;
import com.microsservices.country.repositorys.interfaces.SportRepository;

@Service
public class SportService {
    @Autowired
    SportRepository repository;

    public ResponseEntity<SportDto> getSportById(Long id){
        try{
            Optional<Sport> s = repository.findById(id);
            if(s.isPresent())
                return ResponseEntity.ok().body(new SportDto(s.get()));
            return ResponseEntity.badRequest().build();
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
