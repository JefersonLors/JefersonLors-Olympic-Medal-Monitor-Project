package com.notifier_ms.controller.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("medal-country-ms")
public interface MedalCountryController {
}
