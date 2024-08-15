package com.notifier_ms.controller;

import com.notifier_ms.dto.MessageDataDto;
import com.notifier_ms.dto.SentMessageDto;
import com.notifier_ms.dto.UserFollowCountryDto;
import com.notifier_ms.dto.UserUnfollowCountryDto;
import com.notifier_ms.service.CountryUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifier")
public class NotifierController {
    @Autowired
    private CountryUserService countryUserService;

    @PostMapping("/follow")
    @Transactional
    public ResponseEntity<UserFollowCountryDto> followCountry(@RequestBody UserFollowCountryDto userFollowCountryDto){
        UserFollowCountryDto response = this.countryUserService.userFollowCountry(userFollowCountryDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/unfollow")
    @Transactional
    public ResponseEntity<UserUnfollowCountryDto> unfollowCountry(@RequestBody UserUnfollowCountryDto userUnfollowCountryDto){
        UserUnfollowCountryDto response = this.countryUserService.userUnfollowCountry(userUnfollowCountryDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/notify")
    @Transactional
    public ResponseEntity<SentMessageDto> notifyUsers(@RequestBody MessageDataDto messageDataDto){
        SentMessageDto response = this.countryUserService.sendMessage(messageDataDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
