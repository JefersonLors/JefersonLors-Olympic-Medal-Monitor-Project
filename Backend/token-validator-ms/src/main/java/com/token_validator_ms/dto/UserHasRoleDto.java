package com.token_validator_ms.dto;

import java.util.List;

public record UserHasRoleDto (String token, List<String> roles){
}
