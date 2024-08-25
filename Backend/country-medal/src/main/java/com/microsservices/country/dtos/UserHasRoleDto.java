package com.microsservices.country.dtos;

import java.util.List;

public record UserHasRoleDto(String token, List<String> roles){
}
