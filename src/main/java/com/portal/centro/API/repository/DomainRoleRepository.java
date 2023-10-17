package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.DomainRole;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DomainRoleRepository extends GenericRepository<DomainRole, Long> {

    Optional<DomainRole> findByDomain(String domain);

}
