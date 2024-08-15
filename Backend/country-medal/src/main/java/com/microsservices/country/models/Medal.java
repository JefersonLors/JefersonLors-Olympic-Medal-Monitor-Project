package com.microsservices.country.models;

import com.microsservices.country.dtos.Medal_PostDto;
import com.microsservices.country.enums.MedalType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name="medals")
public class Medal{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MedalType type;
    
    public Medal() {
    }
    public Medal(Medal_PostDto m) {
        this.id = Long.parseLong(m.id());
    }
    public Medal(Long id, MedalType type) {
        this.id = id;
        this.type = type;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public MedalType getType() {
        return type;
    }
    public void setType(MedalType type) {
        this.type = type;
    }
    
    
    
}