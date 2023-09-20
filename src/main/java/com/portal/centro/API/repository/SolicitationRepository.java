package com.portal.centro.API.repository;

import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.Solicitation;
import com.portal.centro.API.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitationRepository extends GenericRepository<Solicitation, Long> {

    List<Solicitation> findAllByProject_TeacherAndStatus(User user, SolicitationStatus status);
    Page<Solicitation> findAllByProject_TeacherAndStatus(User user, SolicitationStatus status, Pageable pageable);

    List<Solicitation> findAllByStatus(SolicitationStatus status);
    Page<Solicitation> findAllByStatus(SolicitationStatus status, Pageable pageable);

}
