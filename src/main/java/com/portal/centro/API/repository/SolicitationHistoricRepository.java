package com.portal.centro.API.repository;

import com.portal.centro.API.enums.SolicitationStatus;
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

    List<SolicitationHistoric> findAllBySolicitation_CreatedBy(User user);
    Page<SolicitationHistoric> findAllBySolicitation_CreatedBy(User user, PageRequest pageRequest);
    List<SolicitationHistoric> findAllBySolicitation_CreatedByAndSolicitationIdAndStatusIsNotOrderByCreatedAtDesc(User user, Long id, SolicitationStatus newStatus);

    List<SolicitationHistoric> findAllBySolicitation_CreatedByOrSolicitation_Project_User(User user, User teacher);
    Page<SolicitationHistoric> findAllBySolicitation_CreatedByOrSolicitation_Project_User(User user, User teacher, PageRequest pageRequest);
    List<SolicitationHistoric> findAllBySolicitation_Project_UserAndSolicitationIdAndStatusIsNotOrderByCreatedAtDesc(User teacher, Long id, SolicitationStatus newStatus);

    List<SolicitationHistoric> findAllBySolicitationIdAndStatusIsNotOrderByCreatedAtDesc(Long id, SolicitationStatus newStatus);

    @Query(nativeQuery = true, value = "select a.* from audit a " +
           "where a.created_at in (select max(created_at) from audit group by solicitation_id) " +
           "group by a.id",
            countQuery = "select count(a.*) from audit a " +
                    "where a.created_at in (select max(created_at) from audit group by solicitation_id) " +
                    "group by a.id")
    Page<SolicitationHistoric> findAllDistinctByOrderByUserCreatedAtDesc(PageRequest pageRequest);

    @Query(nativeQuery = true, value = "select a.* from audit a " +
            "join solicitation s on s.id = a.solicitation_id " +
            "where a.created_at in (select max(created_at) from audit group by solicitation_id) and s.user_id=:userid " +
            "group by a.id",
            countQuery = "select count(a.*) from audit a " +
                    "join solicitation s on s.id = a.solicitation_id " +
                    "where a.created_at in (select max(created_at) from audit group by solicitation_id) and s.user_id=:userid " +
                    "group by a.id")
    Page<SolicitationHistoric> findAllDistinctByOrderByUserCreatedAtDescCreatedByUser(Long userid, PageRequest pageRequest);

    @Query(nativeQuery = true, value = "select a.* from audit a " +
            "join solicitation s on s.id = a.solicitation_id " +
            "join project p on p.id = s.project_id " +
            "where a.created_at in (select max(created_at) from audit group by solicitation_id) and " +
            "(s.user_id=:userid or p.user_id=:userid) " +
            "group by a.id",
            countQuery = "select count(a.id) from audit a " +
                    "join solicitation s on s.id = a.solicitation_id " +
                    "join project p on p.id = s.project_id " +
                    "where a.created_at in (select max(created_at) from audit group by solicitation_id) and " +
                    "(s.user_id=:userid or p.user_id=:userid) " +
                    "group by a.id")
    Page<SolicitationHistoric> findAllDistinctByOrderByUserCreatedAtDescCreatedByUserOrTeacherInProject(Long userid, PageRequest pageRequest);

}