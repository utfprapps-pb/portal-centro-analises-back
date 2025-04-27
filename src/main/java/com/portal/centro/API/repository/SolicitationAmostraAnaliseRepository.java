package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.Solicitation;
import com.portal.centro.API.model.SolicitationAmostra;
import com.portal.centro.API.model.SolicitationAmostraAnalise;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitationAmostraAnaliseRepository extends GenericRepository<SolicitationAmostraAnalise, Long> {

    List<SolicitationAmostraAnalise> findAllByAmostraId(Long amostraId);

}
