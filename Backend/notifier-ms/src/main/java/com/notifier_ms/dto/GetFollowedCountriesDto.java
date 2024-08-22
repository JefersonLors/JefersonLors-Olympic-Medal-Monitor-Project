package com.notifier_ms.dto;

import java.util.List;

public record GetFollowedCountriesDto(long userId, List<Long> countriesId) {
}
