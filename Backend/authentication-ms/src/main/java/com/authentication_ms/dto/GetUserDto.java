package com.authentication_ms.dto;

import com.authentication_ms.entity.Role;
import com.authentication_ms.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public record GetUserDto(long id, String login, List<Long> roles, LocalDateTime dth_inc, LocalDateTime dth_upd) {
    public GetUserDto(User user){
        this(user.getId(), user.getLogin(), user.getRoles().stream().map(Role::getId).toList(), user.getDth_inc(), user.getDth_upd());
    }
}
