
package com.microsservices.country.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microsservices.country.models.Medal;

public interface MedalRepository extends JpaRepository<Medal, Long>{

    
}