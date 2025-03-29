package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.SolicitationHistoric;
import com.portal.centro.API.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitationHistoricRepository extends GenericRepository<SolicitationHistoric, Long> {

    List<SolicitationHistoric> findAllBySolicitation_CreatedByOrSolicitation_Responsavel(User user, User responsavel);

    List<SolicitationHistoric> findAllBySolicitationId(Long id);

}