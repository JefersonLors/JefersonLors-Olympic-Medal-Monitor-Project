package com.microsservices.country.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            throw new RuntimeException("Esporte não encontrado");
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResponseEntity<List<SportDto>> getSports(){
        try{
            List<Sport> s = repository.findAll();
            List<SportDto> sportDtos = s.stream()
            .map(medal -> new SportDto(medal)) 
            .collect(Collectors.toList());
            return ResponseEntity.ok().body(sportDtos);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
