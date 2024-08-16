package com.authentication_ms.dto;

import java.util.List;

public record PutUserRolesDto(String login, List<Long> rolesId) {
}
