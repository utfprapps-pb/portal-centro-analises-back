package com.portal.centro.API.repository;

import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.Audit;
import com.portal.centro.API.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends GenericRepository<Audit, Long> {

    List<Audit> findAllBySolicitation_CreatedBy(String createdBy);
    Page<Audit> findAllBySolicitation_CreatedBy(String createdBy, PageRequest pageRequest);
    List<Audit> findAllBySolicitation_CreatedByAndSolicitationIdAndNewStatusIsNotOrderByChangeDateDesc(String createdBy, Long id, SolicitationStatus newStatus);

    List<Audit> findAllBySolicitation_CreatedByOrSolicitation_Project_Teacher(String createdBy, User teacher);
    Page<Audit> findAllBySolicitation_CreatedByOrSolicitation_Project_Teacher(String createdBy, User teacher, PageRequest pageRequest);
    List<Audit> findAllBySolicitation_Project_TeacherAndSolicitationIdAndNewStatusIsNotOrderByChangeDateDesc(User teacher, Long id, SolicitationStatus newStatus);

    List<Audit> findAllBySolicitationIdAndNewStatusIsNotOrderByChangeDateDesc(Long id, SolicitationStatus newStatus);

    @Query(nativeQuery = true, value = "select a.* from audit a " +
           "where a.change_date in (select max(change_date) from audit group by solicitation_id) " +
           "group by a.id",
            countQuery = "select count(a.*) from audit a " +
                    "where a.change_date in (select max(change_date) from audit group by solicitation_id) " +
                    "group by a.id")
    Page<Audit> findAllDistinctByOrderByUserCreatedAtDesc(PageRequest pageRequest);

    @Query(nativeQuery = true, value = "select a.* from audit a " +
            "join solicitation s on s.id = a.solicitation_id " +
            "where a.change_date in (select max(change_date) from audit group by solicitation_id) and s.created_by=:useremail " +
            "group by a.id",
            countQuery = "select count(a.*) from audit a " +
                    "join solicitation s on s.id = a.solicitation_id " +
                    "where a.change_date in (select max(change_date) from audit group by solicitation_id) and s.created_by=:useremail " +
                    "group by a.id")
    Page<Audit> findAllDistinctByOrderByUserCreatedAtDescCreatedByUser(String useremail, PageRequest pageRequest);

    @Query(nativeQuery = true, value = "select a.* from audit a " +
            "join solicitation s on s.id = a.solicitation_id " +
            "join project p on p.id = s.project_id " +
            "where a.change_date in (select max(change_date) from audit group by solicitation_id) and " +
            "(s.created_by=:useremail or p.created_by=:useremail) " +
            "group by a.id",
            countQuery = "select count(a.id) from audit a " +
                    "join solicitation s on s.id = a.solicitation_id " +
                    "join project p on p.id = s.project_id " +
                    "where a.change_date in (select max(change_date) from audit group by solicitation_id) and " +
                    "(s.created_by=:useremail or p.created_by=:useremail) " +
                    "group by a.id")
    Page<Audit> findAllDistinctByOrderByUserCreatedAtDescCreatedByUserOrTeacherInProject(String useremail, PageRequest pageRequest);

}