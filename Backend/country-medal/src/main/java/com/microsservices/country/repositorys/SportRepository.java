
package com.microsservices.country.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microsservices.country.models.Sport;

public interface SportRepository  extends JpaRepository<Sport, Long>{

    Sport findByName(String name);

    
}