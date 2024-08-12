package com.email.email_ms.repositories;

import com.email.email_ms.entities.EmailStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailStatusRepository extends JpaRepository<EmailStatus, Long> {
}
