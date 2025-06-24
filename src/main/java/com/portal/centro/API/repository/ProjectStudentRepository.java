package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.Project;
import com.portal.centro.API.model.ProjectStudent;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectStudentRepository extends GenericRepository<ProjectStudent, Long> {

    List<ProjectStudent> findAllByProject(Project projectId);

}
