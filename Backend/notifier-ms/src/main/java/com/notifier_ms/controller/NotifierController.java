package com.notifier_ms.controller;

import com.notifier_ms.dto.*;
import com.notifier_ms.entity.Role;
import com.notifier_ms.service.CountryUserService;
import com.notifier_ms.service.RoleValidationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifier")
public class NotifierController {
    @Autowired
    private CountryUserService countryUserService;

    @Autowired
    private RoleValidationService roleValidationService;


    @GetMapping("/id")
    @Operation(summary="Retorna a lista de países que um usuário segue", description="Retorna a lista de países que um usuário segue.")
    public ResponseEntity<GetFollowedCountriesDto> getFollowedCountries(@RequestHeader("Authorization") String requestHeader,
                                                                        @RequestParam long id){
        if(this.roleValidationService.currentUserHasRole(requestHeader, Role.ROLE_USER)) {
            GetFollowedCountriesDto response = this.countryUserService.followedCountries(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/follow")
    @Transactional
    @Operation(summary="Vincula um país a um usuário", description="Vincula um país a um usuário para que seja notificado quando houver atualização de medalha.")
    public ResponseEntity<UserFollowCountryDto> followCountry(@RequestHeader("Authorization") String requestHeader,
                                                                @RequestBody UserFollowCountryDto userFollowCountryDto){
        if(this.roleValidationService.currentUserHasRole(requestHeader, Role.ROLE_USER)) {
            UserFollowCountryDto response = this.countryUserService.userFollowCountry(userFollowCountryDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/unfollow")
    @Transactional
    @Operation(summary="Desvincula um país de um usuário", description="Usuário passa a não receber mais notificações quando há atualizações de medalha do país.")
    public ResponseEntity<UserUnfollowCountryDto> unfollowCountry( @RequestHeader("Authorization") String requestHeader,
                                                                    @RequestBody UserUnfollowCountryDto userUnfollowCountryDto){
        if(this.roleValidationService.currentUserHasRole(requestHeader, Role.ROLE_USER)) {
            UserUnfollowCountryDto response = this.countryUserService.userUnfollowCountry(userUnfollowCountryDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/notify")
    @Transactional
    @Operation(summary="Notifica usuário via e-mail", description="Notifica usuário via e-mail.")
    public ResponseEntity<SentMessageDto> notifyUsers(@RequestHeader("Authorization") String requestHeader,
                                                        @RequestBody MessageDataDto messageDataDto){
        if(this.roleValidationService.currentUserHasRole(requestHeader, Role.ROLE_ADMIN)) {
            SentMessageDto response = this.countryUserService.sendMessage(messageDataDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }
}
