package com.microsservices.country.dtos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter 
public class MedalSportsDto {
    private MedalDto medal;
    private List<SportDto> sports;
}
