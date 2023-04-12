package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.Partner;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends GenericRepository<Partner, Long> {

}
