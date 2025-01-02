package com.portal.centro.API.controller;

import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.Project;
import com.portal.centro.API.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projetos")
public class ProjectController extends GenericController<Project, Long> {

    private final ProjectService projectService;

    public ProjectController(GenericService<Project, Long> genericService,  ProjectService projectService) {
        super(genericService);
        this.projectService = projectService;
    }

    @GetMapping("/self")
    public ResponseEntity getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

//    @PostMapping(value = "add/student/{id}")
//    public ResponseEntity<Project> addUser(@PathVariable Long id, @Valid @RequestBody Project project) {
//        return ResponseEntity.ok(projectService.linkUserToProject(id, project));
//    }
//
//    @Override
//    public ResponseEntity<Project> save(@RequestBody @Valid Project requestBody) throws Exception {
//        return ResponseEntity.ok(projectService.save(requestBody));
//    }

}
