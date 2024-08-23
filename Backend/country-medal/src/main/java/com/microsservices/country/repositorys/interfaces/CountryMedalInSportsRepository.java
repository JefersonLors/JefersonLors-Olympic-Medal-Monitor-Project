package com.microsservices.country.repositorys.interfaces;


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
           "JOIN FETCH csm.sport sport " +
           "JOIN FETCH csm.medal medal " +
           "WHERE country.Id = :countryId")
    List<CountryMedalInSports> findByCountryId(@Param("countryId") Long id);

@Query("SELECT csm FROM country_medal_in_sports csm " +
           "JOIN FETCH csm.country country " +
           "JOIN FETCH csm.medal medal")
// @Query("SELECT csm FROM country_medal_in_sports csm " +
//            "RIGHT JOIN FETCH csm.country country")
    // @Query("SELECT c FROM Country c " +
    //    "LEFT JOIN FETCH c.countryMedalInSports cms " +
    //    "LEFT JOIN FETCH cms.medal")
    List<CountryMedalInSports> findCountriesAndMedals();


@Query("SELECT CASE WHEN COUNT(csm) > 0 THEN TRUE ELSE FALSE END " +
    "FROM country_medal_in_sports csm " +
    "JOIN csm.country country " +
    "JOIN csm.sport sport " +
    "WHERE country.id = :countryId AND sport.id = :sportId")
    boolean existsMedalForCountryAndSport(@Param("countryId") Long countryId, @Param("sportId") Long sportId);


    @Query("SELECT CASE WHEN COUNT(cms) > 0 THEN true ELSE false END " +
    "FROM country_medal_in_sports cms " +
    "JOIN cms.country country " +
    "JOIN cms.sport sport " +
    "JOIN cms.medal medal " +
    "WHERE sport.id = :sportId AND medal.id = :medalId")
boolean isThereSuchAMedalForAnyCountryInThisSport(@Param("sportId") Long sportId, @Param("medalId") Long medalId);

}