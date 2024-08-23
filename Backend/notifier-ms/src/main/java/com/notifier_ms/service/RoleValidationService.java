package com.notifier_ms.service;

import com.notifier_ms.controller.clients.TokenValidator;
import com.notifier_ms.dto.TokenDto;
import com.notifier_ms.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleValidationService {
    @Autowired
    private TokenValidator tokenValidator;

    public Boolean currentUserHasRole(String requestHeader, Role role){
        String token = recoverToken(requestHeader);
        List<String> roles = this.tokenValidator.extractRolesFromToken(new TokenDto(token)).getBody();
        Boolean result = roles.stream().anyMatch(item->item.equalsIgnoreCase(role.name()));

        if( !result ){
            throw new RuntimeException("Usuário não tem acesso a esse recurso.");
        }
        return result;
    }

    private String recoverToken(String token){
        if( token == null || token.isEmpty() || !token.startsWith("Bearer "))
            throw new RuntimeException("Token vazio ou não é um token Bearer ");
        return token.replace("Bearer ", "");
    }
}
