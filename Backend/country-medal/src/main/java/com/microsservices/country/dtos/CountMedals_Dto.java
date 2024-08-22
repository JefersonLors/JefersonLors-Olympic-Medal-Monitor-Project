package com.microsservices.country.dtos;


public class CountMedals_Dto {
    private int ouro = 0;
    private int prata = 0;
    private int bronze = 0;
    public int getOuro() {
        return ouro;
    }
    public void setOuro() {
        this.ouro = ouro + 1;
    }
    public int getPrata() {
        return prata;
    }
    public void setPrata() {
        this.prata = prata + 1;
    }
    public int getBronze() {
        return bronze;
    }
    public void setBronze() {
        this.bronze = bronze + 1;
    }

    
}
