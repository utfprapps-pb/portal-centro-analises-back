package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.Attachment;
import com.portal.centro.API.model.TermsOfUse;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsOfUseRepository extends GenericRepository<TermsOfUse, Long> {

}
