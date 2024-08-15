package com.authentication_ms.dto;

import com.authentication_ms.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public record GetUserDto(long id, String login, List<GetRoleDto> roles, LocalDateTime dth_inc, LocalDateTime dth_upd) {
    public GetUserDto(User user){
        this(user.getId(), user.getLogin(), user.getRoles().stream().map(GetRoleDto::new).toList(), user.getDth_inc(), user.getDth_upd());
    }
}
