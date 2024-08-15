package com.microsservices.country.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "country_medal_in_sports")
@Getter
@Setter
public class CountryMedalInSports{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sport", referencedColumnName = "id")
    private Sport sport;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medal", referencedColumnName = "id")
    private Medal medal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_country", referencedColumnName = "id")
    private Country country;
    
    public CountryMedalInSports() {
    }

    public CountryMedalInSports(Sport sport, Medal medal, Country country) {
        this.sport = sport;
        this.medal = medal;
        this.country = country;
    }

    

}