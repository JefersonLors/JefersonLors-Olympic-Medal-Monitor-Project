package com.token_validator_ms.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.token_validator_ms.dto.TokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JWTokenService {
    @Value("${spring.security.token.secret}")
    private String secretKey;

    @Value("${spring.security.token.issuer}")
    private String issuer;

    public List<String> extractRolesFromToken(TokenDto tokenDto){
        Map<String, Claim> claimMap = validateToken(tokenDto.value());
        return claimMap.get("roles").asList(String.class);
    }

    private Map<String, Claim> validateToken(String token){
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
