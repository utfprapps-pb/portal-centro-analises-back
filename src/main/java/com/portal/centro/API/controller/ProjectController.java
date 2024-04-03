package com.portal.centro.API.controller;

import com.portal.centro.API.dto.RetrieveProjectInfo;
import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.Project;
import com.portal.centro.API.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("projects")
public class ProjectController extends GenericController<Project, Long> {

    private final ProjectService projectService;

    public ProjectController(GenericService<Project, Long> genericService,  ProjectService projectService) {
        super(genericService);
        this.projectService = projectService;
    }

    @GetMapping(value = "all")
    public ResponseEntity<RetrieveProjectInfo> getAllProjects() {
        return ResponseEntity.ok( projectService.getAllProjects() );
    }

    @PostMapping(value = "add/student/{id}")
    public ResponseEntity<Project> addUser(@PathVariable Long id, @Valid @RequestBody Project project) {
        return ResponseEntity.ok(projectService.linkUserToProject(id, project));
    }

    @Override
    public ResponseEntity<Project> save(@RequestBody @Valid Project requestBody) throws Exception {
        return ResponseEntity.ok(projectService.save(requestBody));
    }

}
