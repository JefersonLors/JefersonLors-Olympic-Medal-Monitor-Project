
package com.microsservices.country.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryMedalDto {
    private CountryDto country;
    private CountMedals_Dto medals;//private List<Medal> medals;

    public CountryMedalDto(){

    }

    public CountryMedalDto(CountryDto country, CountMedals_Dto medals) {
        this.country = country;
        this.medals = medals;
    }
    
    // public CountryMedalDto(CountryDto country, List<Medal> medals){
    //     this.country = country;
    //     this.medals = medals;
    // }
}   