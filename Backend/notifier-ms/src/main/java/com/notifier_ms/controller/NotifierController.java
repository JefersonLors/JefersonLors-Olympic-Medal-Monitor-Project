package com.notifier_ms.controller;

import com.notifier_ms.controller.clients.TokenValidator;
import com.notifier_ms.dto.*;
import com.notifier_ms.entity.Role;
import com.notifier_ms.service.CountryUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifier")
public class NotifierController {
    @Autowired
    private CountryUserService countryUserService;

    @Autowired
    private TokenValidator tokenValidator;

    @GetMapping("/followeds/id")
    @Operation(summary="Retorna a lista de países que um usuário segue", description="Retorna a lista de países que um usuário segue.")
    public ResponseEntity<GetFollowedCountriesDto> getFollowedCountries(@RequestHeader("Authorization") String requestHeader,
                                                                        @RequestParam long id){
        UserHasRoleDto data = new UserHasRoleDto(requestHeader, List.of(Role.ROLE_ADMIN.name(), Role.ROLE_USER.name()));
        if(this.tokenValidator.userHasRole(data).getBody().booleanValue()){
            GetFollowedCountriesDto response = this.countryUserService.followedCountries(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @PostMapping("/follow")
    @Transactional
    @Operation(summary="Vincula um país a um usuário", description="Vincula um país a um usuário para que seja notificado quando houver atualização de medalha.")
    public ResponseEntity<UserFollowCountryDto> followCountry(@RequestHeader("Authorization") String requestHeader,
                                                                @RequestBody UserFollowCountryDto userFollowCountryDto){
        UserHasRoleDto data = new UserHasRoleDto(requestHeader, List.of(Role.ROLE_ADMIN.name(), Role.ROLE_USER.name()));
        if(this.tokenValidator.userHasRole(data).getBody().booleanValue()){
            UserFollowCountryDto response = this.countryUserService.userFollowCountry(userFollowCountryDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/unfollow")
    @Transactional
    @Operation(summary="Desvincula um país de um usuário", description="Usuário passa a não receber mais notificações quando há atualizações de medalha do país.")
    public ResponseEntity<UserUnfollowCountryDto> unfollowCountry( @RequestHeader("Authorization") String requestHeader,
                                                                    @RequestBody UserUnfollowCountryDto userUnfollowCountryDto){
        UserHasRoleDto data = new UserHasRoleDto(requestHeader, List.of(Role.ROLE_ADMIN.name(), Role.ROLE_USER.name()));
        if(this.tokenValidator.userHasRole(data).getBody().booleanValue()){
            UserUnfollowCountryDto response = this.countryUserService.userUnfollowCountry(userUnfollowCountryDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/notify")
    @Transactional
    @Operation(summary="Notifica usuário via e-mail", description="Notifica usuário via e-mail.")
    public ResponseEntity<SentMessageDto> notifyUsers(@RequestHeader("Authorization") String requestHeader,
                                                        @RequestBody MessageDataDto messageDataDto){
        UserHasRoleDto data = new UserHasRoleDto(requestHeader, List.of(Role.ROLE_ADMIN.name()));
        if(this.tokenValidator.userHasRole(data).getBody().booleanValue()){
            SentMessageDto response = this.countryUserService.sendMessage(messageDataDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
