package com.token_validator_ms.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.token_validator_ms.dto.TokenDto;
import com.token_validator_ms.dto.UserHasRoleDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SignatureException;
import java.util.List;
import java.util.Map;

@Service
public class JWTokenService {
    @Value("${spring.security.token.secret}")
    private String secretKey;

    @Value("${spring.security.token.issuer}")
    private String issuer;

    public Boolean userHasAtLeastOneRole(UserHasRoleDto userHasRoleDto){
        List<String> tokenRoles = this.extractRolesFromToken(userHasRoleDto.token());
        for( String userRole : userHasRoleDto.roles()){
            if(tokenRoles.contains(userRole)) return true;
        }
        return false;
    }

    public List<String> extractRolesFromToken(String token) throws RuntimeException{
        Map<String, Claim> claimMap;

        if( token == null || token.isEmpty() || !token.startsWith("Bearer "))
            throw new RuntimeException("Token vazio ou não é um token Bearer ");

        claimMap = validateToken(token.replace("Bearer ", ""));

        return claimMap.get("roles").asList(String.class);
    }

    private Map<String, Claim> validateToken(String token) throws RuntimeException{
        try{
            return JWT.require(getAlgorithm())
                    .withIssuer(this.issuer)
                    .build()
                    .verify(token)
                    .getClaims();
        }catch( Exception ex ){
            throw new RuntimeException("Erro durante a validação do token: ", ex );
        }
    }

    private Algorithm getAlgorithm(){
        return Algorithm.HMAC256(secretKey);
    }
}
