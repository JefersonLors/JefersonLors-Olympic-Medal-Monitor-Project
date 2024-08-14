package com.authentication_ms.dto;

import com.authentication_ms.entity.Role;

public record RoleDto(long id, String description) {
    public RoleDto(Role role){
        this(role.getId(), role.getDescription());
    }
}
