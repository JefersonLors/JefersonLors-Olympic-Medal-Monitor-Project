package com.microsservices.country.models;

import com.microsservices.country.criptografia.CriptografiaAES;
import com.microsservices.country.dtos.CountryDto;
import com.microsservices.country.dtos.Country_PostDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity(name="countrys")
// @Getter
// @Setter
public class Country{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private byte[] flag;
    @Transient
    private CriptografiaAES criptografiaAES = new CriptografiaAES();
    
    public Country() {
    }
    public Country(CountryDto c) {
        try{
            this.id = Long.parseLong(c.getId());
            this.name = c.getName();
            this.flag = c.getFlag();
        }catch(Exception e){
            throw new IllegalArgumentException("build error Country");
        }
    }

    public Country(Country_PostDto c) {
        try{
            this.id = Long.parseLong(criptografiaAES.decrypt(c.id()));
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

    public byte[] getFlag() {
        return flag;
    }
    public void setFlag(byte[] flag) {
        this.flag = flag;
    }
}