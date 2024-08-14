package com.authentication_ms.dto;

import java.util.List;

public record PostUserDto(String login, String password, List<Long> rolesId) {
}
