package com.portal.centro.API.repository;

import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.Audit;
import com.portal.centro.API.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends GenericRepository<Audit, Long> {

    List<Audit> findAllBySolicitation_CreatedBy(String createdBy);
    Page<Audit> findAllBySolicitation_CreatedBy(String createdBy, PageRequest pageRequest);
    List<Audit> findAllBySolicitation_CreatedByAndSolicitationIdAndNewStatusIsNotOrderByNewStatus(String createdBy, Long id, SolicitationStatus newStatus);

    List<Audit> findAllBySolicitation_CreatedByOrSolicitation_Project_Teacher(String createdBy, User teacher);
    Page<Audit> findAllBySolicitation_CreatedByOrSolicitation_Project_Teacher(String createdBy, User teacher, PageRequest pageRequest);
    List<Audit> findAllBySolicitation_CreatedByOrSolicitation_Project_TeacherAndSolicitationIdAndNewStatusIsNotOrderByNewStatus(String createdBy, User teacher, Long id, SolicitationStatus newStatus);

    List<Audit> findAllBySolicitationIdAndNewStatusIsNotOrderByNewStatus(Long id, SolicitationStatus newStatus);

}