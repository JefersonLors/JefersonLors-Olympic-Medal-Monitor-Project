package com.notifier_ms.controller.clients;

import com.notifier_ms.dto.CountryDto;
import com.notifier_ms.dto.MedalDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("country")
public interface CountryController {
    @RequestMapping(method= RequestMethod.GET, value="/medal/{id}")
    ResponseEntity<MedalDto> getMedal(@PathVariable Long id);

    @RequestMapping(method= RequestMethod.GET, value="/Country/ById/{id}")
    ResponseEntity<CountryDto> getCountryById(@PathVariable Long id);

}
