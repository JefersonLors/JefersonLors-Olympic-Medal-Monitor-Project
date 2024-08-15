package com.user_ms.dto;

import com.user_ms.entity.User;

import java.time.LocalDateTime;

public record GetUserDto(long id, String name, String email, LocalDateTime dth_inc, LocalDateTime dth_upd) {
    public GetUserDto(User user){
        this(user.getId(), user.getName(), user.getEmail(), user.getDth_inc(), user.getDth_upd());
    }
}
