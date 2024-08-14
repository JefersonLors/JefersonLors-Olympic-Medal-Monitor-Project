package com.microsservices.country.dtos;

import com.microsservices.country.criptografia.CriptografiaAES;
import com.microsservices.country.models.Country;

public class CountryDto {
    private String id;
    private String name;
    private byte[] flag;
    private final CriptografiaAES criptografiaAES = new CriptografiaAES();

    public CountryDto(String id, String name, byte[] flag) {
        this.id = id;
        this.name = name;
        this.flag = flag;
    }

    public CountryDto(Country c){
        try{
            this.id = criptografiaAES.encrypt(c.getId().toString()); 
            this.name = c.getName(); 
            this.flag = c.getFlag();
        }catch(Exception e){
            throw new IllegalArgumentException("build error Country DTO");
        }
    }
    
    
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

    public byte[] getFlag() {
        return flag;
    }

    public void setFlag(byte[] flag) {
        this.flag = flag;
    }
}
