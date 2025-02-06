package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.EmailConfig;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailConfigRepository extends GenericRepository<EmailConfig, Long> {
}
