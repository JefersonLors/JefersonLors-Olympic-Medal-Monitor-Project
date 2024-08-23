package com.microsservices.country.dtos;

import com.microsservices.country.models.Sport;
import com.microsservices.country.service.criptografia.CriptografiaAES;

public class SportDto{
    private String id;
    private String name;
    private String description;
    private CriptografiaAES criptografiaAES = new CriptografiaAES();

    public SportDto(Sport s) {
        try{
            this.id = s.getId().toString();
            this.name = s.getName();
            this.description = s.getDescription();
        }catch(Exception e){
            throw new RuntimeException("build error Country");
        }
    }

    public SportDto encryptId(){
        try {
            this.id = criptografiaAES.encrypt(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return this;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}