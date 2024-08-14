package com.authentication_ms.repository;

import com.authentication_ms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationRepository extends JpaRepository<User, Long> {
    UserDetails findByLogin(String login);
}
