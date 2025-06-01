package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.Project;
import com.portal.centro.API.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends GenericRepository<Project, Long> {

    @Query("SELECT DISTINCT p FROM Project p" +
            "            LEFT JOIN ProjectStudent tps on tps.project = p" +
            "            WHERE p.user = :user OR tps.user = :user")
    List<Project> findAllByUserOrStudent(User user);

}
