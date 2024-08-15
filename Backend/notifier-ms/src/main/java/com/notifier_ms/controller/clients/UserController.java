package com.notifier_ms.controller.clients;

import com.notifier_ms.dto.GetUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user-ms")
public interface UserController {
    @RequestMapping(method= RequestMethod.GET, value="/user/id")
    ResponseEntity<GetUserDto> getUserById(@RequestParam long id);
}
