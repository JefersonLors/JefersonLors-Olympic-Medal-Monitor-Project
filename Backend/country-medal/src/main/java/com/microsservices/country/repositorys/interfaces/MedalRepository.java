
package com.microsservices.country.repositorys.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microsservices.country.models.Medal;

public interface MedalRepository extends JpaRepository<Medal, Long>{

    
}