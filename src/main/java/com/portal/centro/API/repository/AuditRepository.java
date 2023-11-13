package com.portal.centro.API.repository;

import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.Audit;
import com.portal.centro.API.model.Transaction;
import com.portal.centro.API.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends GenericRepository<Audit, Long> {

    List<Audit> findAllBySolicitation_CreatedBy(User user);
    Page<Audit> findAllBySolicitation_CreatedBy(User user, PageRequest pageRequest);
    List<Audit> findAllBySolicitation_CreatedByAndSolicitationIdAndNewStatusIsNotOrderByNewStatus(User user, Long id, SolicitationStatus newStatus);

    List<Audit> findAllBySolicitation_CreatedByOrSolicitation_Project_Teacher(User user, User teacher);
    Page<Audit> findAllBySolicitation_CreatedByOrSolicitation_Project_Teacher(User user, User teacher, PageRequest pageRequest);
    List<Audit> findAllBySolicitation_CreatedByOrSolicitation_Project_TeacherAndSolicitationIdAndNewStatusIsNotOrderByNewStatus(User user, User teacher, Long id, SolicitationStatus newStatus);

    List<Audit> findAllBySolicitationIdAndNewStatusIsNotOrderByNewStatus(Long id, SolicitationStatus newStatus);

    @Query(nativeQuery = true, value = "select a.* from audit a " +
           "where a.change_date in (select max(change_date) from audit group by solicitation_id) " +
           "group by a.id",
            countQuery = "select a.* from audit a " +
                    "where a.change_date in (select max(change_date) from audit group by solicitation_id) " +
                    "group by a.id")
    Page<Audit> findAllDistinctByOrderByUserCreatedAtDesc(PageRequest pageRequest);
}