package com.authentication_ms.controller;

import com.authentication_ms.dto.SignInDto;
import com.authentication_ms.dto.GetUserDto;
import com.authentication_ms.dto.SignUpDto;
import com.authentication_ms.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private UserService userService;
    @PostMapping("/signIn")
    @Transactional
    @Operation(summary="Login de usuário", description="Usuário loga e recebe um token contendo suas roles.")
    public ResponseEntity<String> signIn(@RequestBody @Validated SignInDto authenticationDto){
        String token = this.userService.signIn(authenticationDto);
        return ResponseEntity.ok(token);
    }
    @PostMapping("/signUp")
    @Transactional
    @Operation(summary="Cadastro de usuário", description="Cadastro de usuário.")
    public ResponseEntity<String> signUp(@RequestBody @Validated SignUpDto postUserDto){
        this.userService.signUp(postUserDto);
        return ResponseEntity.ok("Usuário criado com sucesso!");
    }
}
