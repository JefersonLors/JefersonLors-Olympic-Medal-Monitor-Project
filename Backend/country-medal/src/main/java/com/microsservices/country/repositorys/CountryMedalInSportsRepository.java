package com.microsservices.country.repositorys;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.microsservices.country.models.CountryMedalInSports;

public interface CountryMedalInSportsRepository extends JpaRepository<CountryMedalInSports, Long>{

@Query("SELECT csm FROM country_medal_in_sports csm " +
           "JOIN FETCH csm.country country " +
           "JOIN FETCH csm.sport sport " +
           "JOIN FETCH csm.medal medal " +
           "WHERE country.name = :countryName")
    List<CountryMedalInSports> findByCountryName(@Param("countryName") String countryName);

@Query("SELECT csm FROM country_medal_in_sports csm " +
           "JOIN FETCH csm.country country " +
           "JOIN FETCH csm.medal medal")
    List<CountryMedalInSports> findCountriesAndMedals();


@Query("SELECT CASE WHEN COUNT(csm) > 0 THEN TRUE ELSE FALSE END " +
    "FROM country_medal_in_sports csm " +
    "JOIN csm.country country " +
    "JOIN csm.sport sport " +
    "WHERE country.id = :countryId AND sport.id = :sportId")
    boolean existsMedalForCountryAndSport(@Param("countryId") Long countryId, @Param("sportId") Long sportId);

}