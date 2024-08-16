package com.authentication_ms.service;

import com.auth0.jwt.interfaces.Claim;
import com.authentication_ms.entity.Role;
import com.authentication_ms.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
public class JWTokenService {
    @Value("${spring.security.token.secret}")
    private String secret;

    @Value("${spring.application.name}")
    private String issuer;

    public String generateToken(User user){
        try{
            return JWT.create()
                    .withIssuer(this.issuer)
                    .withSubject(user.getLogin())
                    .withClaim("roles", getRolesStringList(user.getRoles()))
                    .withExpiresAt(this.generateTokenExpireDate())
                    .sign(getAlgorithm());
        }catch (Exception ex){
            throw new RuntimeException("Erro durante a geração do token: ", ex);
        }
    }
    public String validateToken(String token){
        try{
            return JWT.require(getAlgorithm())
                    .withIssuer(this.issuer)
                    .build()
                    .verify(token)
                    .getSubject();
        }catch( Exception ex ){
            throw new RuntimeException("Erro durante a validação do token: ", ex );
        }
    }

    private Instant generateTokenExpireDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    private Algorithm getAlgorithm(){
        return Algorithm.HMAC256(secret);
    }

    private List<String> getRolesStringList(List<Role> roles){
        List<String> roleList = new ArrayList<>();
        roles.forEach((role)->roleList.add(role.getDescription()));
        return roleList;
    }
}
