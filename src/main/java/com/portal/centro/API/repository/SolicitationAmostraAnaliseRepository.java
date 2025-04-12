package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.Solicitation;
import com.portal.centro.API.model.SolicitationAmostraAnalise;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitationAmostraAnaliseRepository extends GenericRepository<SolicitationAmostraAnalise, Long> {

    @Query(value = "select " +
            "tsf.solicitation_id " +
            "from tb_solicitation_amostra_analise tsaa " +
            "join tb_solicitation_amostra tsa on tsaa.amostra_id = tsa.id " +
            "join tb_solicitation_form tsf on tsa.form_id = tsf.id " +
            "join tb_solicitation ts on tsf.solicitation_id = ts.id " +
            "where tsaa.id = :id", nativeQuery = true)
    Long findSolicitationByAnaliseId(@Param("id") Long id);

}
