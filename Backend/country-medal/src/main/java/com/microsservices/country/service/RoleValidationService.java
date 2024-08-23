package com.microsservices.country.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microsservices.country.controllers.clients.TokenValidator;
import com.microsservices.country.dtos.TokenDto;
import com.microsservices.country.enums.Role;

import java.util.List;

@Service
public class RoleValidationService {
    @Autowired
    private TokenValidator tokenValidator;

    public Boolean currentUserHasRole(String requestHeader, Role role){
        String token = recoverToken(requestHeader);
        List<String> roles = this.tokenValidator.extractRolesFromToken(new TokenDto(token)).getBody();
        return roles.stream().anyMatch(item->item.equalsIgnoreCase(role.toString()));
    }

    private String recoverToken(String token){
        if( token == null || token.isEmpty() || !token.startsWith("Bearer "))
            return null;
        return token.replace("Bearer ", "");
    }
}
