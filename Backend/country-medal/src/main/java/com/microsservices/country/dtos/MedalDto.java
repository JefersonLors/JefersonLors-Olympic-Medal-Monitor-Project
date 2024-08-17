package com.microsservices.country.dtos;

import com.microsservices.country.enums.MedalType;
import com.microsservices.country.models.Medal;
import com.microsservices.country.service.criptografia.CriptografiaAES;

public class MedalDto {
    private String id;
    private MedalType type;
    private CriptografiaAES criptografiaAES = new CriptografiaAES();
    public MedalDto() {
    }
    public MedalDto(Medal m) {
        try{
            this.id = m.getId().toString();
            this.type = m.getType();
        }catch(Exception e){
            throw new RuntimeException("build error Country");
        }
    }

    public MedalDto encryptId(){
        try {
            this.id = criptografiaAES.encrypt(this.id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return this;
    }
    
    public MedalDto(String id, MedalType type) {
        this.id = id;
        this.type = type;
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
