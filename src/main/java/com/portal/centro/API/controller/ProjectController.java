package com.portal.centro.API.controller;

import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.Project;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/project")
public class ProjectController extends GenericController<Project, Long> {

    public ProjectController(GenericService<Project, Long> genericService) {
        super(genericService);
    }
}
