package com.authentication_ms.dto;

import java.util.List;

public record PutUserRolesDto(Long userId, List<Long> rolesId) {
}
