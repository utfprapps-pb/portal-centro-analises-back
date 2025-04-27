package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.SolicitationAmostra;
import com.portal.centro.API.model.SolicitationAmostraFoto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitationAmostraFotoRepository extends GenericRepository<SolicitationAmostraFoto, Long> {

    List<SolicitationAmostraFoto> findAllByAmostraId(Long amostraId);

}
