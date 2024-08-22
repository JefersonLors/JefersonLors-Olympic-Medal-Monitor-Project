package com.microsservices.country.models;

import java.util.ArrayList;
import java.util.List;

import com.microsservices.country.dtos.CountryDto;
import com.microsservices.country.dtos.Country_PostDto;
import com.microsservices.country.service.criptografia.CriptografiaAES;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity(name="countrys")
// @Getter
// @Setter
public class Country{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String flag;
    @OneToMany(mappedBy = "country")
    private List<CountryMedalInSports> country_medal_in_sports = new ArrayList<>();
    @Transient
    private CriptografiaAES criptografiaAES = new CriptografiaAES();
    
    public Country() {
    }

    

    public Country(Long id, String name, String b) {
        this.id = id;
        this.name = name;
        this.flag = b;
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
            this.id = Long.parseLong(c.id());//Long.parseLong(criptografiaAES.decrypt(c.id()));
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

    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
}