package com.microsservices.country.models;

import com.microsservices.country.criptografia.CriptografiaAES;
import com.microsservices.country.dtos.SportDto;
import com.microsservices.country.dtos.Sport_PostDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity(name="sports")
public class Sport{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Transient
    private CriptografiaAES criptografiaAES = new CriptografiaAES();
    public Sport() {
    }
    public Sport(SportDto s) {
        this.id = Long.parseLong(s.getId());
        this.name = s.getName();
        this.description = s.getDescription();
    }

    public Sport(Sport_PostDto s) {
        try{
            this.id = Long.parseLong(criptografiaAES.decrypt(s.id()));
        }catch(Exception e){
            throw new IllegalArgumentException("build error Country");
        }
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    

}