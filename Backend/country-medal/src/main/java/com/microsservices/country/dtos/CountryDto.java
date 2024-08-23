package com.microsservices.country.dtos;

import java.util.Objects;

import com.microsservices.country.models.Country;
import com.microsservices.country.service.criptografia.CriptografiaAES;

public class CountryDto {
    private String id;
    private String name;
    private String flag;
    private final CriptografiaAES criptografiaAES = new CriptografiaAES();

    public CountryDto(String id, String name, String flag) {
        this.id = id;
        this.name = name;
        this.flag = flag;
    }

    public CountryDto(Country c){
        try{
            this.id = c.getId().toString(); 
            this.name = c.getName(); 
            this.flag = c.getFlag();
        }catch(Exception e){
            throw new IllegalArgumentException("build error Country DTO");
        }
    }
    
    public CountryDto encryptId(){
        try {
            this.id = criptografiaAES.encrypt(this.id);
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryDto that = (CountryDto) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(name, that.name) &&
               Objects.equals(flag, that.flag);  // Comparação de String
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, flag);  // Inclui String no cálculo do hash
    }
    // public CountryDto(Country c, boolean encrypted){
    //     try{
    //         this.id = criptografiaAES.encrypt(c.getId().toString()); 
    //         this.name = c.getName(); 
    //         this.flag = c.getFlag();
    //     }catch(Exception e){
    //         throw new IllegalArgumentException("build error Country DTO");
    //     }
    // }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
