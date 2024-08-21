package com.microsservices.country.dtos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter 
public class MedalSportsDto {
    private MedalDto medal;
    private SportDto sport;//private List<SportDto> sports;
}
