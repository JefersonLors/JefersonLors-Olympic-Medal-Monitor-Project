package com.microsservices.country.controllers.clients;

import com.microsservices.country.dtos.UserHasRoleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.microsservices.country.dtos.TokenDto;

import java.util.List;

@FeignClient("token-validator-ms")
public interface TokenValidator {
    @RequestMapping(method= RequestMethod.POST, value="/tokenValidator/roles")
    ResponseEntity<List<String>> extractRolesFromToken(@RequestBody TokenDto tokenDto);

    @RequestMapping(method= RequestMethod.POST, value="/tokenValidator/hasRole")
    ResponseEntity<Boolean> userHasRole(@RequestBody UserHasRoleDto userHasRoleDto);
}
