
package com.microsservices.country.dtos;

import java.util.List;

import com.microsservices.country.models.Medal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryMedalDto {
    private CountryDto country;
    private List<Medal> medals;

    public CountryMedalDto(){

    }
    public CountryMedalDto(CountryDto country, List<Medal> medals){
        this.country = country;
        this.medals = medals;
    }
}   