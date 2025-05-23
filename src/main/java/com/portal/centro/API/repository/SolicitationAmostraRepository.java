package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.SolicitationAmostra;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitationAmostraRepository extends GenericRepository<SolicitationAmostra, Long> {

    List<SolicitationAmostra> findAllByFormId(Long formId);

}
