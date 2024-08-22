package com.microsservices.country.dtos;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter 
public class MedalSportsDto {
    private MedalDto medal;
    private SportDto sport;//private List<SportDto> sports;
}
