package br.edu.utfpr.pb.app.labcaapi.controller;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericController;
import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericService;
import br.edu.utfpr.pb.app.labcaapi.model.Project;
import br.edu.utfpr.pb.app.labcaapi.service.ProjectService;
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

}
