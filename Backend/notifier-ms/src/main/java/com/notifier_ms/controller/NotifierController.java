package com.notifier_ms.controller;

import com.notifier_ms.controller.clients.TokenValidator;
import com.notifier_ms.dto.*;
import com.notifier_ms.entity.Role;
import com.notifier_ms.service.CountryUserService;
import com.notifier_ms.service.RoleValidationService;
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
    private RoleValidationService roleValidationService;

    @PostMapping("/follow")
    @Transactional
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
    public ResponseEntity<SentMessageDto> notifyUsers(@RequestHeader("Authorization") String requestHeader,
                                                        @RequestBody MessageDataDto messageDataDto){
        if(this.roleValidationService.currentUserHasRole(requestHeader, Role.ROLE_ADMIN)) {
            SentMessageDto response = this.countryUserService.sendMessage(messageDataDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }
}
