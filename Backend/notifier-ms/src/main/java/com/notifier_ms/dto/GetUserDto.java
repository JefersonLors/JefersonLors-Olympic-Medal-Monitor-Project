package com.notifier_ms.dto;

import java.time.LocalDateTime;

public record GetUserDto(long id, String name, String email, LocalDateTime dth_inc, LocalDateTime dth_upd) {

}
