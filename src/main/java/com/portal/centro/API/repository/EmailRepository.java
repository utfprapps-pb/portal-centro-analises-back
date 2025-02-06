package com.portal.centro.API.repository;

import com.portal.centro.API.model.EmailLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmailRepository extends JpaRepository<EmailLog, UUID> {
}
