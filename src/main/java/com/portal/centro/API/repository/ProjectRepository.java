package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.Project;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends GenericRepository<Project, Long> {
}
