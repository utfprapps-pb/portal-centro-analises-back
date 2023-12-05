package com.portal.centro.API.repository;

import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.Audit;
import com.portal.centro.API.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends GenericRepository<Audit, Long> {

    List<Audit> findAllBySolicitation_CreatedBy(User user);
    Page<Audit> findAllBySolicitation_CreatedBy(User user, PageRequest pageRequest);
    List<Audit> findAllBySolicitation_CreatedByAndSolicitationIdAndNewStatusIsNotOrderByChangeDateDesc(User user, Long id, SolicitationStatus newStatus);

    List<Audit> findAllBySolicitation_CreatedByOrSolicitation_Project_Teacher(User user, User teacher);
    Page<Audit> findAllBySolicitation_CreatedByOrSolicitation_Project_Teacher(User user, User teacher, PageRequest pageRequest);
    List<Audit> findAllBySolicitation_CreatedByOrSolicitation_Project_TeacherAndSolicitationIdAndNewStatusIsNotOrderByChangeDateDesc(User user, User teacher, Long id, SolicitationStatus newStatus);

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
            "where a.change_date in (select max(change_date) from audit group by solicitation_id) and s.creator_id=:userid " +
            "group by a.id",
            countQuery = "select count(a.*) from audit a " +
                    "join solicitation s on s.id = a.solicitation_id " +
                    "where a.change_date in (select max(change_date) from audit group by solicitation_id) and s.creator_id=:userid " +
                    "group by a.id")
    Page<Audit> findAllDistinctByOrderByUserCreatedAtDescCreatedByUser(Long userid, PageRequest pageRequest);

    @Query(nativeQuery = true, value = "select a.* from audit a " +
            "join solicitation s on s.id = a.solicitation_id " +
            "join project p on p.id = s.project_id " +
            "where a.change_date in (select max(change_date) from audit group by solicitation_id) and " +
            "(s.creator_id=:userid or p.teacher_id=:userid) " +
            "group by a.id",
            countQuery = "select count(a.id) from audit a " +
                    "join solicitation s on s.id = a.solicitation_id " +
                    "join project p on p.id = s.project_id " +
                    "where a.change_date in (select max(change_date) from audit group by solicitation_id) and " +
                    "(s.creator_id=:userid or p.teacher_id=:userid) " +
                    "group by a.id")
    Page<Audit> findAllDistinctByOrderByUserCreatedAtDescCreatedByUserOrTeacherInProject(Long userid, PageRequest pageRequest);

}