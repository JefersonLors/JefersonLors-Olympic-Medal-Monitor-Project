package com.authentication_ms.dto;

import com.authentication_ms.entity.Role;

public record GetRoleDto(long id, String description) {
    public GetRoleDto(Role role){
        this(role.getId(), role.getDescription());
    }
}
