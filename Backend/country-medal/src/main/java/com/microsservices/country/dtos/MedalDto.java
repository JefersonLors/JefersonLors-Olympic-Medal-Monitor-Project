package com.microsservices.country.dtos;

import com.microsservices.country.criptografia.CriptografiaAES;
import com.microsservices.country.enums.MedalType;
import com.microsservices.country.models.Medal;

public class MedalDto {
    private String id;
    private MedalType type;
    private CriptografiaAES criptografiaAES = new CriptografiaAES();
    public MedalDto() {
    }
    public MedalDto(Medal m) {
        try{
            this.id = criptografiaAES.encrypt(m.getId().toString());
            this.type = m.getType();
        }catch(Exception e){
            throw new IllegalArgumentException("build error Country");
        }
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public MedalType getType() {
        return type;
    }
    public void setType(MedalType type) {
        this.type = type;
    }

}
