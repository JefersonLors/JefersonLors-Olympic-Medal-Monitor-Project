package com.authentication_ms.controller;

import com.authentication_ms.dto.SignInDto;
import com.authentication_ms.dto.GetUserDto;
import com.authentication_ms.dto.SignUpDto;
import com.authentication_ms.service.UserService;
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
    public ResponseEntity<String> signIn(@RequestBody @Validated SignInDto authenticationDto){
        String token = this.userService.signIn(authenticationDto);
        return ResponseEntity.ok(token);
    }
    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody @Validated SignUpDto postUserDto){
        GetUserDto getUserDto = this.userService.signUp(postUserDto);

        if(getUserDto == null)
            return ResponseEntity.badRequest().body("Não foi possível criar o usuário.");

        return ResponseEntity.ok("Usuário criado com sucesso!");
    }
}
