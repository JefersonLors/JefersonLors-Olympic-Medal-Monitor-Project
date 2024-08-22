
package com.microsservices.country.repositorys.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import com.microsservices.country.models.Country;

public interface CountryRespository  extends JpaRepository<Country, Long>{

    Country findByName(String name);

    // @Query("SELECT DISTINCT country FROM Country country " +
    //        "LEFT JOIN FETCH country.countryMedalInSports csm " +
    //        "LEFT JOIN FETCH csm.medal medal")
    // List<Country> findAllCountriesWithOptionalMedals();
//     @Query("SELECT c FROM Country c " +
//        "LEFT JOIN FETCH c.countryMedalInSports cms " +
//        "LEFT JOIN FETCH cms.medal")
// List<Country> findAllCountriesWithOptionalMedals();

    
}