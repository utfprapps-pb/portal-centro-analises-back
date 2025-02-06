package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.Partner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepository extends GenericRepository<Partner, Long> {

    @Query(value = "select tdr.domain from DomainRole tdr where tdr.role = 2")
    List<String> findAllEstudantes();

}
