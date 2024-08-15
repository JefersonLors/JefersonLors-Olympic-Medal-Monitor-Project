package com.notifier_ms.repository;

import com.notifier_ms.entity.CountryUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryUserRepository extends JpaRepository<CountryUser, Long> {
    Optional<CountryUser> findByUserIdAndCountryId(long userId, long countryId);
    List<CountryUser> findByCountryId(long countryId);
}
