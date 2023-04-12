package com.portal.centro.API.service;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.Project;
import org.springframework.stereotype.Service;

@Service
public class ProjectService extends GenericService<Project, Long> {

    public ProjectService(GenericRepository<Project, Long> genericRepository) {
        super(genericRepository);
    }
}
